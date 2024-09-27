package dev.xdpxi.xdlib.mixin.client;

import net.minecraft.client.gl.GlDebug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlDebug.class)
public abstract class GlDebugMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("Suppress OpenGL Error 1280");
    @Unique
    private static boolean hasPostedMessage1280 = false;
    @Unique
    private static boolean hasPostedMessage1281 = false;
    @Unique
    private static boolean hasPostedMessage1282 = false;
    @Unique
    private static boolean hasPostedMessage2 = false;

    @Inject(at = @At(value = "HEAD"), method = "info(IIIIIJJ)V", cancellable = true)
    private static void suppressMessage(int source, int type, int id, int severity, int messageLength, long message, long l, CallbackInfo ci) {
        if (id == 1280) {
            if (hasPostedMessage1280) {
                ci.cancel();
            } else {
                hasPostedMessage1280 = true;
            }
        }
        if (id == 1281) {
            if (hasPostedMessage1281) {
                ci.cancel();
            } else {
                hasPostedMessage1281 = true;
            }
        }
        if (id == 1282) {
            if (hasPostedMessage1282) {
                ci.cancel();
            } else {
                hasPostedMessage1282 = true;
            }
        }
        if (id == 2) {
            if (hasPostedMessage2) {
                ci.cancel();
            } else {
                hasPostedMessage2 = true;
            }
        }
    }
}