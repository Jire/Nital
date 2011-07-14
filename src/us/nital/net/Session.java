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

package us.nital.net;

import org.jboss.netty.channel.Channel;

/**
 * Represents a connection session between the server and
 * a user client by encapsulating data which is related to
 * the single session.
 * 
 * @author Thomas Nappo
 */
public class Session {

	/**
	 * The session's username which the user connected with.
	 */
	private final String username;

	/**
	 * Gets the session's {@link #username}.
	 * @return The session's username which the user connected with.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * The session's password which the user connected with.
	 */
	private final String password;

	/**
	 * Gets the session's {@link #password}.
	 * @return The session's password which the user connected with.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * The connection channel which holds the stream towards
	 * the server and user client.
	 */
	private Channel channel;

	/**
	 * Gets the session's {@link #channel}.
	 * @return The connection channel which holds the stream towards
	 * the server and user client.
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Constructs a new session.
	 * @param username The session's connection username.
	 * @param password The session's connection password.
	 * @param channel The session's connection channel which holds 
	 * the stream towards the server and user client.
	 */
	public Session(String username, String password, Channel channel) {
		this.username = username;
		this.password = password;
		this.channel = channel;
	}

	/**
	 * Constructs a new session with no channel.
	 * @param username The session's connection username.
	 * @param password The session's connection password.
	 */
	public Session(String username, String password) {
		this(username, password, null);
	}

}