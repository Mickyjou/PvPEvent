package de.mickyjou.plugins.pvpevent.listener.quit;

import de.mickyjou.plugins.pvpevent.events.PlayerQuitSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitSurvivalListener implements Listener {


    @EventHandler
    public void onPlayerQuit(PlayerQuitSurvivalEvent e){

        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);
        stats.saveSurvivalStats(p);






    }
}
