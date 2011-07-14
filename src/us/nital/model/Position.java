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

/**
 * A position serves to represent a tile on the map grid.
 * 
 * @author Thomas Nappo
 */
public class Position {

	/**
	 * The x coordinate of the position.
	 */
	private final int x;

	/**
	 * The y coordinate of the position.
	 */
	private final int y;

	/**
	 * The z (height) coordinate of the position.
	 */
	private final int z;

	/**
	 * Gets the position's {@link #x} coordinate.
	 * @return The x coordinate of the position.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the position's {@link #y} coordinate.
	 * @return The y coordinate of the position.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the position's {@link #z} coordinate.
	 * @return The z (height) coordinate of the position.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Constructs a new position.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 * @param z The z (height) coordinate of the position.
	 */
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs a new position.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 */
	public Position(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * Creates a new position.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 * @param z The z (height) coordinate of the position.
	 */
	public static Position create(int x, int y, int z) {
		return new Position(x, y, z);
	}

	/**
	 * Creates a new position.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 */
	public static Position create(int x, int y) {
		return new Position(x, y);
	}

}