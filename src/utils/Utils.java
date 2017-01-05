package utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

public class Utils {
	
	/**
	 * Gets the Location to build a wall
	 * 
	 * @param loc1
	 * @param loc2
	 * @return
	 */

	static ArrayList<Location> getWall(Location loc1, Location loc2) {
		ArrayList<Location> locs = new ArrayList<>();
		if (loc1.getWorld().equals(loc2.getWorld())) {

			int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
			int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
			int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

			int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
			int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
			int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

			for (int x = minX; x <= maxX; x++) {
				for (int y = minY; y <= maxY; y++) {

					Location l1 = new Location(loc1.getWorld(), x, y, loc1.getZ());
					Location l2 = new Location(loc1.getWorld(), x, y, loc2.getZ());

					locs.add(l1);
					locs.add(l2);
				}
			}

			for (int z = minZ; z <= maxZ; z++) {
				for (int y = minY; y <= maxY; y++) {
					Location l1 = new Location(loc1.getWorld(), loc1.getX(), y, z);
					Location l2 = new Location(loc1.getWorld(), loc2.getX(), y, z);
					locs.add(l1);
					locs.add(l2);
				}
			}

		}
		return locs;
	}
	
	/**
	 * Builds a wall 
	 * 
	 * @param loc1
	 * @param loc2
	 */

	public static void setWall(Location loc1, Location loc2) {
		for (Location loc : getWall(loc1, loc2)) {
			loc.getBlock().setType(Material.STONE);
		}
	}
	
	public static void createNewWorld(String name) {
		WorldCreator wc = new WorldCreator(name);
		ChunkGenerator cg = new EventChunkGenerator();
		wc.type(WorldType.NORMAL);
		wc.generateStructures(true);
		wc.createWorld();
	}

}
