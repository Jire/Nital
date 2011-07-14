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
 * An object which acts as a writing buffer. You can write data to 
 * the buffer which stays recording. When the writing process is done
 * you can write the <tt>OutBuffer</tt> object to the encoder for
 * shipping towards the connected client.
 * 
 * @author Thomas Nappo
 */
public class OutBuffer extends Packet {

	/**
	 * Constructs a new output buffer.
	 * @param opCode The operation code which is used 
	 * to associate the data piece with it's handler.
	 * @param type The type of packet. This marks the additions needed and 
	 * the type of recognition variables which need to be also 
	 * attributed towards an outgoing buffer.
	 */
	public OutBuffer(int opCode, Type type) {
		super(opCode, type);
	}

	/**
	 * Constructs a new fixed output buffer.
	 * @param opCode The operation code which is used 
	 * to associate the data piece with it's handler.
	 */
	public OutBuffer(int opCode) {
		super(opCode);
	}

	/**
	 * Constructs a new fixed raw output buffer.
	 * @param buf A specific payload buffer to use as the internal buffer.
	 */
	public OutBuffer(ChannelBuffer buf) {
		super(buf);
	}

	/**
	 * Constructs a new raw fixed output buffer.
	 */
	public OutBuffer() {
		super(-1);
	}

	/**
	 * Writes one <code>byte</code> to the buffer.
	 * @param v The byte's value.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer write(int v) {
		buf.writeByte(v);
		return this;
	}

	/**
	 * Writes numerous <code>byte</code>s to the buffer.
	 * @param v The array of bytes to write.
	 * 
	 * <p><i>Be sure the provided length of this variational 
	 * argument attains a value of <tt>1</tt> or greater.</i></p>
	 * 
	 * @return This writer's instance, for chaining.
	 * @throws IllegalArgumentException should the length of
	 * <tt>v</vv> (the singleton variational argument parameter)
	 * be less than <tt>1</tt>.
	 */
	public OutBuffer write(byte... v) {
		if (v.length < 1) {
			throw new IllegalArgumentException("Must write at least one value.");
		}
		buf.writeBytes(v);
		return this;
	}

	/**
	 * Writes one <code>byte</code> special type <tt>A</tt> to the buffer.
	 * @param v The value of the byte.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeByteA(int v) {
		return write(v + 128);
	}

	/**
	 * Writes one <code>byte</code> special type <tt>C</tt> to the buffer.
	 * @param v The value of the byte.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeByteC(int v) {
		return write(- v);
	}

	/**
	 * Writes one <code>byte</code> special type <tt>S</tt> to the buffer.
	 * @param v The value of the byte.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeByteS(int v) {
		return write(128 - v);
	}

	/**
	 * Writes one <code>short</code> to the buffer.
	 * @param v The short's value.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeShort(int v) {
		buf.writeShort(v);
		return this;
	}

	/**
	 * Writes numerous <code>short</code>s to the buffer.
	 * @param v The array of shorts to write.
	 * 
	 * <p><i>Be sure the provided length of this variational 
	 * argument attains a value of <tt>1</tt> or greater.</i></p>
	 * 
	 * @return This writer's instance, for chaining.
	 * @throws IllegalArgumentException should the length of
	 * <tt>v</vv> (the singleton variational argument parameter)
	 * be less than <tt>1</tt>.
	 */
	public OutBuffer writeShort(int... v) {
		if (v.length < 1) {
			throw new IllegalArgumentException("Must write at least one value.");
		}
		for (int i : v) writeShort(i);
				return this;
	}

	/**
	 * Writes one <code>int</code> to the buffer.
	 * @param v The integer's value.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeInt(int v) {
		buf.writeInt(v);
		return this;
	}

	/**
	 * Writes numerous <code>int</code>s to the buffer.
	 * @param v The array of integers to write.
	 * 
	 * <p><i>Be sure the provided length of this variational 
	 * argument attains a value of <tt>1</tt> or greater.</i></p>
	 * 
	 * @return This writer's instance, for chaining.
	 * @throws IllegalArgumentException should the length of
	 * <tt>v</vv> (the singleton variational argument parameter)
	 * be less than <tt>1</tt>.
	 */
	public OutBuffer writeInt(int... v) {
		if (v.length < 1) {
			throw new IllegalArgumentException("Must write at least one value.");
		}
		for (int i : v) writeInt(i);
				return this;
	}

	/**
	 * Writes one <code>long</code> to the buffer.
	 * @param v The long's value.
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeLong(long v) {
		buf.writeLong(v);
		return this;
	}

	/**
	 * Writes numerous <code>long</code>s to the buffer.
	 * @param v The array of longs to write.
	 * 
	 * <p><i>Be sure the provided length of this variational 
	 * argument attains a value of <tt>1</tt> or greater.</i></p>
	 * 
	 * @return This writer's instance, for chaining.
	 * @throws IllegalArgumentException should the length of
	 * <tt>v</vv> (the singleton variational argument parameter)
	 * be less than <tt>1</tt>.
	 */
	public OutBuffer writeLong(long... v) {
		if (v.length < 1) {
			throw new IllegalArgumentException("Must write at least one value.");
		}
		for (long i : v) writeLong(i);
				return this;
	}

	/**
	 * Writes one <code>String</code> to the buffer.
	 * @param v The string's value.
	 * @return The writer's instance, for chaining.
	 */
	public OutBuffer writeString(String v) {
		BufferUtils.writeString(buf, v);
		return this;
	}

	/**
	 * Transfers the specified source array's data to this buffer 
	 * starting at the current writerIndex and increases the writerIndex 
	 * by the number of the transferred bytes (= length).
	 * @return This writer's instance, for chaining.
	 */
	public OutBuffer writeBytes(byte[] src, int srcIndex, int length) {
		buf.writeBytes(src, srcIndex, length);
		return this;
	}

}