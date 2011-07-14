/*
 * Nital is an effort to provide a well documented, powerful, scalable, and robust 
 * RuneScape server framework delivered open-source to all users.
 *
 *  Copyright (C) 2011 Nital Software
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package us.nital.net.codec;

import java.security.SecureRandom;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import us.nital.model.Player;
import us.nital.net.Session;
import us.nital.net.io.OutBuffer;
import us.nital.util.BufferUtils;
import us.nital.util.NameUtils;
import us.nital.util.PrimitiveUtils;
import us.nital.world.Result;
import us.nital.world.ReturnCodes;
import us.nital.world.World;

/*
 * TODO: At a later time we might want to switch them to
 * be departed from this class, but this is okay for a
 * smaller decode (such as that in the 317 protocol).
 */

/**
 * Performs decoding measures which decodes a new connection into
 * a login request which is then processed by the server to enable
 * game access for the user.
 * 
 * <h2>Things to Note</h2>
 * <ul>
 * 	<li>All operation codes are stored in one class in the client.
 * 		This means that the codes are notifiers of only <b>one</b> action
 * 		and will not be reused.</li>
 * </ul>
 * 
 * @author Thomas Nappo
 * @see {@link State}
 */
public class FrontDecoder extends ReplayingDecoder<FrontDecoder.State> {

	/**
	 * The state of the decoding process.
	 * @author Thomas Nappo
	 */
	protected static enum State {

		/**
		 * At this state the client sends a decode request
		 * to the server. Here the server identifies what state
		 * we it needs to perform by reading an unsigned <code>byte</code>.</p>
		 * 
		 * According to debugging statistics the byte attributes these values:
		 * <ui>
		 * 	<li>14: The client requested to login (state: {@link #SERVER_CHOICE})
		 * 	<li>15: The client requested an update (state: {@link #UPDATE}
		 * </ui>
		 */
		REQUEST,

		/**
		 * At this state the client is sent cache indices which,
		 * when received by the client, update the user's local
		 * cache to match the server's.
		 */
		UPDATE,

		/**
		 * At this state the server decides the best login server
		 * choice for the user.
		 */
		SERVER_CHOICE,

		/**
		 * At this state the server has finished decoding and now
		 * provides the client with information to login after
		 * authenticating the username and password sent by the client.
		 * 
		 * <p>The server at this time swaps the pipeline's decoder to
		 * {@link #Decoder}.</p>
		 */
		GAME;

	}

	/**
	 * Constructs a new front decoder which passes <code>false</code>
	 * to the parent decoder, making the decoding stage not unfold.
	 * It also checkpoints the state {@link State#REQUEST}.
	 */
	public FrontDecoder() {
		super(false);
		checkpoint(State.REQUEST);
	}

	/**
	 * The client receives this response to get ready for an update.
	 */
	private static final byte[] INITIAL_RESPONSE = {
		0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0
	};

	/**
	 * This singleton instance is used to generate random numbers
	 * during the decoding process.
	 */
	private static final SecureRandom RANDOM = new SecureRandom();
	
	/**
	 * Used to log component messages out towards the console during
	 * the decoding stages.
	 */
	private static final Logger logger = Logger.getLogger(FrontDecoder.class.getName());

	/**
	 * This key is used for security, in order to make sure
	 * the client who has began logging in is also the same
	 * one during the authentication.
	 */
	private long serverKey;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, State state) {
		switch (state) {
		default:
		case REQUEST:
			/*
			 * The server requires at least one byte
			 * to be read for the request operation code.
			 */
			if (buffer.readableBytes() < 1) {
				return false;
			}

			/*
			 * We read an unsigned byte which contains
			 * the operation code of the request.
			 */
			switch (buffer.readUnsignedByte()) {
			case 14:
				checkpoint(State.SERVER_CHOICE);
				/*
				 * We return back true because we did read.
				 */
				return true;
			}

			/*
			 * If the value was not specified we return
			 * false because the reading was not successful.
			 */
			return false;
		case SERVER_CHOICE:
			/*
			 * The server requires at least one byte
			 * to be read for the name hash.
			 */
			if (buffer.readableBytes() < 1) {
				return false;
			}

			/*
			 * We read an unsigned byte value. This value is used
			 * to find the best login server for a user.
			 */
			@SuppressWarnings("unused")
			int nameHash = buffer.readUnsignedByte();

			/*
			 * To the channel we write a response.
			 */
			channel.write(new OutBuffer()
					/*
					 * These are the initial login response
					 * keys which we write to the buffer.
					 */
					.write(INITIAL_RESPONSE)
					/*
					 * This is a notification key which might
					 * be there for some authentication, but as
					 * far as the client shows, this is used to
					 * separate the randomly generated server key
					 * from the initiial response buffers.
					 */
					.write(0)
					/*
					 * We generate a random long using the SecureRandom
					 * constant and set it to the field serverKey
					 */
					.writeLong(serverKey = RANDOM.nextLong()));

			/*
			 * We checkpoint the GAME state.
			 */
			checkpoint(State.GAME);

			/*
			 * Return back true, because we did
			 * read from the buffer and the decoding
			 * state was completed successfully.
			 */
			return true;
		case GAME:
			/*
			 * The server requires at least two bytes
			 * of data to be read. The first is the login
			 * operation code and the second is the login
			 * packet's size.
			 */
			if (buffer.readableBytes() < 2) {
				return false;
			}

			/*
			 * We read an operation code which signals what
			 * type of login request should be serviced.
			 */
			int loginOpCode = buffer.readUnsignedByte();

			/*
			 * We switch the operation code to a case-system.
			 * Upon the right case the login will be serviced
			 * as so.
			 */
			switch (loginOpCode) {
			/*
			 * Case 16 is a normal connection.
			 */
			case 16:
			/*
			 * Case 18 is a reconnection. Reconnections take
			 * place when the game server drops the user and
			 * the client quickly reconnects the user.
			 */
			case 18:
				/*
				 * Read the size of the login packet.
				 */
				int loginSize = buffer.readUnsignedByte();

				/*
				 * Calculcate the encrypted buffer size. 
				 */
				int loginSizeE = loginSize - (36 + 1 + 1 + 2);

				/*
				 * A size, regardless of what that size measures
				 * cannot be less than 1.
				 * 
				 * Considering we cannot instantize a new buffer
				 * with negative or no capacity, we'll end the
				 * connection.
				 */
				if (loginSizeE < 1) {
					channel.close();
					return false;
				}

				/*
				 * The client reported the buffer size to the server.
				 * If the buffer has less than the size it's impossible
				 * to read and in the future some sort of error will occur.
				 */
				if (buffer.readableBytes() < loginSize) {
					return false;
				}

				/*
				 * This is yet another security check. We read an unsigned
				 * byte from the buffer which must be equivalent to 255.
				 */
				if (buffer.readUnsignedByte() != 255) {
					channel.close();
					return false;
				}

				/*
				 * This is the client build which is used to associate
				 * the version of the client. Because the decoding stage
				 * is setup for a specific protocol (in our case 317) we'll
				 * stop the decoding if it doesn't match our number.
				 */
				if (buffer.readUnsignedShort() != 317) {
					channel.close();
					return false;
				}

				/*
				 * This value indicates whether or not the client is in
				 * high memory version. If so, the client displays various
				 * detail objects like grass and flowers. In the 317 protocol
				 * thee server does not send sounds to low memory version clients.
				 */
				@SuppressWarnings("unused")
				boolean highMemoryVersion = buffer.readUnsignedByte() == 0;

				/*
				 * The buffer skips over these values. They are known as
				 * cache indices, as they separate the index form inside
				 * the game cache.
				 */
				for (int i = 0; i < 9; i++) buffer.readInt();
				
				/*
				 * We subtract one from the size because it already included
				 * the size byte.
				 */
				loginSizeE--;

				/*
				 * Here the client reports the expected encrypted buffer size
				 * to the server. The server also already calculated the size,
				 * so these must match.
				 */
				if (buffer.readUnsignedByte() != loginSizeE) {
					channel.close();
					return false;
				}

				/*
				 * We now check if the reported RSA code is 10.
				 * 
				 * This could be variable if an RSA generator was
				 * enabled however most clients have it disabled.
				 */
				if (buffer.readUnsignedByte() != 10) {
					channel.close();
					return false;
				}

				/*
				 * The client reports it's own generated random key
				 * which is unique to the connection session. 
				 */
				@SuppressWarnings("unused")
				long clientKey = buffer.readLong();

				/*
				 * The server now reads the randomly generated key
				 * which was sent to the client previously.
				 * 
				 * If they do not match a connection interference
				 * might have taken place therefore we end service.
				 */
				if (buffer.readLong() != serverKey) {
					channel.close();
					return false;
				}

				/*
				 * This is the user identification key.
				 * 
				 * Some clients send random UID keys. Only check
				 * the key's value if your client has it enabled.
				 */
				@SuppressWarnings("unused")
				int uid = buffer.readInt();

				/*
				 * This is the request's username. We read it from the buffer
				 * using some utilities to format the name as well.
				 */
				String username = NameUtils.formatName(BufferUtils.readString(buffer));

				/*
				 * This is the request's password.
				 */
				String password = BufferUtils.readString(buffer);

				/*
				 * We output the request towards the console.
				 */
				logger.info("Login request: (" + username + "," + password + ")");
				
				/*
				 * A new game session is created to encapsulate the entry
				 * username and password, along with the connection channel.
				 */
				Session session = new Session(username, password, channel);
				
				/*
				 * And register that new instance to the world, which returns
				 * back our needed result.
				 */
				Result result = World.getSingleton().register(session);
				
				/*
				 * Inside this block contains command to execute when the player
				 * already has an account.
				 */
				if (result != null) {

					/*
					 * And grab the result's return code attachment to write out.
					 * This code tells the client what messages to display in a failed
					 * login attempt.
					 */
					buffer.writeByte(result.<Integer>getAttachment("returnCode"));

					/*
					 * If the result's return code attachment was not the success code
					 * we end the connection.
					 */
					if (result.<Integer>getAttachment("returnCode") != ReturnCodes.SUCCESS) {
						channel.close();
						return false;
					}
					
					/*
					 * The player instance is now retrieved from the result. This object
					 * serves to represent the player model in the world.
					 */
					Player player = result.<Player>getAttachment("player");
					
					/*/////////////////////////////////////////////////////////////////////
					
					 * We now write out flags which were attached to the result which     *
					 * configure the client to perform certain actions while the user     *
					 * is playing.                                                        *
					
					/////////////////////////////////////////////////////////////////////*/
					
					/*
					 * And now we write the player's right level crown towards the buffer.
					 */
					buffer.writeByte(player.getRight().getCrown());
					
					/*
					 * After whether or not they are flagged for botting will be written.
					 */
					buffer.writeByte(PrimitiveUtils.toInteger(player.isFlagged()));
					
					/*
					 * Finally the login packet (which is actually a bundle of other packets
					 * which are needed to complete the process) is sent.
					 */
					player.getPacketSender().initialize();

					/*
					 * With all said and done we return back true because of all
					 * the writing and reading we've done.
					 */
					return true;
				}
			/*
			 * Any other cases cannot be serviced.
			 */
			default:
				return false;
			}
		}
	}

}