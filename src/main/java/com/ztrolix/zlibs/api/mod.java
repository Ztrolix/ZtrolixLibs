package com.ztrolix.zlibs.api;

import net.fabricmc.loader.api.FabricLoader;

public class mod {
    public boolean isModLoaded(String modID) { return FabricLoader.getInstance().isModLoaded(modID); }
}