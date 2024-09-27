package dev.xdpxi.xdlib.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ProgressScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ProgressScreen.class)
public class ProgressScreenMixin {
    /**
     * @author author
     * @reason reason
     */
    @Overwrite
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    }
}