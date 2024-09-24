package com.ztrolix.zlibs.api.client;

import net.fabricmc.loader.api.FabricLoader;

public class loader {
    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }
}