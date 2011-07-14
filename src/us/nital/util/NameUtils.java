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

import us.nital.Constants;

/**
 * This utility helps with name conversions and the sort.
 * 
 * @author Graham Edgecombe
 */
public class NameUtils {

	/**
	 * Checks if a name is valid.
	 * @param s The name.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public static boolean isValidName(String s) {
		return formatNameForProtocol(s).matches("[a-z0-9_]+");
	}

	/**
	 * Converts a name to a long.
	 * @param s The name.
	 * @return The long.
	 */
	public static long nameToLong(String s) {
		long l = 0L;
		for (int i = 0; i < s.length() && i < 12; i++) {
			char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z') l += (1 + c) - 65;
			else if (c >= 'a' && c <= 'z') l += (1 + c) - 97;
			else if (c >= '0' && c <= '9') l += (27 + c) - 48;
		}
		while (l % 37L == 0L && l != 0L) l /= 37L;
		return l;
	}

	/**
	 * Converts a long to a name.
	 * @param l The long.
	 * @return The name.
	 */
	public static String longToName(long l) {
		int i = 0;
		char ac[] = new char[12];
		while (l != 0L) {
			long l1 = l;
			l /= 37L;
			ac[11 - i++] = Constants.VALID_CHARS[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	/**
	 * Formats a name for use in the protocol.
	 * @param s The name.
	 * @return The formatted name.
	 */
	public static String formatNameForProtocol(String s) {
		return s.toLowerCase().replace(" ", "_");
	}

	/**
	 * Formats a name for display.
	 * @param s The name.
	 * @return The formatted name.
	 */
	public static String formatName(String s) {
		return fixName(s.replace(" ", "_"));
	}

	/**
	 * Method that fixes capitalization in a name.
	 * @param s The name.
	 * @return The formatted name.
	 */
	private static String fixName(final String s) {
		if (s.length() > 0) {
			final char ac[] = s.toCharArray();
			for (int j = 0; j < ac.length; j++)
				if (ac[j] == '_') {
					ac[j] = ' ';
					if ((j + 1 < ac.length) && (ac[j + 1] >= 'a')
							&& (ac[j + 1] <= 'z')) {
						ac[j + 1] = (char) ((ac[j + 1] + 65) - 97);
					}
				}

			if ((ac[0] >= 'a') && (ac[0] <= 'z')) {
				ac[0] = (char) ((ac[0] + 65) - 97);
			}
			return new String(ac);
		} else {
			return s;
		}
	}

}