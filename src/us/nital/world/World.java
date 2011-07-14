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

import us.nital.model.Player;
import us.nital.net.Session;
import us.nital.util.EntityList;
import us.nital.world.impl.GenericWorldLoader;

/**
 * Represents the game world. Within each world holds an
 * {@link EntityList} for each entity type it can contain
 * along with functionality to register and unregister those
 * entities from both view and internal encapsulation.
 * 
 * @author Thomas Nappo
 */
public final class World {

	/**
	 * This instance holds value of the singleton instance
	 * of the world class.
	 */
	private static final World SINGLETON = new World();

	/**
	 * Gets the singleton instance of the server.
	 * @return The one and only instance of the server.
	 */
	public static World getSingleton() {
		return SINGLETON;
	}

	/**
	 * This list encapsulates the players of the world, each assigned with
	 * their own index as the index inside the list.
	 */
	private final EntityList<Player> players = new EntityList<Player>(2000);

	/**
	 * Gets the world's {@link #players} list.
	 * @return This list encapsulates the players of the world, each assigned
	 * with their own index as the index inside the list.
	 */
	public EntityList<Player> getPlayers() {
		return players;
	}

	/**
	 * Loads the world for players by creating {@link Result} objects
	 * which contain attachments which specify information about the
	 * player's load result.
	 */
	private final WorldLoader worldLoader = new GenericWorldLoader();

	/**
	 * Appends a session to be registered.
	 * @param session The session to register.
	 * @return A result containing the <b>returnCode</b> and <b>player</b> or
	 * <code>null</code> if the registration failed.
	 */
	public Result register(Session session) {
		/*
		 * Should there be no available slot, we return back null
		 * to notify the failed registration.
		 */
		if (players.availableSlot() < 0) {
			return null;
		}

		/*
		 * Create a new player with an index equivalent to the next available
		 * slot of the player entity list.
		 */
		Player player = new Player(players.availableSlot(), session);

		/*
		 * If the world's list of players could not add the null
		 * is returned back to notify the failed registration.
		 */
		if (!players.add(player)) {
			return null;
		}

		/*
		 * Otherwise the world loader processes a new player created the
		 * available index.
		 */
		return worldLoader.process(player);
	}

}