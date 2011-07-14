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

import java.util.Arrays;
import java.util.Iterator;

import us.nital.model.Entity;

/**
 * This utility provides an iterable container of {@link Entity} objects,
 * by which they are stored, and using their {@link Entity#hashCode} are
 * marked by an index attribute.
 * 
 * @author Thomas Nappo
 * @author Graham Edgecombe
 * @author Light232
 */
public class EntityList<T extends Entity> implements Iterable<T> {

	/**
	 * This mutex object is required to lock methods for synchronization
	 * and for verification only one thread is accessing the internal {@link #arr}
	 * at once, making this list implementation thread safe.
	 */
	private final Object mutex = new Object();

	/**
	 * This internal array of entities marks contained entities by
	 * their index within.
	 */
	private final Entity[] arr;

	/**
	 * The current index mark in the list. From this value we can
	 * determine the last set index slot in the list.
	 */
	private int cursor = 0;

	/**
	 * The absolute number of elements in the list.
	 */
	private int size = 0;

	/**
	 * Converts the list to an array.
	 * @return The internal {@link #arr} array which encapsulates
	 * the true values of the list.
	 */
	public Object[] toArray() {
		return arr;
	}

	/**
	 * Constructs a new entity list.
	 * @param capacity The maximum capacity of the list. More entities cannot
	 * be added should the list reach this capacity.
	 */
	public EntityList(int capacity) {
		this.arr = new Entity[capacity];
	}

	/**
	 * Adds an entity element to the list.
	 * @param e The entity to add.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean add(T e) {
		synchronized (mutex) {
			/*
			 * We cannot add more elements to the internal array should the
			 * capacity be reached.
			 */
			if (size >= arr.length) {
				return false;
			}

			/*
			 * If the current cursor is not empty we set it to the next available slot.
			 */
			if (arr[cursor] != null) {
				/*
				 * We now set the cursor to the next available slot.
				 */
				if ((cursor = availableSlot()) < 0) {
					/*
					 * Should the available slot that the cursor was set to be less than 0
					 * we return back false. As of the documentation provided by availableSlot(),
					 * a return value of -1 notifies there are no available slots. Even so, arrays
					 * cannot be accessed using negative indexes.
					 */
					return false;
				}

			}

			/*
			 * We set the current cursor to the next element.
			 */
			arr[cursor] = e;

			/*
			 * And now increase the maintained size of the list.
			 */
			size++;

			/*
			 * Finally return back true as the operation was a success.
			 */
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) arr[index];
	}

	/**
	 * Retrieves an element's index in the array.
	 * @param e The element that is used as the case object, whose
	 * index is retrieved.
	 * @return The entity's index in the internal {@link #arr} or
	 * <code>-1</code> should any problems occur or the entity not
	 * be found in the array.
	 */
	public int getIndex(T e) {
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] == e)
				return i;
		}
		return -1;
	}

	/**
	 * Removes an entity element from list.
	 * @param e The entity to remove.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean remove(T e) {
		synchronized (mutex) {
			/*
			 * We retrieve the index of the element. This value is directly
			 * determined through the entity's hash code.
			 */
			int idx = e.hashCode();

			/*
			 * If that retrieval's value is less than one their hash code value may be
			 * incorrect. This is why a double check performs in the following block.
			 */
			if (idx < 0) {
				/*
				 * We set the index to a retrieval from the getIndex(T) method.
				 */
				if ((idx = getIndex(e)) < 0)
					/*
					 * Should this value still not be useable, we return false to
					 * notify the calling value that the removal attempt failed.
					 */
					return false;
			}

			/*
			 * Otherwise we just set the determined index to null in the internal array.
			 */
			arr[idx] = null;

			/*
			 * And decrease the size.
			 */
			size--;

			/*
			 * The operation was successful, so a value of true is returned.
			 */
			return true;
		}
	}

	/**
	 * Retrieves the next available index slot in the list.
	 * @return The next available index, or <code>-1</code> if
	 * there no open slot could be found.
	 */
	public int availableSlot() {
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] == null)
				return i;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterator() {
		return (Iterator<T>) Arrays.asList(arr).iterator();
	}

}