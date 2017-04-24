package de.mickyjou.plugins.pvpevent.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinLobbyEvent extends Event{



    private final Player p;

    public PlayerJoinLobbyEvent(Player p) {
        this.p = p;
    }

    public static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return p;
    }
}
