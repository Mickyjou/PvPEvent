package de.mickyjou.plugins.pvpevent.listener;

import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent e) {

        Player p = e.getPlayer();
        if (e.getMessage().trim().equalsIgnoreCase
                ("ThisIsTheSecretMessageToGetAuthenticatedForTheCraftenServer")) {
            p.sendMessage(PvPEventPlugin.prefix + "Du wurdest authentifiziert!");
            e.setCancelled(true);

        }


        if (e.getMessage().trim().equalsIgnoreCase(".check")) {
            p.kickPlayer(PvPEventPlugin.prefix + "Bitte benutze die Craften-Mod!");
            e.setCancelled(true);
        }
    }
}
