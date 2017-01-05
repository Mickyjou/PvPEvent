package de.mickyjou.plugins.pvpevent.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class EventChunkGenerator extends ChunkGenerator {

	private void setBlock(short[][] result, int x, int y, int z, short blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new short[4096];
		}
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
	}

	public short[][] generateExtBlockSections(World world, Random random, int cx, int cz,
			ChunkGenerator.BiomeGrid biomes) {
		short[][] result = new short[16][];

		for (int x = 0; x < 16; x += 16) {
			for (int z = 0; z < 16; z += 16) {
				for (int y = 0; y < 192; y += 16) {
					setBlock(result, x, y, z, (short) Material.GOLD_BLOCK.getId());
				}
			}
		}

		return result;
	}

	public List<BlockPopulator> getDefaultPopulators(World world) {

		return Arrays.asList(new BlockPopulator[] {});
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 100, 0);
	}

}
