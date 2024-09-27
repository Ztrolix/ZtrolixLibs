package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.XDsLibraryClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        if (XDsLibraryClient.duration == 0)
            XDsLibraryClient.list = new ArrayList<>();
        if (XDsLibraryClient.duration >= 0)
            XDsLibraryClient.duration--;
    }
}