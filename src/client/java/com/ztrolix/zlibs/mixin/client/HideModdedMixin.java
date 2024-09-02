package com.ztrolix.zlibs.mixin.client;

import net.minecraft.util.ModStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModStatus.class)
public class HideModdedMixin {
    @Overwrite
    public boolean isModded() {
        return false;
    }
}