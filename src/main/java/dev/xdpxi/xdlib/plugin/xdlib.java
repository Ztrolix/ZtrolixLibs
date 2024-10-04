package dev.xdpxi.xdlib.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class xdlib extends JavaPlugin {
    private welcomeListener WelcomeListener;
    private chatListener ChatListener;
    private joinLeaveListener JoinLeaveListener;

    @Override
    public void onEnable() {
        setConfig();
        this.getCommand("xdlib").setTabCompleter(new tabCompleter());
        getLogger().info("[XDLib] - Enabled!");
    }

    private void setConfig() {
        saveDefaultConfig();

        boolean welcomeMessage = getConfig().getBoolean("welcomeMessage");
        if (WelcomeListener != null) {
            HandlerList.unregisterAll(WelcomeListener);
        }
        if (welcomeMessage) {
            WelcomeListener = new welcomeListener(this);
            getServer().getPluginManager().registerEvents(WelcomeListener, this);
        }

        boolean customChatMessages = getConfig().getBoolean("customChatMessages");
        if (ChatListener != null) {
            HandlerList.unregisterAll(ChatListener);
        }
        if (customChatMessages) {
            ChatListener = new chatListener();
            getServer().getPluginManager().registerEvents(ChatListener, this);
        }

        boolean customJoinMessage = getConfig().getBoolean("customJoinMessages");
        if (JoinLeaveListener != null) {
            HandlerList.unregisterAll(JoinLeaveListener);
        }
        if (customJoinMessage) {
            JoinLeaveListener = new joinLeaveListener(this);
            getServer().getPluginManager().registerEvents(JoinLeaveListener, this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("[XDLib] - Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("xdlib")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("xdlib.reload")) {
                        reloadConfig();
                        setConfig();
                        sender.sendMessage("[XDLib] - Config reloaded!");
                    } else {
                        sender.sendMessage("[XDLib] - You do not have permission to execute this command.");
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage("Available commands:\n/xdlib - Displays the configured message\n/xdlib reload - Reloads the plugin configuration\n/xdlib help - Shows this help message");
                } else {
                    sender.sendMessage("[XDLib] - Use '/xdlib help' for a list of commands.");
                }
            } else {
                String message = getConfig().getString("message");
                sender.sendMessage(message);
            }
            return true;
        }
        return false;
    }
}