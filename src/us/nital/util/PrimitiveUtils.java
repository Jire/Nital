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

package us.nital.util;

/**
 * Utility methods in this class deal with primitive data types.
 * 
 * @author Thomas Nappo
 */
public class PrimitiveUtils {

	/**
	 * Converts a primitive boolean to an integer by using
	 * it's status as a marker for the bit value.
	 * @param b The boolean to convert.
	 * @return The bit value of the boolean.
	 */
	public static int toInteger(boolean b) {
		return b ? 1 : 0;
	}

	/**
	 * Converts a primitive integer to a boolean by using
	 * it's value as a marker for the boolean value.
	 * @param i The integer to convert.
	 * @return The appropriate status value of the boolean.
	 */
	public static boolean toBoolean(int i) {
		return i > 0;
	}

}