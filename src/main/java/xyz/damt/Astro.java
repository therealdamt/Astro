package xyz.damt;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.handlers.MessageHandler;
import xyz.damt.queue.QueueHandler;
import xyz.damt.tasks.QueueMoveTask;

public class Astro extends JavaPlugin {

    @Getter private static Astro instance;

    @Getter private QueueHandler queueHandler;
    @Getter private BungeeChannelApi bungeeChannelApi;
    @Getter private MessageHandler messageHandler;

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.messageHandler = new MessageHandler();
        this.queueHandler = new QueueHandler();
        this.queueHandler.loadAllQueues();

        this.bungeeChannelApi = BungeeChannelApi.of(this);

        new QueueMoveTask().runTaskTimerAsynchronously(this, getConfig().getInt("settings.delay") * 20L, getConfig().getInt("settings.delay") * 20L);

        new AstroAPI();
    }

}
