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

import java.util.HashMap;
import java.util.Map;

/**
 * The result of a request.
 * 
 * @author Thomas Nappo
 */
public class Result {

	/**
	 * A map which associates attachments of the result with their identifying keys.
	 */
	private final Map<String, Object> attachments = new HashMap<String, Object>();

	/**
	 * Associates an attachment object with a certain key.
	 * @param key The identification key.
	 * @param o The attachment object.
	 * @return The previous attachment associated with key, or <code>null</code> if
	 * the key did not have an association.
	 */
	public Object putAttachment(String key, Object o) {
		return attachments.put(key, o);
	}

	/**
	 * Gets the attachment object associated with a specific key.
	 * @param key The identification key.
	 * @return The value to which the key was associated with or <code>null</code> if
	 * the key did not have an association.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttachment(String key) {
		return (T) attachments.get(key);
	}

	/**
	 * Constructs a new result with defined attachments.
	 * 
	 * <p>The attachments will be associated with their index caret (<code>+ 1</code>) 
	 * inside the passed variation argument array (namely: <code>attachments</code>).</p>
	 * 
	 * @param attachments The attachments to define upon construction.
	 */
	public Result(Object... attachments) {
		for (int i = 1; i < attachments.length; i++) {
			Object o = attachments[i];
			putAttachment(Integer.toString(i), o);
		}
	}

}