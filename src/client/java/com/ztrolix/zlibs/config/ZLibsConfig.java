package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "ztrolix-libs")
public class ZLibsConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("main")
    @ConfigEntry.Gui.TransitiveObject
    public main main = new main();

    @ConfigEntry.Category("compatibility")
    @ConfigEntry.Gui.TransitiveObject
    public compatibility compatibility = new compatibility();

    public static ZLibsConfig get() {
        return AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();
    }
}