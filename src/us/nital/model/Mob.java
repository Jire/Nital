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

package us.nital.model;

import us.nital.util.EntityList;

/**
 * A mob serves to represent an entity which is mobile. All mobs
 * hold a walking queue which is used for it's travelling.
 * 
 * @author Thomas Nappo
 */
public abstract class Mob extends Entity {

	/**
	 * This is the mob's queue for walking points. It holds an internal
	 * queue of positions which are processed to move the entity along
	 * the tile grid.
	 */
	private WalkingQueue walkingQueue = new WalkingQueue(this);

	/**
	 * Gets the mob's {@link #walkingQueue}.
	 * 
	 * <p><i>Some functions result in possibly unexpected actions, be sure
	 * to review the documentation provided by the queue's internal methods
	 * before use.</i></p>
	 * 
	 * @return The walking queue of the mob which holds an internal
	 * queue of positions which are processed to move the entity along
	 * the tile grid.
	 * @see {@link WalkingQueue}'s internal structure if you are unsure
	 * about how adding steps works.
	 */
	public WalkingQueue getWalkingQueue() {
		return walkingQueue;
	}

	/**
	 * Constructs a new mob.
	 * @param index The mob's enlisted index inside it's appropriate
	 * {@link EntityList} of the world which it is active.
	 */
	public Mob(int index) {
		super(index);
	}

}