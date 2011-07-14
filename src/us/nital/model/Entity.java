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
 * An entity serves to represent any roaming model of the game. All
 * entities hold a position along with functionality to change that position.
 * They also hold an {@link #index} which marks their position on their
 * parenting {@link EntityList} encapsulator.
 * 
 * @author Thomas Nappo
 */
public abstract class Entity {

	/**
	 * The entity's representative position on the grid map.
	 */
	private Position position;

	/**
	 * Gets the {@link #position} of the entity.
	 * @return The entity's representative position
	 * on the grid map.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the entity's {@link #position}.
	 * @param position The new position of the entity.
	 */
	public void setPosition(Position position) {
		this.position = position;
		this.hashCode();
	}

	/**
	 * The entities enlisted index inside it's appropriate
	 * {@link EntityList} of the {@link World} which it is active.
	 */
	private final int index;

	/**
	 * Constructs a new entity.
	 * @param index The entity's enlisted index inside it's appropriate
	 * {@link EntityList} of the world which it is active.
	 */
	public Entity(int index) {
		this.index = index;
	}

	/**
	 * This method has been overrided in entities to
	 * return the entity's {@link #index} value.
	 * @see {@link Object#hashCode}
	 */
	@Override
	public int hashCode() {
		return index;
	}

	/**
	 * This method has been overridden in entities to
	 * be based upon the entity's {@link #index}.
	 * @return <code>true</code> if both entities {@link #hashCode}'s
	 * match, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		/*
		 * Should the object which is checked not be an instance of
		 * an entity, they cannot be equivalent.
		 */
		if (!(o instanceof Entity))
			return false;

		/*
		 * Otherwise we return true should the object's and this
		 * entity instance have matching hash codes.
		 */
		return ((Entity) o).hashCode() == hashCode();
	}

}