package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.events.PlayerQuitSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitSurvivalListener implements Listener {

    @EventHandler
    public void onPlayerQuitSurvival(PlayerQuitSurvivalEvent e) {
        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);
        e.getPlayerStore().saveInventory(e.getPlayer().getInventory());
        e.getPlayer().getInventory().clear();
        stats.setLobby(true);

    }
}
