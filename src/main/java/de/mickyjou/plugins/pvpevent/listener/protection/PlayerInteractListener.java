package de.mickyjou.plugins.pvpevent.listener.protection;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractListener implements Listener {


    public PotionEffectType[] forbiddenItems = {PotionEffectType.INCREASE_DAMAGE, PotionEffectType.POISON, PotionEffectType.HARM};

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        try {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR) {
                    if (Potion.fromItemStack(e.getItem()) != null) {
                        Potion potion = Potion.fromItemStack(e.getItem());
                        for (PotionEffectType type : forbiddenItems) {
                            if (potion.getType().getEffectType() == type) {
                                if (potion.getLevel() == 2) {
                                    e.setCancelled(true);
                                    e.getPlayer().sendMessage(PvPEventPlugin.prefix + "You are not allowed to consume this item.");
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception ex){

        }




    }
}
