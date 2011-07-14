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

package us.nital.bundle;

/**
 * A specialized version of an {@link Activator} which enables
 * implementation of checkpoints which are defined through an {@link Enum}.
 * 
 * <h1>Usage</h1>
 * <p>If a developer created a new bundle which utilized a {@link StringBuilder}
 * to create a new {@link String} (<b>happy</b>) in <tt>3</tt> stages on activation
 * we could use a ReplayingActivator to separate those stages easily:
 * <pre><code>public class Activator extends ReplayingActivator&#060;Activator.Stage&#062; {
 * 	
 * 	enum Stage {
 * 		STAGE_1,
 * 		STAGE_2,
 * 		STAGE_3;
 * 	}
 * 
 * 	&#064;Override
 * 	public void start(Stage stage) {
 * 		StringBuilder builder = new StringBuilder();
 * 		switch (stage) {
 * 		case STAGE_1:
 * 			builder.append("ha");
 * 			checkpoint(Stage.STAGE_2);
 * 			break;
 * 		case STAGE_2:
 * 			builder.append("p");
 * 			checkpoint(Stage.STAGE_3);
 * 			break;
 * 		case STAGE_3:
 * 			builder.append("py");
 * 			System.out.println(builder.toString());
 * 			return;
 * 		}
 * 		start();
 * 	}
 * 
 * 	&#064;Override
 * 	public void stop(Stage stage) {
 * 		...
 * 	}
 * 
 * }</code></pre>
 * 
 * <h1>Advantages:</h1>
 * <ul><li>The activator implementation can be easily scaled to fit within
 * a singular class.</li>
 * <li>The utilization of checkpoints allows collegaue developers to follow
 * the activation and deactivation of the bundle component thoroughly.</li></ul>
 * 
 * @param T A ReplayingActivator implementation requires a generic (namely <b>T</b>),
 * which must extend an {@link Enum}. This is used to define the checkpoint <i>stages</i>.
 * @author Thomas Nappo
 */
public abstract class ReplayingActivator<T extends Enum<?>> implements Activator {

	/**
	 * Current replaying state of the activator.
	 */
	private T state;

	/**
	 * Gets the current replaying state of the activator.
	 * @return The activator's {@link #state}.
	 */
	public T getState() {
		return state;
	}

	/**
	 * Creates a new instance with no initial state.
	 * By using this constructor {@link #state} will be set
	 * to <code>null</code>.
	 */
	public ReplayingActivator() {
		this(null);
	}

	/**
	 * Creates a new instance for an initial state.
	 * @param state The initial state.
	 */
	public ReplayingActivator(T state) {
		this.state = state;
	}

	/**
	 * Stores the internal activation state.
	 * @param stage The new state.
	 */
	protected void checkpoint(T state) {
		this.state = state;
	}

	/**
	 * Called when the encapsulating bundle is activated.
	 * @param state The internal activation state.
	 */
	public abstract void start(T state);

	/**
	 * Called when the encapsulating bundle is deactivated.
	 * @param state The internal activation state.
	 */
	public abstract void stop(T state);

	@Override
	public void start() {
		start(state);
	}

	@Override
	public void stop() {
		stop(state);
	}

}