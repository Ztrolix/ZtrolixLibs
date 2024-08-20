package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "features")
public class features implements ConfigData {
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Custom Items")
    public boolean customItems = true;
    @ConfigEntry.Gui.RequiresRestart
    @Comment("Custom Blocks (Restart Required)")
    public boolean customBlocks = true;
    @ConfigEntry.Gui.RequiresRestart
    @Comment("UI Framework (Restart Required)")
    public boolean uiFramework = true;
    @ConfigEntry.Gui.RequiresRestart
    @Comment("World Generation (Restart Required)")
    public boolean worldGen = true;
    @Comment("Custom Modmenu Badges")
    public boolean modmenuCustomBadges = true;
    @Comment("Shows a Window Popup")
    public boolean popup = false;
}