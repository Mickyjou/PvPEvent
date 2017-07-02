package de.mickyjou.plugins.pvpevent.listener.player;

import de.mickyjou.plugins.pvpevent.events.PlayerJoinSurvivalEvent;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractZombieListener implements Listener {


    @EventHandler
    public void onZombieInteract(PlayerInteractAtEntityEvent e) {

        Player p = e.getPlayer();
        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            ArmorStand stand = (ArmorStand) e.getRightClicked();
            if (stand.getName().equals("§6JOIN SURVIVAL")) {
                e.setCancelled(true);
                if (new StatsGetter(p).isInLobby() == true) {
                    Bukkit.getPluginManager().callEvent(new PlayerJoinSurvivalEvent(p));
                }
            }

        }
    }
}
