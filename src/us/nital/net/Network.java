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

package us.nital.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import us.nital.bundle.ReplayingActivator;
import us.nital.net.codec.Encoder;
import us.nital.net.codec.FrontDecoder;

/**
 * Activates networking componenets.
 * 
 * @author Thomas Nappo
 */
public final class Network extends ReplayingActivator<Network.State> {

	/**
	 * This singleton logger instance can be used for logging various
	 * component messages to the console.
	 */
	private static final Logger logger = Logger.getLogger(Network.class.getName());

	/**
	 * Encapsulates the singleton instance of the network.
	 * @author Thomas Nappo
	 */
	private static final class SingletonContainer {
		private static final Network INSTANCE = new Network();
	}

	/**
	 * Gets the singleton instance of the network.
	 * @return The one and only instance of the network.
	 */
	public static Network getSingleton() {
		return SingletonContainer.INSTANCE;
	}

	/**
	 * Handles channel events.
	 */
	private final ChannelHandler channelHandler = new SimpleChannelHandler() {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
			logger.info("Exception caught: " + e);
		}

		@Override
		public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
			logger.info("Received connection from: " + 
					((InetSocketAddress) ctx.getChannel().getRemoteAddress()).getHostName());
		}

		@Override
		public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
			logger.info("Disconnected connection: " + 
					((InetSocketAddress) ctx.getChannel().getRemoteAddress()).getHostName());
		}

	};

	/**
	 * Creates a new pipeline for the network to use.
	 */
	private final ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {

		@Override
		public ChannelPipeline getPipeline() {
			ChannelPipeline pipeline = Channels.pipeline();

			pipeline.addLast("handler", channelHandler);

			// This decoder will be later switched over to:
			// 					com.nital.net.codec.Decoder
			pipeline.addLast("encoder", new Encoder());
			pipeline.addLast("decoder", new FrontDecoder());

			return pipeline;
		}

	};

	/**
	 * Defines the component activation state.
	 * @author Thomas Nappo
	 */
	protected static enum State {

		/**
		 * At this state a new channel factory will be created.
		 */
		INITIALIZATION,

		/**
		 * At this state the bootstrap will be configured.
		 */
		BOOTSTRAP_CONFIGURATION,

		/**
		 * At this state the bootstrap will be binded to a socket.
		 */
		BIND,

	}

	/**
	 * Constructs a new network.
	 */
	private Network() {
		// changing changes the initial
		// state of the parent activator.
		super(State.INITIALIZATION);
	}

	/**
	 * Creates a channel which is used for communication
	 * with incoming connections from clients.
	 */
	private ChannelFactory factory;

	/**
	 * Used to configure the channel and bind it to a socket.
	 */
	private ServerBootstrap bootstrap;

	@Override
	public void start(State state) {
		switch (state) {
		default:
		case INITIALIZATION:
			Executor pool = Executors.newCachedThreadPool();
			factory = new NioServerSocketChannelFactory(pool, pool);
			checkpoint(State.BOOTSTRAP_CONFIGURATION);
			break;
		case BOOTSTRAP_CONFIGURATION:
			bootstrap = new ServerBootstrap();
			bootstrap.setFactory(factory);
			bootstrap.setPipelineFactory(pipelineFactory);
			checkpoint(State.BIND);
			break;
		case BIND:
			bootstrap.bind(new InetSocketAddress("localhost", 43594));
			return;
		}
		start();
	}

	@Override
	public void stop(State state) {
		switch (state) {
		case BIND:
			bootstrap.getPipeline().getChannel().close();
			break;
		}
	}

}