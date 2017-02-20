package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.StreamCorruptedException;

public class ConfigManager {

    private File file;
    private FileConfiguration cfg;
    private int sessions;

    public ConfigManager() {
        this.file = new File(FileManager.getDataFolder(), "config.yml");
        this.cfg = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfiguration() {
        return cfg;
    }

    public static void reloadValues() {

    }

    public static void createConfigIfNotExists() {
        File file = new File(FileManager.getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("SessionsPerWeek", String.valueOf(7));
    }
}
