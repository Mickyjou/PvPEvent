package de.mickyjou.plugins.pvpevent.utils;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ChestCountdown
        extends BukkitRunnable {
    public static final int REPETING = 2;
    private final PvPEventPlugin plugin;
    private final Player player;
    private final Inventory inventory;
    private int countdown;
    private final int defaultCountdown;
    private int delayedCount = 0;

    public ChestCountdown(PvPEventPlugin plugin, Player player, Inventory inventory, int countdown) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = inventory;
        this.countdown = countdown;
        this.defaultCountdown = countdown;
    }

    public void run() {
        this.countdown -= 2;
        if (this.countdown > this.defaultCountdown * 0.7D) {
            rotateInventory();
        } else if (this.countdown > this.defaultCountdown * 0.5D) {
            this.delayedCount += 1;
            if (this.delayedCount > 3) {
                rotateInventory();
                this.delayedCount = 0;
            }
        } else if (this.countdown > this.defaultCountdown * 0.35D) {
            this.delayedCount += 1;
            if (this.delayedCount > 5) {
                rotateInventory();
                this.delayedCount = 0;
            }
        } else if (this.countdown > this.defaultCountdown * 0.2D) {
            this.delayedCount += 1;
            if (this.delayedCount > 7) {
                rotateInventory();
                this.delayedCount = 0;
            }
        } else if (this.countdown > 0.1D) {
            this.delayedCount += 1;
            if (this.delayedCount > 9) {
                rotateInventory();
                this.delayedCount = 0;
            }
        } else if (this.countdown > 0) {
            this.delayedCount += 1;
            if (this.delayedCount > 12) {
                rotateInventory();
                this.delayedCount = 0;
            }
        }
        if (this.countdown < 0) {
            cancel();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
                public void run() {
                    //TODO
                    ItemStack win = inventory.getItem(13);
                    player.getOpenInventory().close();
                    player.sendMessage(PvPEventPlugin.prefix + "Congrats! You got " + ChatColor.GOLD + win.getAmount() + " " +
                            WordUtils.capitalize(win.getType().toString().toLowerCase()).replace("_", " ") + ChatColor.GRAY + ".");
                    new StatsGetter(player).addSurivalItem(win);
                    player.playSound(player.getLocation(),Sound.NOTE_STICKS, 1.0F, 1.0F);
                }
            }, 40L);
        }
    }

    private void rotateInventory() {

        List<ItemStack> items = Utils.getChestItems();
        ItemStack stack = items.get(new Random().nextInt(items.size()));


        this.inventory.setItem(9, this.inventory.getItem(10));
        this.inventory.setItem(10, this.inventory.getItem(11));
        this.inventory.setItem(11, this.inventory.getItem(12));
        this.inventory.setItem(12, this.inventory.getItem(13));
        this.inventory.setItem(13, this.inventory.getItem(14));
        this.inventory.setItem(14, this.inventory.getItem(15));
        this.inventory.setItem(15, this.inventory.getItem(16));
        this.inventory.setItem(16, this.inventory.getItem(17));
        this.inventory.setItem(17, stack);
        this.player.getWorld().playSound(this.player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);

    }


}
