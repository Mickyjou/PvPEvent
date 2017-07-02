package de.mickyjou.plugins.pvpevent.listener.quit;

import de.mickyjou.plugins.pvpevent.events.PlayerQuitLobbyEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitLobbyListener implements Listener {

    @EventHandler
    public void onQuitLobby(PlayerQuitLobbyEvent e){
        StatsGetter stats = new StatsGetter(e.getPlayer());
        stats.setLobby(false);
    }
}
