package de.mickyjou.plugins.pvpevent.listener.quit;

import de.mickyjou.plugins.pvpevent.events.PlayerQuitLobbyEvent;
import de.mickyjou.plugins.pvpevent.events.PlayerQuitSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);

        if(stats.isInLobby()){
            Bukkit.getPluginManager().callEvent(new PlayerQuitLobbyEvent(e.getPlayer()));
        }else{
            Bukkit.getPluginManager().callEvent(new PlayerQuitSurvivalEvent(e.getPlayer()));
        }
    }
}
