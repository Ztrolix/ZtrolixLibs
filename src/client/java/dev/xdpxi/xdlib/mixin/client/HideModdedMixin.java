package dev.xdpxi.xdlib.mixin.client;

import net.minecraft.util.ModStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModStatus.class)
public class HideModdedMixin {
    /**
     * @author author
     * @reason reason
     */
    @Overwrite
    public boolean isModded() {
        return false;
    }
}