package de.mickyjou.plugins.pvpevent.listener.protection;

import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LobbyProtectionListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(new StatsGetter(e.getPlayer()).isInLobby()) {

            if (!e.getPlayer().isOp()) e.setCancelled(true);
        }
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(new StatsGetter((Player)e.getWhoClicked()).isInLobby()){

            if(!e.getWhoClicked().isOp()) e.setCancelled(true);
        }}

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if(e.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) e.getEntity();
            if(new StatsGetter(p).isInLobby()) {
                e.setCancelled(true);

            }
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(new StatsGetter((Player)e.getEntity()).isInLobby()) e.setCancelled(true);
        }
    }


    @EventHandler
    public void onEntityDamage(FoodLevelChangeEvent e){
        if(e.getEntity() instanceof Player){
            if(new StatsGetter((Player)e.getEntity()).isInLobby()) e.setCancelled(true);
        }
    }


}
