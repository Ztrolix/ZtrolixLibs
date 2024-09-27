package dev.xdpxi.xdlib.mixin;

import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Unique
    private static final HashMap<UUID, Long> craftingCooldown = new HashMap<>();

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onCraftRequest", at = @At("HEAD"), cancellable = true)
    public void onCraftRequestStart(CraftRequestC2SPacket packet, CallbackInfo ci) {
        long tm = System.currentTimeMillis() - craftingCooldown.getOrDefault(player.getUuid(), 0L);
        if (tm <= 100) {
            ci.cancel();
        }
    }

    @Inject(method = "onCraftRequest", at = @At("TAIL"))
    public void onCraftRequest(CraftRequestC2SPacket packet, CallbackInfo ci) {
        craftingCooldown.put(player.getUuid(), System.currentTimeMillis());
    }
}