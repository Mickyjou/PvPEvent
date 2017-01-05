package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;

import de.mickyjou.plugins.PvPEvent;

public class SchematicManager {
	private PvPEvent plugin;
	Map<String, ArrayList<String>> schematics;

	public SchematicManager(PvPEvent plugin) {
		this.plugin = plugin;
		this.schematics = new HashMap<>();
	}

	/**
	 * Puts the Schematic into a {@link HashMap}
	 * 
	 * @param name
	 */
	public void load(String name) {
		ArrayList<String> list = getSchematicStringList(name);
		if (list != null && list.size() > 0)
			schematics.put(name, list);
	}
	
	/**
	 * Reads the schematic from a file
	 * 
	 * @param filename
	 * @return
	 */

	public ArrayList<String> getSchematicStringList(String filename) {
		File f = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics", filename + "schematic");
		FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> list = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Saves the Schematic into the file
	 * 
	 * @param name
	 * @param list
	 */
	public void save(String name, ArrayList<String> list) {
		File f = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics", name + ".schematic");
		String newLine = System.getProperty("line.seperator");
		if (f.exists()) {
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (String s : list) {
				bw.write(s);
				bw.write(newLine);
			}
			bw.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Converts {@link ArrayList<Block>} into {@link ArrayList<String>}
	 * 
	 * @param blocks
	 * @param loc
	 * @return
	 */
	public ArrayList<String> blockListConverter(ArrayList<Block> blocks, Location loc) {
		ArrayList<String> list = new ArrayList<>();
		blocks.forEach(b -> {
			list.add(convertBlockToString(b, loc));
		});
		return list;
	}

	public ArrayList<Block> getBlocks(Location l1, Location l2) {
		return new Cuboid(l1, l2).getBlocks();
	}
	
	/**
	 * paste the Schematic on the Location
	 * 
	 * @param name
	 * @param loc
	 */

	public void paste(String name, Location loc) {
		ArrayList<String> list = schematics.get(name);
		if (list == null || list.size() > 0)
			return;
		for (String str : list) {
			String[] s = str.split(":");
			int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]), z = Integer.parseInt(s[2]),
					id = Integer.parseInt(s[3]);
			byte data = Byte.parseByte(s[4]);
			loc.clone().add(x, y, z).getBlock().setTypeIdAndData(id, data, true);
		}
	}
	
	/**
	 * Serealizes the Block into a String
	 * 
	 * @param b
	 * @param loc
	 * @return
	 */

	public String convertBlockToString(Block b, Location loc) {
		int diffx = b.getX() - loc.getBlockX();
		int diffy = b.getY() - loc.getBlockY();
		int diffz = b.getZ() - loc.getBlockZ();
		return diffx + ":" + diffy + ":" + diffz + ":" + b.getTypeId() + ":" + b.getData();
	}
	
	public void delete(String filename){
		File file = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics", filename + ".schematic");
		if(file.exists()) file.delete();
	}
}
