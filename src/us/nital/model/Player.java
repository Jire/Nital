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

package us.nital.model;

import us.nital.net.Session;
import us.nital.net.io.InBuffer;
import us.nital.net.io.OutBuffer;
import us.nital.net.packet.PacketSender;
import us.nital.util.EntityList;
import us.nital.util.PrimitiveUtils;
import us.nital.world.Saveable;

/**
 * Each instantation of a player serves to represent
 * a connected user in the world. A player holds a constructed
 * connection object which is utilized for output writing and
 * maintaining a connection line towards the unique user channel.
 * 
 * <p>A player is an implementation of a {@link Saveable}. A generic
 * world loader utilizes the saveable's blueprint methods to read and
 * write the player's game progress.</p>
 * 
 * @author Thomas Nappo
 * @see {@link Mob}
 * @see {@link Saveable}
 */
public class Player extends Mob implements Saveable {

	/**
	 * A player's game rights. This serves to identify what permissions
	 * a user has while interacting with the server.
	 * 
	 * @author Thomas Nappo
	 */
	public static enum Right {

		/**
		 * A player obtaining standard rights have access to all normal
		 * functionality of the world. They do not have the ability to
		 * enforce the rules, only report those breaking the rules.
		 * 
		 * <p><b>Crown:</b> 0</p>
		 */
		STANDARD,

		/**
		 * A player obtaining moderator rights have access to all priviledges
		 * provided to {@link #STANDARD}, however also can enforce rules.
		 * They may also have access to special areas of the game.
		 * 
		 * <p><b>Crown:</b> 1</p>
		 */
		MODERATOR,

		/**
		 * A player obtaining administrator rights has access to all
		 * priviledges provided to {@link #MODERATOR}. They too, however,
		 * are granted all priviledges for interaction with the world and
		 * can take and give priviledges to others (who are below their rights).
		 * 
		 * <p><b>Crown:</b> 2</p>
		 */
		ADMINISTRATOR;

		/**
		 * Retrieves the associated identification crown
		 * number for the right.
		 * @return The right's ordinal index in the enumeration.
		 */
		public int getCrown() {
			return ordinal();
		}

		/**
		 * Retrieves a {@link Right} for an identification
		 * number that corresponds to that right's crown.
		 * @param id The crown identification to find the
		 * right value for.
		 * @return The corresponding right.
		 */
		public static Right forId(int id) {
			/*
			 * Loop through all of the values inside
			 * the enumeration.
			 */
			for (Right right : values()) {
				/*
				 * If the rights ordinal is equivalent to
				 * the identification number (which is in
				 * the parameters) then we return back
				 * that right value.
				 */
				if (right.ordinal() == id)
					return right;
			}

			/*
			 * If we couldn't find a right value inside
			 * the values which matches a value or something 
			 * else went wrong, we return back standard rights.
			 * 
			 * The standard rights are returned back to guarentee
			 * the player no special priviledges during game interaction,
			 * so that the user may not gain higher priviledges than
			 * originally intended.
			 */
			return Right.STANDARD;
		}

	}

	/**
	 * If this flag is set to <code>true</code>, the player is
	 * flagged. When a player is flagged the client will be configured
	 * to send special bot detection packets towards the server so
	 * that they may be determined to, or not to of been using macros.
	 */
	private boolean flagged = false;

	/**
	 * Checks whether or not the player is {@link #flagged}.
	 * 
	 * <p>When a player is flagged the client will be configured to
	 * send special bot detection packets towards the server so that
	 * they may be determined to, or not to of been using macros.</p>
	 * 
	 * @return <code>true</code> the player is flagged, <code>false</code>
	 * otherwise.
	 */
	public boolean isFlagged() {
		return flagged;
	}

	/**
	 * Sets whether or not the player is flagged.
	 * 
	 * <p>When a player is flagged the client will be configured to
	 * send special bot detection packets towards the server so that
	 * they may be determined to, or not to of been using macros.</p>
	 * 
	 * @param flagged The new {@link #flagged} status.
	 */
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	/**
	 * The player's game rights. This serves to identify what permissions
	 * a user has while interacting with the server.
	 */
	private Right right = Right.STANDARD;

	/**
	 * Gets the player's {@link #right}.
	 * @return The player's game rights. This serves to identify what
	 * permissions a user has while interacting with the server.
	 */
	public Right getRight() {
		return right;
	}

	/**
	 * Sets a player's {@link #right}.
	 * @param right The player's game rights. This serves to identify
	 * what permissions a user has while interacting with the server.
	 */
	public void setRight(Right right) {
		this.right = right;
	}

	/**
	 * The player's packet sender which is used to create predefined
	 * packets by giving specific fill-ins towards the build parameters
	 * of the functions which build packets that are within.
	 */
	private final PacketSender packetSender = new PacketSender(this);

	/**
	 * Gets the player's {@link #packetSender}.
	 * @return The player's packet sender which is used to create
	 * predefined packets by giving specific fill-ins towards the
	 * build parameters of the functions which build packets that
	 * are within.
	 */
	public PacketSender getPacketSender() {
		return packetSender;
	}

	/**
	 * The player's connection session which holds their details
	 * along with their connection channel.
	 */
	private Session session;

	/**
	 * Gets the player's {@link #session}.
	 * @return The player's connection session which holds their details
	 * along with their connection channel.
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Constructs a new player.
	 * @param index The player's enlisted index inside it's appropriate
	 * {@link EntityList} of the world which it is active.
	 * @param session The player's connection session which holds their details
	 * along with their connection channel.
	 */
	public Player(int index, Session session) {
		super(index);
		this.session = session;
	}

	@Override
	public void save(OutBuffer buf) {
		buf.writeString(session.getUsername());
		buf.writeString(session.getPassword());

		buf.writeShort(right.getCrown());

		buf.write(PrimitiveUtils.toInteger(isFlagged()));
	}

	@Override
	public void load(InBuffer buf) {
		/*
		 * Before adding to this method, read:
		 * 
		 * 		When loading from the buffer it is best practice to
		 * 		check if the data which is expected to be read from
		 * 		the buffer exists before reading takes place. This way,
		 * 		players who do not have the data present in their current
		 * 		file save still maintain the other data, while the new
		 * 		data will be written when they are saved.
		 */


		/*
		 * We cannot load the player file because
		 * the builded buffer is empty.
		 */
		if (!buf.readable()) {
			return;
		}

		session = new Session(
				buf.readString(), // username
				buf.readString(), // password
				session.getChannel());

		if (buf.readable())
			/*
			 * This sets the player's right level.
			 */
			this.right = Right.forId(buf.readShort());

		if (buf.readable())
			/*
			 * This sets whether or not the player is flagged.
			 */
			this.flagged = buf.readByte() == 1;
	}

}
