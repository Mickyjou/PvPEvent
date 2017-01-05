package de.mickyjou.plugins.pvpevent.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;

public class FileManager {

	private File file;
	private FileConfiguration cfg;

	/**
	 * Creates the FileManager Object
	 * 
	 * @param file
	 */

	public FileManager(File file) {
		this.file = file;
		this.cfg = YamlConfiguration.loadConfiguration(file);
	}

	/**
	 * Saves a value to the path into the file
	 * 
	 * @param path
	 * @param value
	 */

	public void set(String path, Object value) {
		cfg.set(path, value);
		saveFile();
	}

	/**
	 * Saves the File
	 */
	public void saveFile() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the DataFolde of the Plugin
	 * 
	 * @return File
	 */
	public static File getDataFolder() {
		return PvPEventPlugin.getPlugin(PvPEventPlugin.class).getDataFolder();
	}

}
