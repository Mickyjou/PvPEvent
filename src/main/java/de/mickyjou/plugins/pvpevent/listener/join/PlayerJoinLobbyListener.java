package de.mickyjou.plugins.pvpevent.listener.join;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.events.PlayerJoinLobbyEvent;
import de.mickyjou.plugins.pvpevent.utils.Hologram;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinLobbyListener implements Listener {


    private Location lobbyLocation;
    private Location hologrammLocation;
    private Location chestLocation;


    public PlayerJoinLobbyListener(PvPEventPlugin plugin) {
        lobbyLocation = Utils.getLocation("lobby");
        hologrammLocation = Utils.getLocation("hologramm");
        chestLocation = Utils.getLocation("chest").subtract(0,1,0);
    }

    @EventHandler
    public void onLobbyJoin(PlayerJoinLobbyEvent e) {
        StatsGetter stats = new StatsGetter(e.getPlayer());
        stats.setLobby(true);


        if (lobbyLocation == null || hologrammLocation == null || chestLocation == null) return;

        Player p = e.getPlayer();
        String[] statsText = {"§6§nYour Stats §7§n" + p.getName() + "§6:", " ", "§6Team: §7" + stats.getTeam(), "§6Team-Mate: §7" + Bukkit.getOfflinePlayer(stats.getTeamMate()).getName(),
                "§6Kills: §7" + stats.getKills(), "§6Warnings: §7" + stats.getWarnings()
        };
        Hologram statsHologram = new Hologram(statsText, hologrammLocation);


        String[] chestText = {ChatColor.GOLD + "Your Daily Reward"};
        Hologram chestHologramm = new Hologram(chestText,chestLocation);

        statsHologram.showPlayer(p);
        chestHologramm.showPlayer(p);
    }
}
