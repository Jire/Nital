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

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import us.nital.net.io.OutBuffer;

/**
 * Encodes outgoing {@link OutBuffer}s to be delivered towards a connected channel
 * by encoding them into a channel buffer which is used by the Netty implementation.
 * 
 * @author Thomas Nappo
 * @see {@link OneToOneEncoder}
 */
public class Encoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		/*
		 * The encoder only can encode instances of OutBuffer.
		 */
		if (!(msg instanceof OutBuffer)) {
			throw new UnsupportedEncodingException();
		}

		/*
		 * We convert the message to an OutBuffer object. 
		 */
		OutBuffer out = (OutBuffer) msg;

		/*
		 * If the buffer is raw we've finished the job and return
		 * a wrapped buffer using the message's data as the wrapping array.
		 */
		if (out.isRaw()) {
			return ChannelBuffers.wrappedBuffer(out.getPayload());
		}

		/*
		 * This is the formatted message length.
		 * We add 1 because we'll need one extra byte of
		 * capacity so we can write the operation code as
		 * an unsigned byte into the buffer.
		 */
		int length = out.getLength() + 1;

		/*
		 * Here we need to increase the length for the soon
		 * instantized buffer.
		 */
		switch (out.getType()) {
		case VAR_BYTE:
			/*
			 * We add 1 to the length because according
			 * to the documentation provided by Type#VAR_BYTE
			 * setting an outgoing packet to this type increases
			 * the length by 1 byte.
			 */
			length += 1; // ++ is not used for easy changes
			break;
		case VAR_SHORT:
			/*
			 * We add 2 to the length because according
			 * to the documentation provided by Type#VAR_SHORT
			 * setting an outgoing packet to this type increases
			 * the length by 1 short (which is 2 bytes).
			 */
			length += 2;
			break;
		}

		/*
		 * We create a new buffer instance utilizing that newly
		 * formatted variable length to pass as the new buffer's
		 * capacity.
		 */
		ChannelBuffer buffer = ChannelBuffers.buffer(length);

		/*
		 * This is the reason we increased the length by 1.
		 * Here we write one byte with the value of the operation
		 * code (which can only reach a maximum value of 255).
		 */
		buffer.writeByte(out.getOpCode());

		/*
		 * Here we need to write the length to the buffer.
		 */
		switch (out.getType()) {
		case VAR_BYTE:
			/*
			 * The length is up to a byte long.
			 */
			buffer.writeByte(out.getLength());
			break;
		case VAR_SHORT:
			/*
			 * The length is up to a short long.
			 */
			buffer.writeShort(out.getLength());
			break;
		}

		/*
		 * Finally we can write all the actual data from the
		 * message into the buffer.
		 */
		buffer.writeBytes(out.getPayload());

		/*
		 * We return back our product buffer.
		 */
		return buffer;
	}

}