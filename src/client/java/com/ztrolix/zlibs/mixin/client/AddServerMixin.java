package com.ztrolix.zlibs.mixin.client;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.screen.multiplayer.AddServerScreen.class)
public class AddServerMixin {
    @Redirect(method = "addAndClose", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;getText()Ljava/lang/String;", ordinal = 1))
    private String trimGetText(TextFieldWidget instance) {
        return instance.getText().replace(" ", "");
    }

    @Shadow
    private ServerInfo server;

    @Inject(at = @At("TAIL"), method = "addAndClose")
    private void addAndClose(CallbackInfo ci) {
        if (server.name.equals("Minecraft Server")) {
            server.name = server.address;
        }
    }
}