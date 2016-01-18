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

package us.nital.util;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * A utility class which allows extra functionality to {@link ChannelBuffer}s.
 * 
 * @author Graham Edgecombe
 * @author Thomas Nappo
 */
public class BufferUtils {

	/**
	 * Reads a string from a buffer.
	 * @param buf The buffer to read from.
	 * @return The product string which was builded by
	 * reading the buffer.
	 */
	public static String readString(ChannelBuffer buf) {
		/*
		 * Create a new string builder to use.
		 */
		StringBuilder bldr = new StringBuilder();
		/*
		 * This value is not set for it is used as a check
		 * component, and then as the proper adding byte value.
		 */
		byte b;
		/*
		 * While the buffer is still reading and the next byte
		 * value in the buffer isn't equivalent to 10 we'll append
		 * the byte as a character towards the string builder.
		 */
		while (buf.readable() && (b = buf.readByte()) != 10) {
			bldr.append((char) b);
		}
		/*
		 * When alls done we return back our product by converting
		 * the string builder into a string.
		 */
		return bldr.toString();
	}

	/**
	 * Writes a string object to a buffer.
	 * @param buf The buffer to write to.
	 * @param string The string to write.
	 */
	public static void writeString(ChannelBuffer buf, String string) {
		/*
		 * For every character in the string, a byte value is
		 * written out equivalent to the character value.
		 */
		for (char c : string.toCharArray()) {
			buf.writeByte(c);
		}
		/*
		 * We write a value of 10 to the end of the 
		 * buffer to signify that we're finished.
		 */
		buf.writeByte(10);
	}

}
