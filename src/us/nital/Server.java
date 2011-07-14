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

package us.nital;

import java.util.logging.Logger;

//import org.hyperion.fileserver.FileServer;

import us.nital.bundle.ReplayingActivator;
import us.nital.world.World;
import us.nital.net.Network;


/**
 * This class represents the application that will form the base of 
 * other source files which will be able to run within this program.
 * 
 * @author Thomas Nappo
 */
public final class Server extends ReplayingActivator<Server.State> {

	/**
	 * This singleton logger instance can be used for logging various
	 * component messages to the console.
	 */
	private static final Logger logger = Logger.getLogger(Server.class.getName());

	/**
	 * Encapsulates the singleton instance of the server.
	 * 
	 * @author Thomas Nappo
	 */
	private static final class SingletonContainer {
		private static final Server INSTANCE = new Server();
	}

	/**
	 * Gets the singleton instance of the server.
	 * @return The one and only instance of the server.
	 */
	public static Server getSingleton() {
		return SingletonContainer.INSTANCE;
	}

	/**
	 * Defines the component activation state.
	 * 
	 * @author Thomas Nappo
	 */
	protected static enum State {

		/**
		 * At this state the server initializes
		 * networking components.
		 */
		NETWORK,

		/**
		 * At this state the server initializes
		 * game components.
		 */
		GAME,

		/**
		 * At this state the server has finished
		 * deployment and can utilize all components
		 * for a complete environment.
		 */
		FINISHED;

	}

	/**
	 * Constructs a new server.
	 */
	private Server() {
		// changing changes the initial
		// state of the parent activator.
		super(State.NETWORK);
	}

	/**
	 * This is the entry point of the application which implements
	 * the command line interface. From here we begin initialization
	 * of the application's separate components.
	 * @param args The command line parameters.
	 */
	public static void main(String[] args) {
		logger.info("Initializing Nital...");

		// call for activation
		getSingleton().start();

		/* 
		 * Append a shutdown hook which calls the stop method
		 * so we can be sure to run shutdown procedures before
		 * process termination.
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				getSingleton().stop();
			}
		});
	}

	@Override
	public void start(State state) {
		switch (state) {
		default:
		case NETWORK:
			Network.getSingleton().start();
			checkpoint(State.GAME);
			break;
		case GAME:
			World.getSingleton();
			checkpoint(State.FINISHED);
			break;
		case FINISHED:
			logger.info("Ready for takeoff!");
			return;
		}
		start();
	}

	@Override
	public void stop(State state) {
		logger.info("Stopping Nital...");

		/*
		 * Here you can implement whatever shutdown procedures
		 * should take place.
		 */

		Network.getSingleton().stop();
		System.exit(0);
	}

}