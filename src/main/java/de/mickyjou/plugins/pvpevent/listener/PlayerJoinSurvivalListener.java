package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.events.PlayerJoinSurvivalEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinSurvivalListener implements Listener {

    @EventHandler
    public void onPlayerJoinSurvival(PlayerJoinSurvivalEvent e){
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setContents(e.getPlayerStore().getInventory().getContents());
    }
}
