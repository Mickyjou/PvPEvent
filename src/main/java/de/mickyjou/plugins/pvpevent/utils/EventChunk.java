package de.mickyjou.plugins.pvpevent.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
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
    private boolean marked;


    /**
     * Creates a EventChunk Object
     *
     * @param chunks
     */
    public EventChunk(Chunk[] chunks) {
        this.chunks = chunks;
        file = new File(FileManager.getDataFolder() + "/chunks", "chunks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves the EventChunk in the chunks file
     */
    public void save() {
        StringBuilder chunk = new StringBuilder();
        int i = 0;
        for (Chunk c : this.chunks) {
            chunk.append(c.getX() + ":" + c.getZ());
            i++;
            if (i != chunks.length) chunk.append(",");
        }
        if (owner1 != null && owner2 != null) {
            cfg.set(chunk.toString() + ".owner1", owner1.getUniqueId().toString());
            cfg.set(chunk.toString() + ".owner2", owner2.getUniqueId().toString());
        } else {
            cfg.set(chunk.toString() + ".owner1", "''");
            cfg.set(chunks.toString() + ".owner2", "''");
        }
        cfg.set(chunk.toString() + ".marked", marked);
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

        UUID[] uuids = {Bukkit.getOfflinePlayer(cfg.getString(chunks + ".owner2")).getUniqueId(), Bukkit.getOfflinePlayer(cfg.getString(chunks + ".owner2")).getUniqueId()};
        return uuids;
    }

    /**
     * Sets a mark around the EventChunk
     *
     * @param b
     * @param material
     */

    public void markArea(boolean b, Material material) {
        if (!material.isBlock()) return;
        if (b) {
            marked = true;
            return;
        }
        marked = false;
    }

    /**
     * Gets if the EventChunk is owned
     *
     * @return boolean
     */
    public boolean isOwned() {
        if (cfg.getString(chunks.toString() + ".owner") != null && cfg.getString(chunks.toString() + ".owner") != null)
            return true;
        return false;
    }

    /**
     * Gets if there is a mark around the EventChunk
     *
     * @return boolean
     */

    public boolean isMarked() {
        return cfg.getBoolean(chunks.toString() + "marked");
    }

}


