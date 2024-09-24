package com.ztrolix.zlibs.api;

import net.fabricmc.loader.api.FabricLoader;

public class loader {
    public boolean isModLoaded(String modID) { return FabricLoader.getInstance().isModLoaded(modID); }
}