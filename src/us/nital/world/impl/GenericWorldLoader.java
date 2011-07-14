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

package us.nital.world.impl;

import java.io.File;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffers;

import us.nital.model.Player;
import us.nital.net.io.InBuffer;
import us.nital.net.io.OutBuffer;
import us.nital.util.FileUtils;
import us.nital.world.Result;
import us.nital.world.ReturnCodes;
import us.nital.world.Saveable;
import us.nital.world.WorldLoader;

/**
 * This implementation is used for a generic world loader in which player
 * process results and save/load results are at standard.
 * 
 * <p>When a player is processed the result created contains <tt>2</tt> attachments.
 * The attachment <b>player</b> is equivalent to the player who the result was processed
 * for. The attachment <b>returnCode</b> is a {@link ReturnCodes} constant value.</p>
 * 
 * @author Thomas Nappo
 * @see {@link WorldLoader} if you are unclear about how the implementation works.
 */
public class GenericWorldLoader implements WorldLoader {

	/**
	 * The location of player file saves.
	 */
	private static final String FILE_LOCATION = "data/games/";

	/**
	 * Gets a player's representative game file.
	 * @param player The player to get the file for.
	 * @return The player's representative game file which is used for saving/loading.
	 */
	public static File getFile(Player player) {
		return new File(FILE_LOCATION + player.getSession().getUsername().toLowerCase() + ".bin");
	}

	@Override
	public Result process(Player player) {
		/*
		 * The player's game save could not be loaded therefore
		 * the result cannot be processed using their save information.
		 */
		if (!load(player)) {
			return null;
		}

		/*
		 * Create a new result which we use add our attachments to and
		 * finally return back.
		 */
		Result result = new Result();

		/*
		 * Because the player has been loaded we can attach it to the result.
		 */
		result.putAttachment("player", player);

		/*
		 * Create a new integer to use as a generated return code.
		 */
		int returnCode = ReturnCodes.SUCCESS;

		/*
		 * Now we start to generate the appropriate return code.
		 */

		/*
		 * If the player's right's crown is less than 0, they are banned.
		 */
		if (player.getRight().getCrown() < 0) {
			returnCode = ReturnCodes.BANNED;
		}

		/*
		 * We put the generated return code as an attachment to the result.
		 */
		result.putAttachment("returnCode", returnCode);

		/*
		 * Return back the product result.
		 */
		return result;
	}

	@Override
	public boolean save(Saveable saveable) {
		/*
		 * The saveable instance must be an instance
		 * of a player otherwise we cannot save them
		 * as so.
		 */
		if (!(saveable instanceof Player)) {
			return false;
		}

		/*
		 * Construct a new output buffer with a provided dynamic buffer.
		 */
		OutBuffer buf = new OutBuffer(ChannelBuffers.dynamicBuffer());

		/*
		 * Create a new object to be used as representing
		 * the player, and set it to a conversion of the
		 * saveable instance parameter as a player.
		 */
		Player player = (Player) saveable;

		/*
		 * Use the buffer to call saving of the player's game progress.
		 */
		player.save(buf);

		/*
		 * Attempt to use our file utilities to write the buffer towards
		 * an appropriate game file.
		 */
		try {

			/*
			 * The game file's name is equivalent to the player's username
			 * with a ".bin" extension.
			 */
			FileUtils.writeBufferToFile(getFile(player).getPath(), buf.getInternalBuffer());

		} catch (IOException e) {
			/*
			 * An exception occuring notifies us that the player file was
			 * not loaded successfully, therefore we return back to notify
			 * a caller of this method that the save attempt failed.
			 */
			return false;
		}

		/*
		 * If everything went good we should arrive here. We then return
		 * back to notify the caller that the save was a success.
		 */
		return true;
	}

	@Override
	public boolean load(Saveable saveable) {
		/*
		 * The saveable instance must be an instance
		 * of a player otherwise we cannot load them
		 * as so.
		 */
		if (!(saveable instanceof Player)) {
			return false;
		}

		/*
		 * Create a new object to be used as representing
		 * the player, and set it to a conversion of the
		 * saveable instance parameter as a player.
		 */
		Player player = (Player) saveable;

		/*
		 * Attempt to process the load request.
		 */
		try {

			/*
			 * We attempt to create a new input buffer. The game file's name 
			 * is equivalent to the player's username with a ".bin" extension.
			 */
			InBuffer buf = new InBuffer(FileUtils.buildBufferFromFile(getFile(player).getPath()));

			/*
			 * We then call the load method from the player's save progress.
			 * This will throw an exception if {buf} was equivalent to null.
			 */
			player.load(buf);

		} catch (Exception e) {
			/*
			 * Because the loading failed, the registration needs a new account file.
			 */
			File file = getFile(player);

			try {
				if (file.createNewFile()) {
					/*
					 * If the file was successfully created we save the player.
					 */
					save(player);
					return true;
				}
			} catch (IOException e1) {}

			return false;
		}

		/*
		 * If everything went good we should arrive here. We then return
		 * back to notify the caller that the save was a success.
		 */
		return true;
	}

}