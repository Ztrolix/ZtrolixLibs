package com.ztrolix.zlibs.api.client;

import net.minecraft.text.Text;

public class markdown {
    public static Text Parse(String markdown) {
        return Text.of(markdown);
    }
}