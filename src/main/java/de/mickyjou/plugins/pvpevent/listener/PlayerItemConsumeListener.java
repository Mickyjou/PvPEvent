package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemConsumeListener implements Listener {
    public ItemStack[] forbiddenItems = {new ItemStack(Material.getMaterial(322),1,(byte)1)};


    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        for (ItemStack all : forbiddenItems) {
            if (all.getType() == e.getItem().getType()) {
                if (all.getData().getData() == e.getItem().getData().getData()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(PvPEventPlugin.prefix + "You are not allowed to consume this item.");
                    break;
                }
            }
        }

    }
}
