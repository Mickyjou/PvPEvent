package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.Hologram;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private Location lobbyLocation;
    private Location skullLocation;

    public PlayerJoinListener(PvPEventPlugin plugin) {
        this.plugin = plugin;
        lobbyLocation = Utils.getLocation("lobby");
        skullLocation = Utils.getLocation("skull");
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

            //e.disallow(PlayerLoginEvent.Result.KICK_BANNED, PvPEventPlugin.prefix
            //  + "Du kannst nur zwischen 14 und 18 Uhr spielen.");
            p.sendMessage("Normalerweise könntest du nicht joinen!");

        } else {
            Countdown.startCouuntdown(p);

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (lobbyLocation != null) {
            p.teleport(lobbyLocation);
        }

        if (skullLocation != null) {

            StatsGetter stats = new StatsGetter(p);
            String[] text = {"§6§nStats von §7§n" + p.getName() + "§6:", " " ,  "§6Team: §7" + stats.getTeam(), "§6Team-Mate: §7" + Bukkit.getOfflinePlayer(stats.getTeamMate()).getName(),
                    "§6Kills: §7" + stats.getKills(), "§6Verwarnungen: §7" + stats.getWarnings()
            };
            Hologram hologram = new Hologram(text, skullLocation);
            hologram.showPlayer(p);
        }


    }

}
