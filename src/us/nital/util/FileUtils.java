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

import java.io.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * A utility class for dealing with writing/reading disk files.
 * 
 * @author Thomas Nappo
 * @author Steven Galarza
 */
public class FileUtils {

	/**
	 * The size of the buffer payload capacities for the files
	 * we are dealing with.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * Creates a channel buffer payload from a file.
	 * @param name The name of the disk file.
	 * @return The builded {@link ChannelBuffer} which contains the
	 * binary data which was read from the file.
	 * @throws IOException Should any I/O exceptions occur.
	 */
	public static ChannelBuffer buildBufferFromFile(String name) throws IOException {
		/*
		 * We create a new input stream equivalent to a
		 * construction using a file input stream towards
		 * the file name entered in the parameters.
		 */
		InputStream in = new FileInputStream(name);

		/*
		 * Afterwards a new dynamic buffer is instantized.
		 */
		ChannelBuffer build = ChannelBuffers.dynamicBuffer();

		/*
		 * An array of data bytes with a capacity equivalent 
		 * to the constant {BUFFER_SIZE} is created.
		 * 
		 * This array will be used to transfer the actual
		 * buffer's data towards.
		 */
		byte[] data = new byte[BUFFER_SIZE];

		/*
		 * This integer is a mark value. It is used by the
		 * while loop below.
		 */
		int read;

		/*
		 * While the next piece of information inside the
		 * is not equivalent to -1 we transfer the data towards
		 * the array which was created at preceeding command.
		 */
		while ((read = in.read(data, 0, BUFFER_SIZE)) != -1) {
			/*
			 * We then transfer the information towards that array.
			 */
			build.writeBytes(data, 0, read);
		}

		/*
		 * When all is done we close the input stream to prevent
		 * later usage along with allowing another stream to be
		 * opened towards the file.
		 */
		in.close();

		/*
		 * The builded channel buffer is returned back as a product.
		 */
		return build;
	}

	/**
	 * Writes a {@link ChannelBuffer} to a disk file.
	 * @param name The name of the disk file.
	 * @param buf The buffer which is the container of the
	 * binary data to write.
	 */
	public static void writeBufferToFile(String name, ChannelBuffer buf) throws IOException {
		OutputStream out = new FileOutputStream(name);
		out.write(buf.array(), 0, buf.readableBytes());
		out.flush();
		out.close();
	}

}