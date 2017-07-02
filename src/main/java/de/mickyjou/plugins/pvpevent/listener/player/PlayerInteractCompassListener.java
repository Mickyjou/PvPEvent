package de.mickyjou.plugins.pvpevent.listener.player;

import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractCompassListener implements Listener {

    @EventHandler
    public void onCompassInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null && e.getItem().getType() != Material.AIR) {
                if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Navigator")) {
                    e.setCancelled(true);
                    Inventory inv = Bukkit.createInventory(null, 9, "§7Navigator");
                    inv.setItem(1, createItemStack(Material.FIREWORK, "§bJump'n'Run"));
                    inv.setItem(3, createItemStack(Material.DIAMOND, "§7Join Zombie"));
                    inv.setItem(5, createItemStack(Material.DIAMOND_SWORD, "§6Stats"));
                    inv.setItem(7, createItemStack(Material.WORKBENCH, "§3New Recipes"));
                    e.getPlayer().openInventory(inv);

                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();

            if (e.getInventory().getName().equalsIgnoreCase("§7Navigator")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if (e.getCurrentItem().getType() == Material.FIREWORK) {
                        p.teleport(Utils.getLocation("compassjnr"));
                        p.closeInventory();
                    } else if (e.getCurrentItem().getType() == Material.DIAMOND) {
                        p.teleport(Utils.getLocation("compasszombie"));
                        p.closeInventory();

                    } else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                        p.teleport(Utils.getLocation("compassstats"));
                        p.closeInventory();

                    } else if (e.getCurrentItem().getType() == Material.WORKBENCH) {
                        p.teleport(Utils.getLocation("compassrecipes"));
                        p.closeInventory();
                    }
                }
            }
        }

    }


    public ItemStack createItemStack(Material material, String name) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
}
