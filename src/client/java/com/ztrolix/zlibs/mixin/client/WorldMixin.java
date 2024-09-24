package com.ztrolix.zlibs.mixin.client;

import com.ztrolix.zlibs.ZtrolixLibsClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow
    @Final
    public boolean isClient;

    @Inject(method = "hasRain(Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    public void clouds$hasRain(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (pos.getY() > ZtrolixLibsClient.getCloudHeight((World) (Object) this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getRainGradient(F)F", at = @At("RETURN"), cancellable = true)
    public void clouds$getRainGradient(float delta, CallbackInfoReturnable<Float> cir) {
        if (isClient && cir.getReturnValue() > 0) {
            cir.setReturnValue(ZtrolixLibsClient.getRainGradient((World) (Object) this, cir.getReturnValue()));
        }
    }
}