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

package us.nital.world;

/**
 * This class serves to hold constant return code values.
 * 
 * <p>Each is a marking code that tells the client the server's
 * response on a login attempt.</p>
 * 
 * @author Thomas Nappo
 */
public final class ReturnCodes {

	/**
	 * This return code makes the client wait 2 seconds then try
	 * to login once more.
	 */
	public static final int WAIT_2_SEC = 1;

	/**
	 * This return code notifies the client that the
	 * login request was a success.
	 */
	public static final int SUCCESS = 2;

	/**
	 * This return code notifies the client that the
	 * login request had invalid details.
	 * 
	 * <p><b>Message:</b> Invalid username or password.</p>
	 */
	public static final int INVALID_DETAILS = 3;

	/**
	 * This return code notifies the client that the
	 * login request failed because the user is banned.
	 * 
	 * <p><b>Message:</b> Your account has been disabled. Please
	 * check your message-center for details.</p>
	 */
	public static final int BANNED = 4;

	/**
	 * This return code notifies the client that the login
	 * request failed because the user is already logged in.
	 * 
	 * <p><b>Message:</b> Your account is already logged in.
	 * Try again in 60 secs...</p>
	 */
	public static final int ALREADY_LOGGED_IN = 5;

	/**
	 * This return code notifies the client that it's
	 * version is outdated.
	 * 
	 * <p><b>Message:</b> RuneScape has been updated! Please
	 * reload this page.</p>
	 */
	public static final int OUTDATED_CLIENT = 6;

	/**
	 * This return code notifies the client that the
	 * login request failed because the world is full.
	 * 
	 * <p><b>Message:</b> This world is full. Please use a different
	 * world.</p>
	 */
	public static final int WORLD_FULL = 7;

	/**
	 * This return code notifies the client that the login
	 * server is not available.
	 * 
	 * <p><b>Message:</b> Unable to connect. Login server offline.</p>
	 */
	public static final int LOGIN_SERVER_OFFLINE = 8;

	/**
	 * This return code notifies that the user already has
	 * too many connections from the same IP address.
	 * 
	 * <p><b>Message:</b> Login limit exceeded. Too many connections
	 * from your address.</p>
	 */
	public static final int LOGIN_LIMIT_EXCEEDED = 9;

	/**
	 * This return code notifies the client that the session key
	 * IDs were modified.
	 * 
	 * <p><b>Message:</b> Unable to connect. Bad session id.</p>
	 */
	public static final int BAD_SESSION_ID = 10;

	/**
	 * This return code notifies the client that the login server
	 * rejected their request for various reasons.
	 * 
	 * <p><b>Message:</b> Login server rejected session. Please try again.</p>
	 */
	public static final int LOGIN_SERVER_REJECTED = 11;

	/**
	 * This return code notifies the user that they are a non-member and
	 * are trying to login to a members world.
	 * 
	 * <p><b>Message:</b> You need a members account to login to this world.
	 * Please subscribe, or use a different world.</p>
	 */
	public static final int MEMBERS_WORLD = 12;

	/**
	 * This return code notifies the client that the login request could not
	 * be fully interpreted.
	 * 
	 * <p><b>Message:</b> Could not complete login. Please try using a different world.</p>
	 */
	public static final int COULD_NOT_COMPLETE_LOGIN = 13;

	/**
	 * This return code notifies the user that an update in the world they
	 * are trying to connect to is currently in a system update.
	 * 
	 * <p><b>Message:</b> The server is being updated. Please wait 1 minute and try again.</p>
	 */
	public static final int UPDATE_IN_PROGRESS = 14;

	/**
	 * This return code notifies the user that their failed login attempts
	 * has passed the limit for the time, and to wait 1 minute and try again.
	 * 
	 * <p><b>Message:</b> Login attempts exceeded. Please wait 1 minute and try again.</p>
	 */
	public static final int LOGIN_ATTEMPTS_EXCEEDED = 16;

	/**
	 * This return code notifies the user that their account is standing in
	 * a members-only area and must login to a members server to remove it from
	 * the area.
	 * 
	 * <p><b>Message:</b> You are standing in a members-only area.
	 * To play on this world move to a free area first.</p>
	 */
	public static final int MEMBERS_AREA = 17;

	/**
	 * This return code notifies the client that the login server they attempted to
	 * ues is not valid.
	 * 
	 * <p><b>Message:</b> Invalid loginserver requested. Please try using a different world.</p>
	 */
	public static final int INVALID_LOGIN_SERVER = 20;

	/**
	 * This return code notifies the client that they have just left another world and their profile
	 * will be transfered in a number of seconds.
	 * 
	 * <p><b>Message:</b> You have only just left another world. Your profile will be transferred in: (number) seconds.</p>
	 */
	public static final int JUST_LEFT_ANOTHER_WORLD = 21;

}