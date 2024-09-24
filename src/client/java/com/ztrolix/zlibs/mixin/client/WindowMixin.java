package com.ztrolix.zlibs.mixin.client;

import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Window.class)
public class WindowMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwWindowHint(II)V", remap = false))
    private void hintOverride(int hint, int value) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (hint == GLFW.GLFW_CONTEXT_VERSION_MAJOR) {
            value = 4;
        } else if (hint == GLFW.GLFW_CONTEXT_VERSION_MINOR) {
            if (!osName.contains("mac")) {
                value = 6;
            }
        }

        GLFW.glfwWindowHint(hint, value);
    }
}