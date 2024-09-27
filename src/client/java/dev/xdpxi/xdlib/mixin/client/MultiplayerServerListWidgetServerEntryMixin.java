package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.XDsLibraryClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerServerListWidgetServerEntryMixin {
    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V", at = @At("HEAD"))
    private void onMultiplayerStart(ServerInfo entry, CallbackInfo ci) {
        XDsLibraryClient.setIcon(entry.getFavicon());
    }
}