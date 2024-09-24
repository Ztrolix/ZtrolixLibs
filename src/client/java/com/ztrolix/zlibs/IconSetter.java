package com.ztrolix.zlibs;

import net.minecraft.resource.InputSupplier;

import java.io.InputStream;

public interface IconSetter {
    void ztrolixLibs$setIcon(InputSupplier<InputStream> icon);

    void ztrolixLibs$resetIcon();
}