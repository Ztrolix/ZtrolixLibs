package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "compatibility")
public class compatibility implements ConfigData {
    @ConfigEntry.Gui.PrefixText
    @Comment("Enable Discord RPC")
    public boolean discordRPC = true;
    @Comment("Add ZtrolixLibs Settings to Sodium Video Settings")
    public boolean sodiumIntegration = false;
}