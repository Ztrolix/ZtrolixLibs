package com.ztrolix.zlibs.mixin.client;

import com.ztrolix.zlibs.ZtrolixLibsClient;
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
        if (ZtrolixLibsClient.duration == 0)
            ZtrolixLibsClient.list = new ArrayList<>();
        if (ZtrolixLibsClient.duration >= 0)
            ZtrolixLibsClient.duration--;
    }
}