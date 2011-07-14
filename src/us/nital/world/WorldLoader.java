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

/**
 * This interface provides the blueprints for functionality that
 * creates {@link Result} objects and saves/loads users in the world.
 * 
 * @author Thomas Nappo
 */
public interface WorldLoader {

	/**
	 * Processes a result for a player.
	 * @param player The player to process the result for.
	 * @return The result object which contains the attachments.
	 */
	public Result process(Player player);

	/**
	 * Saves a progress to an appropriate output disk file.
	 * @param saveable The saveable obejct implementation to save.
	 * @return <code>true</code> if the save was successful.
	 */
	public boolean save(Saveable saveable);

	/**
	 * Loads progress from a disk file.
	 * @param saveable The saveable object implementation to load for.
	 * @return <code>true</code> if the load was successful.
	 */
	public boolean load(Saveable saveable);

}