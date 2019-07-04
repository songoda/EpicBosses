package com.songoda.epicbosses.events;

import lombok.Getter;
import com.songoda.epicbosses.holder.ActiveBossHolder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 21-Oct-18
 */
public class PreBossDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private ActiveBossHolder activeBossHolder;
    @Getter private Location location;
    @Getter private Player killer;

    public PreBossDeathEvent(ActiveBossHolder activeBossHolder, Location location, Player killer) {
        this.activeBossHolder = activeBossHolder;
        this.location = location;
        this.killer = killer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
