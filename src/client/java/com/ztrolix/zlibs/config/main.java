package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "main")
public class main implements ConfigData {
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Enable The Mod (Restart Required)")
    public boolean modEnabled = true;
    @Comment("Add The Mod To The World")
    public boolean injectToWorld = true;
    @Comment("Count Your Client To The Player Count")
    public boolean contributeToPlayerCount = true;
}
