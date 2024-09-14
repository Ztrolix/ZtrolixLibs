package com.ztrolix.zlibs;

import com.ztrolix.zlibs.gui.PreLaunchWindow;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class EarlyLoadingBarPreLaunch implements PreLaunchEntrypoint {
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    public boolean isNoEarlyLoaders() {
        return !(isModLoaded("early-loading-screen") ||
                isModLoaded("early_loading_bar") ||
                isModLoaded("earlyloadingscreen") ||
                isModLoaded("mindful-loading-info") ||
                isModLoaded("neoforge") ||
                isModLoaded("connector") ||
                isModLoaded("mod-loading-screen"));
    }

    @Override
    public void onPreLaunch() {
        if (isWindows() && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && isNoEarlyLoaders()) {
            PreLaunchWindow.display();
        }
    }
}