package dev.xdpxi.xdlib.plugin;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class welcomeListener implements Listener {
    private final xdlib plugin;

    public welcomeListener(xdlib plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore() && plugin.getConfig().getBoolean("welcomeMessage")) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome " + ChatColor.GREEN + event.getPlayer().getName() + ChatColor.GOLD + " to the server!");
        }
    }
}