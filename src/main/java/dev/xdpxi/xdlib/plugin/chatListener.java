package dev.xdpxi.xdlib.plugin;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class chatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        String playerName = event.getPlayer().getName();
        Component message = event.message().color(NamedTextColor.WHITE);
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Component newFormat = Component.text(currentTime, NamedTextColor.GRAY)
                .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
                .append(Component.text(playerName, NamedTextColor.GOLD))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(message);
        event.renderer((source, sourceDisplayName, msg, viewer) -> newFormat);
    }
}