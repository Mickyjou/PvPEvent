package listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Math.floor(e.getTo().getX()) != Math.floor(e.getFrom().getX())
				|| Math.floor(e.getTo().getZ()) != Math.floor(e.getFrom().getZ())) {
			// if (p.getLocation().getChunk() != e.getTo().getChunk()) {
			// }
		}
	}



}
