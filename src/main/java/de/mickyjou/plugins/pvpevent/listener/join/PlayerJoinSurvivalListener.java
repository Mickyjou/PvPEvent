package de.mickyjou.plugins.pvpevent.listener.join;

import de.mickyjou.plugins.pvpevent.events.PlayerJoinSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinSurvivalListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinSurvivalEvent e){

        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);
        stats.setSurvivalStats(p);



    }
}
