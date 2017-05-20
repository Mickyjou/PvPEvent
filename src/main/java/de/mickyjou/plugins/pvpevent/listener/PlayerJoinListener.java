package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.events.PlayerJoinLobbyEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

/**
 * Created by Mickyjou on 15.01.2017.
 */
public class PlayerJoinListener implements Listener {

    private final PvPEventPlugin plugin;


    public PlayerJoinListener(PvPEventPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);
        Date date = new Date();
        int hours = date.getHours() + 1;
        if (stats.isBanned()) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, PvPEventPlugin.prefix + "Du bist bereits aus dem Projekt ausgeschieden!");
            stats.getPlayerStore().put("banned", String.valueOf(false));
            return;
        } else if (hours >= 18 || hours <= 14) {
            if (!p.isOp()) {


                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, PvPEventPlugin.prefix
                        + "Du kannst nur zwischen 14 und 18 Uhr spielen.");
            }


        } else {
            Bukkit.getPluginManager().callEvent(new PlayerJoinLobbyEvent(p));

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getPluginManager().callEvent(new PlayerJoinLobbyEvent(e.getPlayer()));


    }

}
