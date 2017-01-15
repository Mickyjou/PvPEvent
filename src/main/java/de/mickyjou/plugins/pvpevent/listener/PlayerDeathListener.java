package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import de.mickyjou.plugins.pvpevent.utils.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Mickyjou on 14.01.2017.
 */
public class PlayerDeathListener implements Listener {


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();

        if (killer == null) {
            e.setDeathMessage(ChatColor.GRAY + "Der Spieler §6" + p.getName() + " §7ist gestorben.");
        } else {
            e.setDeathMessage(ChatColor.GRAY + "Der Spieler §6" + p.getName() + " §7wurde von §6" + killer.getName()
                    + " §7getötet.");
            Utils.addKill(killer);
        }
        p.kickPlayer(PvPEventPlugin.prefix + "Du bist gestorben und somit aus dem Projekt ausgeschlossen");
        Utils.banPlayer(p);
        double worldbordersize = WorldBorder.getWorldBorderSize(p.getWorld());
        for(World all: Bukkit.getWorlds()){
            WorldBorder.setWorldBoarderSize(all, worldbordersize - 50);
        }

    }



}
