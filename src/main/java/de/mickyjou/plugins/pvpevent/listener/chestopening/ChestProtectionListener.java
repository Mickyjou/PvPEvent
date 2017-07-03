package de.mickyjou.plugins.pvpevent.listener.chestopening;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestProtectionListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (e.getInventory().getType() == InventoryType.CHEST) {
                if (e.getInventory().getName().equalsIgnoreCase("§6Daily Reward")) {
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onChestBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.CHEST) {
            Chest chest = (Chest) e.getBlock().getState();
            if (chest.getBlockInventory().getName().equalsIgnoreCase("§6Daily Reward")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase("§6Daily Reward")) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (PlayerInventoryOpenListener.isOpening.get(e.getPlayer()) == true)
                        e.getPlayer().openInventory(PlayerInventoryOpenListener.inventorys.get(e.getPlayer()));
                    cancel();
                }
            }.runTaskLater(PvPEventPlugin.getPlugin(PvPEventPlugin.class), 1);
        }
    }
}
