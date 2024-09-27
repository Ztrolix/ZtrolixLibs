package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.DarkUtils;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftMixin {
    @Unique
    private boolean hasSetDarkmode = false;

    @Inject(method = "setScreen", at = @At("HEAD"))
    public void win_act(CallbackInfo ci) {
        if (!hasSetDarkmode) {
            hasSetDarkmode = true;
            Long window = MinecraftClient.getInstance().getWindow().getHandle();
            int windowId = (int) GLFWNativeWin32.glfwGetWin32Window(window);
            DarkUtils.enableImmersiveDarkMode(windowId, true);
        }
    }
}