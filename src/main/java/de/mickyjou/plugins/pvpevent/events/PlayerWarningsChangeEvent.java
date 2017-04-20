package de.mickyjou.plugins.pvpevent.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWarningsChangeEvent extends Event {

    OfflinePlayer player;
    Integer oldWarnings;
    Integer newWarnings;


    public PlayerWarningsChangeEvent(OfflinePlayer player, Integer oldWarnings, Integer newWarnings) {
        this.player = player;
        this.oldWarnings = oldWarnings;
        this.newWarnings = newWarnings;

    }

    public static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public Integer getOldWarnings() {
        return oldWarnings;
    }

    public Integer getNewWarnings() {
        return newWarnings;
    }


}
