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

package us.nital.util.logging;

import static java.lang.System.out;

import java.security.InvalidParameterException;

/**
 * A logging utility for advanced logging with node support.
 * 
 * @author Thomas Nappo
 */
public class Logger {

	/**
	 * The logger's index, which is used to keep track of
	 * the space between the nodes and the nodes themeselves.
	 * 
	 * This is also referred to as the <b>node level</b>.
	 */
	private static int i = 0;

	/**
	 * Retrieves the proper symbol to place for the log instance.
	 * @return no symbol if <code>i == 0</code>, <tt>*</tt> if <code>(i % 2) == 0</code>
	 * and <tt>-</tt> otherwise.
	 */
	private static String getSymbol() {
		if (i == 0)
			return "";
		if ((i % 2) == 0)
			return "*";
		return "-";
	}

	/**
	 * Prints the appropriate symbol along with a space (if there is a symbol).
	 */
	private static void printSymbol() {
		if (i > 0) 
			out.print(getSymbol() + " ");
	}

	/**
	 * Logs a line to the text using the current node level {@link #i}.
	 * @param text The text of the node.
	 * @param increase Whether or not to increase the node level.
	 */
	public static void log(String text, boolean increase) {
		indent();
		printSymbol();
		out.println(text);
		if (increase) i++;
	}

	/**
	 * Logs a line to the text using the current node level {@link #i}.
	 * @param text The text of the node.
	 */
	public static void log(String text) {
		log(text, false);
	}

	/**
	 * Logs a line to the text.
	 * @param text The text of the node.
	 * @param before If <code>true</code> the node level will be increased
	 * before the output, otherwise the node level will be increased after the output.
	 */
	public static void logI(String text, boolean before) {
		if (before) i++;
		log(text, !before);
	}

	/**
	 * Logs a line to the text.
	 * @param text The text of the node.
	 * @param before If <code>true</code> the node level will be decreased
	 * before the output, otherwise the node level will be decreased after the output.
	 */
	public static void logD(String text, boolean before) {
		if (before) i--;
		indent();
		printSymbol();
		out.println(text);
		if (!before) i--;
	}

	/**
	 * Alters the node level by <tt>1</tt>.
	 * @param up if <b><code>true</code></b> the node level will be increased<br />
	 * if <b><code>false</code></b> the node level will be decreased
	 */
	public static void changeNode(boolean up) {
		i += (up ? 1 : -1);
	}

	/**
	 * Sets the logger's node level.
	 * @param i The new node level.
	 */
	public static void setNode(int i) {
		if (i < 0)
			throw new InvalidParameterException();
		Logger.i = i;
	}

	/**
	 * Prints a space for each node level.
	 */
	private static void indent() {
		for (int t = 0; t < (i * 2); t++)
			out.print(" ");
	}

}