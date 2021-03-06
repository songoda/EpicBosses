package com.songoda.epicbosses.utils;

import com.songoda.epicbosses.EpicBosses;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 15-Oct-18
 */
public class ServerUtils {

    private static ServerUtils serverUtils;

    private JavaPlugin javaPlugin;

    public ServerUtils(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;

        serverUtils = this;
    }

    public static ServerUtils get() {
        return serverUtils;
    }

    public void log(String log) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.get().translateColor(log));
    }

    public void logError(String log) {
        log("&c[EpicBosses] Error - &7" + log);
    }

    public void logWarn(String log) {
        log("&e[EpicBosses] Warn - &7" + log);
    }

    public void logDebug(String log) {
        if (EpicBosses.getInstance().isDebug()) {
            log("&d[EpicBosses] Debug - &7" + log);
        }
    }

    public BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(this.javaPlugin, runnable);
    }

    public BukkitTask runTaskAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, runnable);
    }

    public BukkitTask runLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(this.javaPlugin, runnable, delay);
    }

    public BukkitTask runLaterAsync(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(this.javaPlugin, runnable, delay);
    }

    public BukkitTask runTimer(long delay, long periods, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimer(this.javaPlugin, runnable, delay, periods);
    }

    public BukkitTask runTimerAsync(long delay, long periods, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this.javaPlugin, runnable, delay, periods);
    }

    public void cancelTask(BukkitTask bukkitTask) {
        if (bukkitTask == null) return;

        bukkitTask.cancel();
    }

    public void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this.javaPlugin);
    }

    public void sendConsoleCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public Entity getEntity(UUID uuid) {
        for (World world : Bukkit.getWorlds())
            for (Entity entity : new ArrayList<>(world.getEntities()))
                if (entity.getUniqueId().equals(uuid))
                    return entity;
        return null;
    }
}
