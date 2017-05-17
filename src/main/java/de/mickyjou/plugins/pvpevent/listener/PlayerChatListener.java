package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);
        e.setCancelled(true);

        if(stats.isInLobby()){
            for(Player all: Bukkit.getOnlinePlayers()){
                StatsGetter playerStatsGetter = new StatsGetter(all);
                if(playerStatsGetter.isInLobby() == true){
                    all.sendMessage(e.getFormat() + e.getMessage());
                }

            }

        }else{
            for(Player all: Bukkit.getOnlinePlayers()){
                StatsGetter playerStatsGetter = new StatsGetter(all);
                if(playerStatsGetter.isInLobby() == false){
                    all.sendMessage(e.getMessage());
                }
            }
        }
    }
}
