package de.mickyjou.plugins.pvpevent.listener.chestopening;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ChestProtectionListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            if(e.getInventory().getType() == InventoryType.CHEST){
                if(e.getInventory().getName().equalsIgnoreCase("§6Daily Reward")){
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onChestBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.CHEST){
            Chest chest = (Chest) e.getBlock().getState();
            if(chest.getBlockInventory().getName().equalsIgnoreCase("§6Daily Reward")){
                e.setCancelled(true);
            }
        }
    }
}
