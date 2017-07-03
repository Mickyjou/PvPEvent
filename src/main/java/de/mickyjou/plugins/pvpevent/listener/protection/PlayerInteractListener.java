package de.mickyjou.plugins.pvpevent.listener.protection;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.Material;
import org.bukkit.block.Chest;
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


    @EventHandler
    public void onPlayerChestInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.CHEST){
                Chest chest = (Chest) e.getClickedBlock().getState();
                if(chest.getInventory().getName().equalsIgnoreCase("§6Daily Reward")){
                    if(new StatsGetter(e.getPlayer()).hasTimeTask("caseopening")) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(PvPEventPlugin.prefix + "You can only open aone case every 24 hours.");
                    }
                }
            }
        }
    }
}
