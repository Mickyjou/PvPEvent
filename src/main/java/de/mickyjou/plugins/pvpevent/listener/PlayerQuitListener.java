package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.events.PlayerQuitLobbyEvent;
import de.mickyjou.plugins.pvpevent.events.PlayerQuitSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Mickyjou on 15.01.2017.
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(PvPEventPlugin.prefix + "Der Spieler §6"
                + e.getPlayer().getName() + " §7hat das Spiel verlassen");
        Countdown.stopCountdown(e.getPlayer());

        StatsGetter stats = new StatsGetter(e.getPlayer());
        if(stats.isInLobby()){
            Bukkit.getPluginManager().callEvent(new PlayerQuitLobbyEvent(e.getPlayer()));
        }else{
            Bukkit.getPluginManager().callEvent(new PlayerQuitSurvivalEvent(e.getPlayer()));
        }
    }
}
