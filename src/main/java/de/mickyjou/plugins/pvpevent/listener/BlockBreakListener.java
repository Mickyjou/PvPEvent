package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.commands.HologrammCommand;
import de.mickyjou.plugins.pvpevent.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(HologrammCommand.players.contains(e.getPlayer())){
            Utils.saveLocation(e.getBlock().getLocation(), "skull");
            e.getPlayer().sendMessage(PvPEventPlugin.prefix + "Succesfully saved the Location");
            HologrammCommand.players.remove(e.getPlayer());
        }

    }
}
