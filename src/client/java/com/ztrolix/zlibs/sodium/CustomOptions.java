package com.ztrolix.zlibs.sodium;

public class CustomOptions {
    public static void integrate() {
        if (SodiumIntegration.isSodiumPresent()) {
            addCustomOptions();
        }
    }

    private static void addCustomOptions() { }
}