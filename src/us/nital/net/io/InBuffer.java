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

package us.nital.net.io;

import org.jboss.netty.buffer.ChannelBuffer;

import us.nital.net.packet.Packet;
import us.nital.util.BufferUtils;

/**
 * An object which acts as a reading buffer. The network
 * decodes connection packets into these buffers.
 * 
 * <p><i>Some functionality of this buffer is not supported
 * by the RuneScape protocol. Unsupported functions are
 * marked with a unsupported tag, which appears as</i> <b>@unsupported</b></p>
 * 
 * @author Thomas Nappo
 */
public class InBuffer extends Packet {

	/**
	 * Constructs a new input buffer.
	 * @param opCode The operation code which is used 
	 * to associate the data piece with it's handler.
	 * @param type The type of packet. This marks the additions needed and 
	 * the type of recognition variables which need to be also 
	 * attributed towards an outgoing buffer.
	 */
	public InBuffer(int opCode) {
		super(opCode);
	}

	/**
	 * Constructs a new fixed raw input buffer.
	 * @param buf A specific payload buffer to use as the internal buffer.
	 */
	public InBuffer(ChannelBuffer buf) {
		super(buf);
	}

	/**
	 * Reads one <code>byte</code> from the buffer.
	 * @return The next byte in the buffer.
	 */
	public byte readByte() {
		return buf.readByte();
	}

	/**
	 * Reads and then unsigns one <code>byte</code> from the buffer.
	 * @return The next unsigned byte in the buffer.
	 */
	public short readUnsignedByte() {
		return buf.readUnsignedByte();
	}

	/**
	 * Reads one <code>short</code> from the buffer.
	 * @return The next short in the buffer.
	 */
	public short readShort() {
		return buf.readShort();
	}

	/**
	 * Reads and then unsigns one <code>short</code> from the buffer.
	 * @return The next unsigned short in the buffer.
	 */
	public int readUnsignedShort() {
		return buf.readUnsignedShort();
	}

	/**
	 * Reads one <code>short</code> special <tt>A</tt> from the buffer.
	 * @return The next short value in the buffer <code>- 128</code>
	 */
	public int readShortA() {
		return buf.readShort() - 128;
	}

	/**
	 * Reads one <code>short</code> special <tt>C</tt> from the buffer.
	 * @return The next short value in the buffer inverted.
	 */
	public int readShortC() {
		return - buf.readShort();
	}

	/**
	 * Reads one <code>short</code> special <tt>S</tt> from the buffer.
	 * @return <code>128 -</code> the next short value in the buffer.
	 */
	public int readShortS() {
		return 128 - buf.readShort();
	}

	/**
	 * Reads one <code>char</code> from the buffer.
	 * @return The next character in the buffer.
	 * @unsupported The functionality of this method is not supported
	 * by the RuneScape protocol.
	 */
	public char readChar() {
		return buf.readChar();
	}

	/**
	 * Reads one <code>int</code> from the buffer.
	 * @return The next integer in the buffer.
	 */
	public int readInt() {
		return buf.readInt();
	}

	/**
	 * Reads and then unsigns one <code>int</code> from the buffer.
	 * @return The next unsigned integer in the buffer.
	 */
	public long readUnsignedInt() {
		return buf.readUnsignedInt();
	}

	/**
	 * Reads one <code>long</code> from the buffer.
	 * @return The next long in the buffer.
	 */
	public long readLong() {
		return buf.readLong();
	}

	/**
	 * Reads one <code>double</code> from the buffer.
	 * @return The next double in the buffer.
	 * @unsupported The functionality of this method is not supported
	 * by the RuneScape protocol.
	 */
	public double readDouble() {
		return buf.readDouble();
	}

	/**
	 * Reads one <code>float</code> from the buffer.
	 * @return The next float in the buffer.
	 * @unsupported The functionality of this method is not supported
	 * by the RuneScape protocol.
	 */
	public float readFloat() {
		return buf.readFloat();
	}

	/**
	 * Reads one <code>String</code> from the buffer.
	 * @return The next string in the buffer.
	 */
	public String readString() {
		return BufferUtils.readString(buf);
	}

	/**
	 * Checks whether or not the buffer is readable.
	 * @return <code>true</code> if the buffer has greater
	 * than <tt>0</tt> readable bytes.
	 */
	public boolean readable() {
		return buf.readable();
	}

	/**
	 * Gets the number of readable bytes in the buffer.
	 * @return The internal buffer's readable bytes.
	 */
	public int readableBytes() {
		return buf.readableBytes();
	}

}