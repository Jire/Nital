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

import java.util.ArrayDeque;
import java.util.Queue;

// XXX: Needs improvement on documentation

/**
 * Holds a queue of points for walking.
 * 
 * <p>Internal position points can be are processed to move 
 * the encapsulating mobile entity along the tile grid.</p>
 * 
 * <p><i>Some functions result in possibly unexpected actions, be sure
 * to review the documentation provided by the queue's internal methods
 * before use.</i></p>
 * 
 * @author Thomas Nappo
 */
public class WalkingQueue {

	/**
	 * The parent of the walking queue.
	 */
	@SuppressWarnings("unused")
	private final Mob mob;

	/**
	 * Constructs a new walking queue.
	 * @param mob The walking queue's parent.
	 */
	public WalkingQueue(Mob mob) {
		this.mob = mob;
	}

	/**
	 * The literal queue of positions to walk.
	 */
	private final Queue<Position> queue = new ArrayDeque<Position>();

	/**
	 * Adds a position to the queue.
	 * @param position The position to add.
	 */
	public void add(Position position) {
		queue.add(position);
	}

	/**
	 * Processes the next step in the queue.
	 */
	public void process() {
		@SuppressWarnings("unused")
		Position next = queue.poll();
	}

}