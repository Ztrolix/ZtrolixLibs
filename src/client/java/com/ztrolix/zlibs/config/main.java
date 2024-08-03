package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "main")
public class main implements ConfigData {
    @Comment("Enable The Mod")
    public boolean modEnabled = true;
    @Comment("Add The Mod To The World")
    public boolean injectToWorld = true;
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Count Your Client To The Player Count (Restart Required)")
    public boolean contributeToPlayerCount = true;
}
