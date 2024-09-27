package dev.xdpxi.xdlib.sodium;

public class SodiumIntegration {
    public static boolean isSodiumPresent() {
        try {
            Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionsGUI");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}