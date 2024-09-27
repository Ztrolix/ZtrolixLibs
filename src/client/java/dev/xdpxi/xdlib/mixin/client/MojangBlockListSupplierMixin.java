package dev.xdpxi.xdlib.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.patchy.MojangBlockListSupplier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(MojangBlockListSupplier.class)
public class MojangBlockListSupplierMixin {
    @ModifyReturnValue(method = "createBlockList", at = @At("RETURN"), remap = false)
    @Nullable
    private Predicate<String> hookCreateBlockList(Predicate<String> original) {
        return null;
    }
}