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

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import us.nital.Constants;
import us.nital.net.packet.*;

/**
 * Decodes an incoming message into a {@link Packet}. The decoded object
 * is then sent to a {@link PacketDispenser} to be dispensed to it's
 * appropriate {@link PacketHandler} via the channel handler associated
 * with the pipeline.
 * 
 * @author Thomas Nappo
 */
public class Decoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		/*
		 * Since the decoder is going to be reading the
		 * operation code, the buffer must be readable.
		 */
		if (!buffer.readable()) {
			return null;
		}

		/*
		 * The is the operation code which identifies the
		 * appropriate packet size and handler.
		 */
		int opCode = buffer.readUnsignedByte();

		/*
		 * We use the packet size array at the operation code's
		 * index to get the appropriate size.
		 */
		int size = Constants.PACKET_SIZES[opCode];

		/*
		 * If the size is unspecified by the size array we set
		 * it equal to the number of readable bytes left in the
		 * buffer.
		 */
		if (size < 0) {
			size = buffer.readableBytes();
		}

		/*
		 * If there are less readable bytes in the buffer than the
		 * expected packet size we cannot continue decoding.
		 */
		if (buffer.readableBytes() < size) {
			return null;
		}

		/*
		 * We construct the packet object using the buffer and the
		 * operation code.
		 */
		Packet packet = new Packet(opCode, buffer);

		/*
		 * Return back the encoded packet object.
		 */
		return packet;
	}

}