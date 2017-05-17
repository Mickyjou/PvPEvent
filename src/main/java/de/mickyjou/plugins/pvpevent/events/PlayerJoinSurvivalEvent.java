package de.mickyjou.plugins.pvpevent.events;

import de.mickyjou.plugins.pvpevent.utils.StatsGetter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinSurvivalEvent extends Event{

    private final Player p;

    public PlayerJoinSurvivalEvent(Player p) {
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

    public StatsGetter getPlayerStore() { return new StatsGetter(p);}
}
