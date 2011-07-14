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

import us.nital.net.io.InBuffer;
import us.nital.net.packet.handlers.*;

/**
 * Maintains a fixed array of {@link PacketHandler} elements to
 * distribute incoming handle requests to their appropriate handlers.
 * 
 * @author Thomas Nappo
 */
public final class PacketDispenser {

	/**
	 * A fixed array of {@link PacketHandler} elements which is used
	 * by the dispenser by grabbing the index (slot) of the array to
	 * find the associated handler implementation.
	 */
	/*
	 * This array of of length 255 because packet operation codes range
	 * from 0-255 as they are written/read as an unsigned byte.
	 */
	private static final PacketHandler[] handlers = new PacketHandler[255];

	/**
	 * Encapsulates the singleton instance of the dispenser.
	 * @author Thomas Nappo
	 */
	private static final class SingletonContainer {
		private static final PacketDispenser INSTANCE = new PacketDispenser();
	}

	/**
	 * Gets the singleton instance of the dispenser.
	 * @return The one and only instance of the dispenser.
	 */
	public static PacketDispenser getSingleton() {
		return SingletonContainer.INSTANCE;
	}

	/**
	 * This packet handler is a dummy handler which is used to 
	 * provide developers with information about unhandled packet 
	 * requests which have been received by the server.
	 */
	private static final PacketHandler DEFAULT = new DefaultPacketHandler();

	/*
	 * The internal componenets within the constructors brackets
	 * should be used to set index slots of the packet handler
	 * array towards specific packet handlers which are associated
	 * with the index (as an operation code).
	 */
	static {

	}

	/**
	 * Dispenses a packet to it's appropriate handler.
	 * @param in The packet child instance. The handlers can 
	 * use this buffer to read information needed to handle
	 * the packet request.
	 */
	public void handle(InBuffer in) {
		/*
		 * If inside the handler array at the input's operation
		 * code is not found we call the default packet handler
		 * to do the job.
		 */
		if (handlers[in.getOpCode()] == null) {
			DEFAULT.handle(in);
			/*
			 * We return back to stop the method from executing
			 * succeeding command.
			 */
			return;
		}
		/*
		 * If there was in fact a match in the handler array
		 * we call the matching index according to the operation
		 * code of the stream.
		 */
		handlers[in.getOpCode()].handle(in);
	}

}