package de.mickyjou.plugins.pvpevent.listener.join;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.events.PlayerJoinLobbyEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PvPEventPlugin plugin;


    public PlayerJoinListener(PvPEventPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getPluginManager().callEvent(new PlayerJoinLobbyEvent(e.getPlayer()));
    }

}
