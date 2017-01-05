package de.mickyjou.plugins.pvpevent.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class PortalCreateListener implements Listener {
	boolean allow;
	
	/*
	 * Listener to allow creating portals just in case
	 * a operator is close to it
	 * 
	 */

	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		if (e.getReason() == CreateReason.FIRE) {
			allow = false;
			Block b = e.getBlocks().get(0);
			Location loc = b.getLocation();

			for (Entity all : loc.getWorld().getNearbyEntities(b.getLocation(), 10, 10, 10)) {
				if (all instanceof Player) {
					if (((Player) all).isOp())
						allow = true;
				}
			}
			if (!allow)
				e.setCancelled(true);
		}
	}

}
