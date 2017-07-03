package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Mickyjou on 14.01.2017.
 */
public class EventTeam {

    String name;
    String p1;
    String p2;
    File file;
    FileConfiguration cfg;
    ConfigurationSection section;

    public EventTeam(String name, String player1UUID, String player2UUID) {
        this.name = name;
        this.p1 = player1UUID;
        this.p2 = player2UUID;
        this.file = new File(FileManager.getDataFolder(), "Teams.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
        if (cfg.getConfigurationSection(name) == null) cfg.createSection(name);
        section = cfg.getConfigurationSection(name);
    }

    public OfflinePlayer[] getPlayers() {

        return new OfflinePlayer[]{Bukkit.getOfflinePlayer(UUID.fromString(section.getString("player1"))),
                Bukkit.getOfflinePlayer(UUID.fromString(section.getString("player2")))};
    }

    public String getName() {
        return name;
    }

    public void save() {

        StatsGetter p1 = new StatsGetter(UUID.fromString(this.p1));
        StatsGetter p2 = new StatsGetter(UUID.fromString(this.p2));
        p1.setTeam(name);
        p2.setTeam(name);
        p1.setTeamMate(UUID.fromString(this.p2));
        p2.setTeamMate(UUID.fromString(this.p1));

        section.set("player1", this.p1);
        section.set("player2", this.p2);

        try {
            cfg.save(file);
        } catch (IOException e) {

        }

    }

    public void delete() {
        try {
            StatsGetter p1 = new StatsGetter(UUID.fromString(this.p1));
            StatsGetter p2 = new StatsGetter(UUID.fromString(this.p2));
            p1.setTeam(null);
            p2.setTeam(null);
            p1.setTeamMate(UUID.fromString(null));
            p2.setTeamMate(UUID.fromString(null));
        }catch(NullPointerException e){

        }
        cfg.set(name, null);

        try {
            cfg.save(file);
        } catch (IOException e) {

        }
    }


}
