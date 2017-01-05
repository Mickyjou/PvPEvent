package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBoarder {

	/*
	 * Sets the size of the WorldBoarder in this world
	 * 
	 * @param world
	 * 
	 * @param radius
	 */

	public static void setWorldBoarderSize(World world, double radius) {
		WorldBorder border = world.getWorldBorder();
		border.setCenter(new Location(world, 0, 20, 0));
		border.setSize(radius);
	}

	/*
	 * Gets the size of the WorldBoarder in the world
	 * 
	 * @return double
	 */

	public static double getWorldBorderSize(World world) {
		return world.getWorldBorder().getSize();
	}

}
