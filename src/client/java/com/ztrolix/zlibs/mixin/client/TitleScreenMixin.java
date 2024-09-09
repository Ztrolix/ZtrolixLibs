package com.ztrolix.zlibs.mixin.client;

import com.ztrolix.zlibs.CustomScreen;
import com.ztrolix.zlibs.config.ZLibsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Unique
    private static boolean Shown = false;

    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();
        if (config.main.zlibsStartupPopup8 && !Shown) {
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return 0;
            }).thenAcceptAsync(result -> {
                Shown = true;
                config.main.zlibsStartupPopup8 = false;
                AutoConfig.getConfigHolder(ZLibsConfig.class).save();

                MinecraftClient client = MinecraftClient.getInstance();
                Screen currentScreen = client.currentScreen;

                client.execute(() -> client.setScreen(new CustomScreen(Text.empty(), currentScreen)));
            }, MinecraftClient.getInstance());
        }
    }
}