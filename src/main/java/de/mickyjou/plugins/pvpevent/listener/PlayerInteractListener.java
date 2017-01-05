package de.mickyjou.plugins.pvpevent.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mickyjou.plugins.pvpevent.commands.SchematicCommands;
import de.mickyjou.plugins.pvpevent.PvPEventPlugin;

public class PlayerInteractListener implements Listener {
	private PvPEventPlugin plugin;

	public PlayerInteractListener(PvPEventPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!p.isOp())
			return;
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (p.getItemInHand().getType() == Material.GOLD_AXE) {
				SchematicCommands.loc1.put(p, e.getClickedBlock().getLocation());
				p.sendMessage(PvPEventPlugin.prefix + "Location 1 erfolgreich gesetzt.");
				e.setCancelled(true);
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().getType() == Material.GOLD_AXE) {
				SchematicCommands.loc2.put(p, e.getClickedBlock().getLocation());
				p.sendMessage(PvPEventPlugin.prefix + "Location 2 erfolgreich gesetzt.");
				e.setCancelled(true);
			}
		}
	}

}
