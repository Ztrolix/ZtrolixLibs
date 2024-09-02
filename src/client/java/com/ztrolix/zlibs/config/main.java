package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "main")
public class main implements ConfigData {
    @Comment("Add The Mod To The World (Not Working)")
    public boolean injectToWorld = false;
    @Comment("Count Your Client To The Player Count")
    public boolean contributeToPlayerCount = true;
    @Comment("Show Changelog On Statup (Disables Each Startup)")
    public boolean zlibsStartupPopup1 = true;
}
