package xyz.damt.handlers;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Astro;
import xyz.damt.util.CC;

public class MessageHandler {

    private final FileConfiguration astro;

    @Getter private final String moveMessage;
    @Getter private final String sentMessage;

    public MessageHandler() {
        this.astro = JavaPlugin.getPlugin(Astro.class).getConfig();

        this.moveMessage = CC.translate(astro.getString("messages.moved"));
        this.sentMessage = CC.translate(astro.getString("messages.sent"));
    }

}
