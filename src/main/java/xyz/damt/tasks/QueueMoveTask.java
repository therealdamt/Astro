package xyz.damt.tasks;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.Astro;

public class QueueMoveTask extends BukkitRunnable {

    private final Astro astro;

    public QueueMoveTask() {
        this.astro = JavaPlugin.getPlugin(Astro.class);
    }

    @Override
    public void run() {
        astro.getQueueHandler().getAllQueues().forEach(queue -> {
            if (queue.isPaused()) return;
            queue.move();
        });
    }
}
