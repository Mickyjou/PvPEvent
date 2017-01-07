package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

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

    /**
     * Creates a new World
     *
     * @param name
     */

    public static void createNewWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        ChunkGenerator cg = new EventChunkGenerator();
        wc.type(WorldType.NORMAL);
        wc.generateStructures(true);
        wc.createWorld();
    }

    /**
     * Gets the UUID's of the Owners from a EventChunk
     *
     * @param chunk
     * @return
     */

    public static UUID[] getOwnersFromEventChunk(Chunk chunk) {
        String c = chunk.getX() + ":" + chunk.getZ();
        File file = new File(FileManager.getDataFolder() + "/chunks", "chunks.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for (String keys : cfg.getKeys(false)) {
            String[] chunks = keys.split(",");
            for (String all : chunks) {
                if (all != null && !all.isEmpty()) {
                    String[] chunkString = all.split(":");
                    int x = Integer.valueOf(chunkString[0]);
                    int z = Integer.valueOf(chunkString[1]);
                    Chunk newChunk = chunk.getWorld().getChunkAt(x, z);
                    if (chunk == newChunk) {
                        UUID[] toReturn = {UUID.fromString(cfg.getString(keys + ".owner1")), UUID.fromString(cfg.getString(keys + ".owner2"))};
                        return toReturn;
                    }


                }
            }
        }
        return null;
    }

    /**
     * Generate EventChunks with the size of 4x4 Chunks
     *
     * @param chunkRadius
     * @param world
     */

    public static void generateEventChunks(int chunkRadius, World world) {
        for (int x = -(chunkRadius); x <= chunkRadius; x += 2) {
            for (int z = -(chunkRadius); z <= chunkRadius; z += 2) {
                Chunk[] chunk = {world.getChunkAt(x, z), world.getChunkAt(x + 1, z), world.getChunkAt(x, z + 1), world.getChunkAt(x+1, z+1)};
                EventChunk eventchunk = new EventChunk(chunk);
                eventchunk.save();

            }
        }
    }

}
