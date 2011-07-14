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

import us.nital.model.Player;
import us.nital.net.io.OutBuffer;
import us.nital.net.packet.Packet.Type;

/**
 * A packet sender is used to create predefined packets
 * by giving specific <i>fill-ins</i> towards the build
 * parameters of the functions which build packets that
 * are within.
 * 
 * @author Thomas Nappo
 */
public final class PacketSender {

	/**
	 * Tha parent of the packet sender. 
	 */
	private final Player player;

	/**
	 * Constructs a new packet sender.
	 * @param player The parent of the packet sender.
	 */
	public PacketSender(Player player) {
		this.player = player;
	}

	/**
	 * Writes a message towards the player's connection channel.
	 * @param msg The message object to write.
	 */
	public void write(Object msg) {
		player.getSession().getChannel().write(msg);
	}

	/**
	 * Sends a server message to the client.
	 * 
	 * <p>These are the game messages which appear inside
	 * the chat box.</p>
	 * 
	 * @param message The message's context.
	 */
	public void sendMessage(String message) {
		write(new OutBuffer(253, Type.VAR_BYTE).writeString(message));
	}

	/**
	 * Sends required login packets to the client.
	 */
	public void initialize() {
		/*
		 * This is at the core of the initialize packet.
		 */
		write(new OutBuffer().writeByteA(1).writeByteA(player.hashCode())); // members, index

		/*
		 * After, we preceed to provide the client with standard login functionality.
		 */


		sendMessage("Welcome to RuneScape.");
	}

}