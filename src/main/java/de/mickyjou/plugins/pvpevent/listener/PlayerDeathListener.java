package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import de.mickyjou.plugins.pvpevent.utils.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mickyjou on 14.01.2017.
 */
public class PlayerDeathListener implements Listener {

    private final PvPEventPlugin plugin;

    public PlayerDeathListener(PvPEventPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();
        updateDeathsTable(p);
        StatsGetter stats = new StatsGetter(p);

        if (killer == null) {
            e.setDeathMessage(ChatColor.GRAY + "Der Spieler §6" + p.getName() + " §7ist gestorben.");
        } else {
            e.setDeathMessage(ChatColor.GRAY + "Der Spieler §6" + p.getName() + " §7wurde von §6" + killer.getName()
                    + " §7getötet.");
            stats.addKill();
        }
        p.kickPlayer(PvPEventPlugin.prefix + "Du bist gestorben und somit aus dem Projekt ausgeschlossen");
        stats.banPlayer();
        double worldbordersize = WorldBorder.getWorldBorderSize(p.getWorld());
        for (World all : Bukkit.getWorlds()) {
            WorldBorder.setWorldBoarderSize(all, worldbordersize - 50);
        }


    }


    public void updateDeathsTable(Player death) {
        List<String> latestDeaths = plugin.cfg.getStringList("LATEST_DEATHS");
        BufferedImage image;


            latestDeaths.add(death.getUniqueId().toString());

        try {
            plugin.cfg.set("LATEST_DEATHS", latestDeaths);
            plugin.cfg.save(plugin.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            image = ImageIO.read(new File(plugin.getDataFolder(), "DeathsBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
            plugin.getLogger().severe("Could not load DeathsBackground.png" + e.getMessage());
            return;
        }

        int x = 30;
        int y = 80;
        Graphics g = image.getGraphics();
        g.setFont(new Font(Font.SERIF, Font.BOLD, 35));
        g.setColor(Color.BLACK);
        for (String all : latestDeaths) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(all));
            String name = player.getName();
            g.drawString(name, x, y);
            y += 30;
        }

        File outputFile = new File("/var/lib/minecraft/plugins/PictureFrame/images", "Deaths.png");

        try {
            ImageIO.write(image,"png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pf reload");
    }



}
