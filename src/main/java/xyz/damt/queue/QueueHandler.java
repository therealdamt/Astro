package xyz.damt.queue;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Astro;

import java.util.Collection;

public class QueueHandler {

    private final Astro astro;

    public QueueHandler() {
        this.astro = JavaPlugin.getPlugin(Astro.class);
    }

    public void loadAllQueues() {
        astro.getConfig().getConfigurationSection("queue").getKeys(false).forEach(s -> {
            new Queue(astro.getConfig().getString("queue." + s + ".name"), astro.getConfig().getString("queue." + s + ".bungeecord"));
        });
    }

    public Collection<Queue> getAllQueues() {
        return Queue.getQueueMap().values();
    }


}
