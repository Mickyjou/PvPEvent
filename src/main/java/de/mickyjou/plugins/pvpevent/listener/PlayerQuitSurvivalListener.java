package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.events.PlayerQuitSurvivalEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitSurvivalListener implements Listener {

    @EventHandler
    public void onPlayerQuitSurvival(PlayerQuitSurvivalEvent e) {

        e.getPlayerStore().saveInventory(e.getPlayer().getInventory());
        e.getPlayer().getInventory().clear();

    }
}
