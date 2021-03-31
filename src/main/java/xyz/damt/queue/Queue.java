package xyz.damt.queue;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Astro;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Queue {

    @Getter private static HashMap<String, Queue> queueMap = new HashMap<>();
    private final HashMap<UUID, Integer> playerMap;

    private final String queueName;
    private final String bungeeCordName;
    private final Astro astro;

    private boolean isPaused;

    public Queue(String queueName, String bungeeCordName) {
        this.queueName = queueName;
        this.bungeeCordName = bungeeCordName;
        this.astro = JavaPlugin.getPlugin(Astro.class);

        queueMap.put(queueName, this);

        this.playerMap = new HashMap<>();
        this.isPaused = false;
    }

    public static Queue getQueueByName(String name) {
        return queueMap.get(name);
    }

    public void remove() {
        queueMap.remove(queueName, this);
    }

    public int getSize() {
        return this.playerMap.size();
    }

    public void addPlayer(Player player) {
        if (playerMap.isEmpty()) {
            playerMap.put(player.getUniqueId(), 0);
            return;
        }

        playerMap.put(player.getUniqueId(), playerMap.size() + 1);
    }

    public void removePlayer(Player player) {
        playerMap.remove(player.getUniqueId());
    }

    public void setPaused(boolean value) {
        this.isPaused = value;
    }

    public Set<Player> getPlayersInQueue() {
        return Bukkit.getServer().getOnlinePlayers().stream().filter(player -> playerMap.containsKey(player.getUniqueId())).collect(Collectors.toSet());
    }

    public int getPosition(Player player) {
        return this.playerMap.get(player.getUniqueId());
    }

    public void move() {
        if (playerMap.isEmpty()) return;
        getPlayersInQueue().forEach(player -> {
            int playerPosition = getPosition(player);

            if (playerPosition == 0) {
                astro.getBungeeChannelApi().connect(player, bungeeCordName);
                player.sendMessage(astro.getMessageHandler().getSentMessage().replace("%server%", bungeeCordName));
                return;
            }

            playerMap.remove(player.getUniqueId());
            playerMap.put(player.getUniqueId(), playerPosition - 1);
            player.sendMessage(astro.getMessageHandler().getMoveMessage().replace("%position%", String.valueOf(getPosition(player)))
            .replace("%queue%", queueName).replace("%size%", String.valueOf(getSize())));
        });
    }

    public boolean isPaused() {
        return this.isPaused;
    }

}
