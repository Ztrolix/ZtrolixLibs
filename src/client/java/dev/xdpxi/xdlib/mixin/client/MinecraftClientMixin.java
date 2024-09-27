package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.XDsLibraryClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "hasOutline", at = @At("RETURN"), cancellable = true)
    private void showOutline(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof HostileEntity && XDsLibraryClient.list.contains(entity)) info.setReturnValue(true);
    }
}