package dev.xdpxi.xdlib.sodium;

public class CustomOptions {
    public static void integrate() {
        if (SodiumIntegration.isSodiumPresent()) {
            addCustomOptions();
        }
    }

    private static void addCustomOptions() {
    }
}