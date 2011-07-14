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

package us.nital.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * Represents a data packet which the server has received.
 * 
 * <p>The network builds packets to dispense them to their
 * appropriate handlers.</p>
 * 
 * @author Thomas Nappo
 */
public class Packet {

	/**
	 * A type of packet. This marks the additions needed 
	 * and the type of recognition variables which need
	 * to be also attributed towards an outgoing buffer.
	 * 
	 * @author Thomas Nappo
	 */
	public static enum Type {

		/**
		 * This packet type is fixed.
		 * Nothing will change during encoding.
		 */
		FIXED,

		/**
		 * This packet type notifies that a variation of one 
		 * <code>byte</code> will be attributed during encoding.
		 */
		VAR_BYTE,

		/**
		 * This packet type notifies that a variation of one 
		 * <code>short</code> will be attributed during encoding.
		 */
		VAR_SHORT;

	}

	/**
	 * The operation code which is used to associate
	 * the data piece with it's handler.
	 */
	private final int opCode;

	/**
	 * Gets the packet's {@link #opCode}.
	 * @return The operation code which is used to associate
	 * the data piece with it's handler.
	 */
	public int getOpCode() {
		return opCode;
	}

	/**
	 * The type of packet. This marks the additions needed and 
	 * the type of recognition variables which need to be also 
	 * attributed towards an outgoing buffer.
	 */
	private final Type type;

	/**
	 * Gets the packet's {@link #type}.
	 * @return The type of packet. This marks the additions needed and 
	 * the type of recognition variables which need to be also 
	 * attributed towards an outgoing buffer.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * An instance of a dynamic buffer which is utilized for children
	 * of a packet, so that it may only publicize methods of the buffer
	 * which are specific to that child.
	 */
	protected ChannelBuffer buf;

	/**
	 * Gets the packet's internal buffer.
	 * @return An instance of a dynamic buffer which is utilized for children
	 * of a packet, so that it may only publicize methods of the buffer
	 * which are specific to that child.
	 */
	public ChannelBuffer getInternalBuffer() {
		return buf;
	}

	/**
	 * Constucts a new packet.
	 * @param opCode The operation code which is used to associate
	 * the data piece with it's handler.
	 * @param type The type of packet. This marks the additions needed and 
	 * the type of recognition variables which need to be also 
	 * attributed towards an outgoing buffer.
	 * @param buf A specific payload buffer to use as the packet's buffer.
	 */
	public Packet(int opCode, Type type, ChannelBuffer buf) {
		this.opCode = opCode;
		this.type = type;
		this.buf = buf;
	}

	/**
	 * Constucts a new packet with a dynamic buffer.
	 * @param opCode The operation code which is used to associate
	 * the data piece with it's handler.
	 * @param type The type of packet. This marks the additions needed and 
	 * the type of recognition variables which need to be also 
	 * attributed towards an outgoing buffer.
	 */
	public Packet(int opCode, Type type) {
		this(opCode, type, ChannelBuffers.dynamicBuffer());
	}

	/**
	 * Constucts a new fixed packet.
	 * @param opCode The operation code which is used to associate
	 * the data piece with it's handler.
	 * @param buf A specific payload buffer to use as the packet's buffer.
	 */
	public Packet(int opCode, ChannelBuffer buf) {
		this(opCode, Type.FIXED, buf);
	}

	/**
	 * Constructs a new fixed raw packet.
	 * @param buf A specific payload buffer to use as the packet's buffer.
	 */
	public Packet(ChannelBuffer buf) {
		this(-1, buf);
	}

	/**
	 * Constructs a new fixed packet with a dynamic buffer.
	 * @param opCode The operation code which is used to associate
	 * the data piece with it's handler.
	 */
	public Packet(int opCode) {
		this(opCode, Type.FIXED);
	}

	/**
	 * Returns the backing <code>ChannelBuffer</code> of the packet.
	 * @return The backing {@link #buf} of the packet.
	 */
	public ChannelBuffer getPayload() {
		return buf;
	}

	/**
	 * Gets the number of readable bytes in the packet.
	 * @return The length of the packet.
	 */
	public int getLength() {
		return buf.readableBytes();
	}

	/**
	 * Checks if the packet is raw.
	 * @return <code>true</code> if the operation code
	 * is equal to <tt>-1</tt>.
	 */
	public boolean isRaw() {
		return opCode == -1;
	}

}