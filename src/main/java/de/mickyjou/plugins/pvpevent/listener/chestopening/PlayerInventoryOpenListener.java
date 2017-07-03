package de.mickyjou.plugins.pvpevent.listener.chestopening;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.ChestCountdown;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlayerInventoryOpenListener implements Listener {
    private PvPEventPlugin plugin = PvPEventPlugin.getPlugin(PvPEventPlugin.class);
    public static Map<Player, Inventory> inventorys = new HashMap<>();
    public static Map<Player, Boolean> isOpening = new HashMap<>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        StatsGetter stats = new StatsGetter((Player) e.getPlayer());
        Player p = (Player) e.getPlayer();

        if (stats.isInLobby()) {
            if (e.getInventory().getName().equalsIgnoreCase("§6Daily Reward")) {
                if (isOpening.get(p) == false) {
                    Inventory inv = e.getInventory();
                    inv.setItem(4, createItemStack(Material.HOPPER, "§7Your item"));

                    int countdown = new Random().nextInt(61) + 140;
                    setItems(inv);
                    new ChestCountdown(plugin, p, inv, countdown).runTaskTimer(plugin, 0L, 2L);
                    inventorys.put(p, inv);
                    isOpening.put(p, true);
                }

            }
        }

    }


    public void setItems(Inventory inv) {

        Integer[] glasses = {0, 1, 2, 3, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (Integer all : glasses) {
            inv.setItem(all, createItemStack(Material.STAINED_GLASS_PANE, " "));
        }


        List<ItemStack> items = Utils.getChestItems();
        int toSet = 9;


        for (int i = 0; i < 8; i++) {
            ItemStack stack = items.get(new Random().nextInt(items.size()));
            inv.setItem(toSet, stack);
            toSet++;
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
