package com.ztrolix.zlibs.api.client;

import net.fabricmc.loader.api.FabricLoader;

public class mod {
    public boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }
}