package listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import commands.SchematicCommands;
import de.mickyjou.plugins.PvPEvent;
import utils.SchematicManager;

public class PlayerInteractListener implements Listener {
	private PvPEvent plugin;

	public PlayerInteractListener(PvPEvent plugin) {
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
				p.sendMessage(PvPEvent.prefix + "Location 1 erfolgreich gesetzt.");
				e.setCancelled(true);
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().getType() == Material.GOLD_AXE) {
				SchematicCommands.loc2.put(p, e.getClickedBlock().getLocation());
				p.sendMessage(PvPEvent.prefix + "Location 2 erfolgreich gesetzt.");
				e.setCancelled(true);
			}
		}
	}

}
