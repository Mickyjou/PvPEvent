package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.events.PlayerJoinSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.Countdown;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinSurvivalListener implements Listener {

    @EventHandler
    public void onPlayerJoinSurvival(PlayerJoinSurvivalEvent e){
        Player p = e.getPlayer();
        StatsGetter stats = new StatsGetter(p);
        Countdown.startPreCountdown(p);
        p.getInventory().clear();
        p.getInventory().setContents(e.getPlayerStore().getInventory().getContents());
        stats.setLobby(false);
        p.teleport(getPlayerSpawnLocation(p));
        p.setGameMode(GameMode.SURVIVAL);


    }

    public Location getPlayerSpawnLocation(Player p){
        //TODO
        return new Location(Bukkit.getWorld("world"),0, 80, 0);
    }
}
