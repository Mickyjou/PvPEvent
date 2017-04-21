package de.mickyjou.plugins.pvpevent.utils;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    private static Plugin plugin = PvPEventPlugin.getPlugin(PvPEventPlugin.class);

    /**
     * Creates a new World
     *
     * @param name
     */

    public static void createNewWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.type(WorldType.NORMAL);
        wc.generateStructures(true);
        wc.createWorld();
    }


    public static void loadAllTeams() {
        File teamsfile = new File(FileManager.getDataFolder(), "Teams.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(teamsfile);
        ArrayList<EventTeam> teams = new ArrayList<>();

        for (String all : cfg.getKeys(false)) {
            ConfigurationSection section = cfg.getConfigurationSection(all);
            teams.add(new EventTeam(all, section.getString("player1"), section.getString("player2")));
        }

        PvPEventPlugin.teams = teams;
    }

    public static ArrayList<EventTeam> getAllTeams() {
        return PvPEventPlugin.teams;
    }

    public static void saveLocation(Location location, String name) {
        name = name + ".";

        ConfigurationSection section = PvPEventPlugin.cfg.getConfigurationSection("locations." + name) != null ?
                PvPEventPlugin.cfg.getConfigurationSection("locations." + name) : PvPEventPlugin.cfg.createSection("locations." + name);

        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());

        try {
            PvPEventPlugin.cfg.save(PvPEventPlugin.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Location getLocation(String name) {
        ConfigurationSection section = PvPEventPlugin.cfg.getConfigurationSection("locations." + name);
        String world = section.getString("world");
        Double x = section.getDouble("x");
        Double y = section.getDouble("y");
        Double z = section.getDouble("z");
        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }


}
