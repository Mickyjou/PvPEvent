package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Michi on 06.01.2017.
 */
public class EventChunk {
    private Chunk[] chunks;
    private Player owner1 = null;
    private Player owner2 = null;
    private File file;
    private FileConfiguration cfg;
    private StringBuilder chunkBuilder;
    private ConfigurationSection chunkSection;


    /**
     * Creates a EventChunk Object
     *
     * @param chunks
     */
    public EventChunk(Chunk[] chunks) {
        this.chunks = chunks;
        file = new File(FileManager.getDataFolder() + "/chunks", "chunks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        chunkBuilder = new StringBuilder();
        int i = 0;
        for (Chunk c : this.chunks) {
            chunkBuilder.append(c.getX() + ":" + c.getZ());
            i++;
            if (i != chunks.length) chunkBuilder.append(",");
        }
        cfg.createSection(chunkBuilder.toString());
        chunkSection = cfg.getConfigurationSection(chunkBuilder.toString());
    }

    /**
     * Saves the EventChunk in the chunks file
     */
    public void save() {
        if (owner1 != null && owner2 != null) {
            chunkSection.set("owner1", owner1.getUniqueId().toString());
            chunkSection.set("owner2", owner2.getUniqueId().toString());
        } else {
            chunkSection.set("owner1", " ");
            chunkSection.set("owner2", " ");
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the Owners of the EventChunk
     *
     * @param owner1
     * @param owner2
     */
    public void setOwners(Player owner1, Player owner2) {
        this.owner1 = owner1;
        this.owner2 = owner2;
    }

    /**
     * Gets the Owners of the EventChunk
     *
     * @return UUID
     */

    public UUID[] getOwners() {

        UUID[] uuids = {Bukkit.getOfflinePlayer(chunkSection.getString("owner1")).getUniqueId(),
                Bukkit.getOfflinePlayer(chunkSection.getString("owner2")).getUniqueId()};
        return uuids;
    }

    /**
     * Gets if the EventChunk is owned
     *
     * @return boolean
     */
    public boolean isOwned() {
        if (chunkSection.getString("owner1") != null && chunkSection.getString("owner2") != null)
            return true;
        return false;
    }


}


