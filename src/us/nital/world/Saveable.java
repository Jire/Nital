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

import us.nital.net.io.InBuffer;
import us.nital.net.io.OutBuffer;

/**
 * With implementation of this interface the child class becomes
 * authorized to be generically saved and loaded by a buffer stream.
 * 
 * <p>This interface defines the methods for saving by buffer and loading
 * by buffer which must be specifically implemented by the implementation.</p>
 * 
 * @author Thomas Nappo
 */
public interface Saveable {

	/**
	 * Called when the implementation is saved.
	 * @param buf The buffer which should be utilized to
	 * write save data to.
	 */
	public void save(OutBuffer buf);

	/**
	 * Called when the implementation is loaded.
	 * @param buf The buffer which should be utilized to
	 * read save data from.
	 */
	public void load(InBuffer buf);

}