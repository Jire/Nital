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

/**
 * This interface provides the blueprints for a packet handler.
 * 
 * @author Thomas Nappo
 */
public interface PacketHandler {

	/**
	 * Called when the packet is handled.
	 * 
	 * <p>This method should utilize the reader <tt>in</tt> 
	 * to perform the command that handles the packet.</p>
	 * 
	 * @param in The input source which can be utilized by
	 * the handler to retrieve information from the packet.
	 */
	public void handle(InBuffer in);

}