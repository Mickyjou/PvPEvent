package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.events.PlayerJoinLobbyEvent;
import de.mickyjou.plugins.pvpevent.utils.Hologram;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinLobbyListener implements Listener {


    private Location lobbyLocation;
    private Location hologrammLocation;


    public PlayerJoinLobbyListener(PvPEventPlugin PvPEventPlugin) {
        lobbyLocation = Utils.getLocation("lobby");
        hologrammLocation = Utils.getLocation("hologramm");


    }

    @EventHandler
    public void onJoinLobby(PlayerJoinLobbyEvent e) {

        if (lobbyLocation == null || hologrammLocation == null) return;

        Player p = e.getPlayer();
        StatsGetter stats = e.getPlayerStore();
        stats.setLobby(true);
        String[] text = {"§6§nYour Stats §7§n" + p.getName() + "§6:", " ", "§6Team: §7" + stats.getTeam(), "§6Team-Mate: §7" + Bukkit.getOfflinePlayer(stats.getTeamMate()).getName(),
                "§6Kills: §7" + stats.getKills(), "§6Warnings: §7" + stats.getWarnings()
        };
        Hologram hologram = new Hologram(text, hologrammLocation);
        hologram.showPlayer(p);

    }
}
