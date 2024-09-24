package com.ztrolix.zlibs.mixin.client;

import com.ztrolix.zlibs.IconSetter;
import com.ztrolix.zlibs.ZtrolixLibsClient;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.InputSupplier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin2 implements IconSetter {
    @Shadow
    @Nullable
    public Screen currentScreen;

    @Shadow
    @Final
    private Window window;

    @Shadow
    public abstract DefaultResourcePack getDefaultResourcePack();

    public void ztrolixLibs$setIcon(InputSupplier<InputStream> icon) {
        if (MinecraftClient.IS_SYSTEM_MAC) {
            return;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            GLFWImage.Buffer buffer = GLFWImage.malloc(1, memoryStack);
            try (NativeImage nativeImage = NativeImage.read(icon.get())) {
                ByteBuffer byteBuffer = MemoryUtil.memAlloc(nativeImage.getWidth() * nativeImage.getHeight() * 4);
                byteBuffer.asIntBuffer().put(nativeImage.copyPixelsRgba());
                buffer.position(0);
                buffer.width(nativeImage.getWidth());
                buffer.height(nativeImage.getHeight());
                buffer.pixels(byteBuffer);
                GLFW.glfwSetWindowIcon(this.window.getHandle(), buffer.position(0));
                MemoryUtil.memFree(byteBuffer);
            } catch (IOException e) {
                ZtrolixLibsClient.LOGGER.error("Could not set icon");
            }
        }
    }

    @Override
    public void ztrolixLibs$resetIcon() {
        try {
            this.window.setIcon(this.getDefaultResourcePack(), SharedConstants.getGameVersion().isStable() ? Icons.RELEASE : Icons.SNAPSHOT);
        } catch (IOException iOException) {
            ZtrolixLibsClient.LOGGER.error("Could not set icon back to default");
        }
    }


    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;updateWindowTitle()V"))
    private void onScreenChanged(Screen screen, CallbackInfo ci) {
        if (this.currentScreen instanceof TitleScreen || this.currentScreen instanceof SelectWorldScreen || this.currentScreen instanceof MultiplayerScreen) {
            ztrolixLibs$resetIcon();
        }
    }
}