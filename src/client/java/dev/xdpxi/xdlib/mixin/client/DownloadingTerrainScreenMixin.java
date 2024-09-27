package dev.xdpxi.xdlib.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DownloadingTerrainScreen.class)
public abstract class DownloadingTerrainScreenMixin {
    /**
     * @author author
     * @reason reason
     */
    @Overwrite
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    }
}