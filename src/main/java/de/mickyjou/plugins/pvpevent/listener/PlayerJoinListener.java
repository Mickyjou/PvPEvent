package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Mickyjou on 15.01.2017.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if (Utils.isBanned(p)) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, PvPEventPlugin.prefix + "Du bist bereits aus dem Projekt ausgeschieden!");
            Utils.getPlayerStore(p).put("banned", String.valueOf(false));
            return;
        }
        Countdown.startCouuntdown(p);
    }


}
