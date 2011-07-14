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

package us.nital.net.packet.handlers;

import java.util.logging.Logger;

import us.nital.net.io.InBuffer;
import us.nital.net.packet.PacketHandler;
import us.nital.net.packet.PacketDispenser;

/**
 * This packet handler is a dummy handler which is used
 * to provide developers with information about unhandled
 * packet requests which have been received by the server.
 * @author Thomas Nappo
 * @see {@link PacketDispenser} if you're unclear about how
 * the packet system works.
 */
public class DefaultPacketHandler implements PacketHandler {

	/**
	 * This singleton logger instance can be used for logging various
	 * unhandled packet information to the console so that developers may
	 * acquire debugging information, and utilize it to handle more packets.
	 */
	private static final Logger logger = Logger.getLogger(DefaultPacketHandler.class.getName());

	@Override
	public void handle(InBuffer in) {
		logger.info("Unhandled packet: " + in.getOpCode());
	}

}