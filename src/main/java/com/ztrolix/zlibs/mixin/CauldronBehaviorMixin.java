package com.ztrolix.zlibs.mixin;

import com.ztrolix.zlibs.CauldronBehaviorExtended;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
    @Inject(method = "registerBehavior", at = @At("TAIL"))
    private static void ztrolixLibs$injectRegisterBehavior(CallbackInfo ci) {
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(Items.SHIELD, CauldronBehaviorExtended.WASH_SHIELD);
    }
}