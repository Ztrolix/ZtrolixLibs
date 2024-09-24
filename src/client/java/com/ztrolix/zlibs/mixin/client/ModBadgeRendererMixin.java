package com.ztrolix.zlibs.mixin.client;

import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.ModBadgeRenderer;
import com.ztrolix.zlibs.api.client.loader;
import com.ztrolix.zlibs.config.ZLibsConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;

@Mixin(ModBadgeRenderer.class)
public abstract class ModBadgeRendererMixin {
    @Shadow
    protected Mod mod;

    @Unique
    private static boolean clothConfig = loader.isModLoaded("cloth-config");

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");

    @Unique
    boolean customBadges = true;

    @Shadow
    public abstract void drawBadge(DrawContext DrawContext, OrderedText text, int outlineColor, int fillColor, int mouseX, int mouseY);

    @Inject(method = "draw", at = @At("TAIL"))
    public void drawCustomBadges(DrawContext DrawContext, int mouseX, int mouseY, CallbackInfo ci) {
        if (clothConfig) {
            try {
                Class<?> autoConfigClass = Class.forName("me.shedaniel.autoconfig.AutoConfig");
                Method getConfigHolderMethod = autoConfigClass.getMethod("getConfigHolder", Class.class);
                Object configHolder = getConfigHolderMethod.invoke(null, ZLibsConfig.class);
                ZLibsConfig config = (ZLibsConfig) configHolder.getClass().getMethod("getConfig").invoke(configHolder);
                customBadges = config.main.customBadges;
                configHolder.getClass().getMethod("save").invoke(configHolder);
            } catch (Exception e) {
                System.err.println("Error saving AutoConfig: " + e.getMessage());
            }
        }
        if (customBadges) {
            try {
                FabricLoader.getInstance().getModContainer(mod.getId()).orElse(null)
                        .getMetadata().getCustomValue("mcb").getAsArray().forEach(customValue -> {
                            var obj = customValue.getAsObject();
                            var name = obj.get("name").getAsString();
                            var outline = obj.get("outlineColor").getAsNumber().intValue();
                            var fill = obj.get("fillColor").getAsNumber().intValue();
                            drawBadge(DrawContext, Text.literal(name).asOrderedText(), outline, fill, mouseX, mouseY);
                        });
            }catch(Exception ignored){} try {
                FabricLoader.getInstance().getModContainer(mod.getId()).orElse(null)
                        .getMetadata().getCustomValue("modmenu-badges").getAsArray().forEach(customValue -> {
                            var obj = customValue.getAsObject();
                            var name = obj.get("name").getAsString();
                            var outline = obj.get("outlineColor").getAsNumber().intValue();
                            var fill = obj.get("fillColor").getAsNumber().intValue();
                            drawBadge(DrawContext, Text.literal(name).asOrderedText(), outline, fill, mouseX, mouseY);
                        });
            }catch(Exception ignored){} try {
                FabricLoader.getInstance().getModContainer(mod.getId()).orElse(null)
                        .getMetadata().getCustomValue("zlibs").getAsArray().forEach(customValue -> {
                            var obj = customValue.getAsObject();
                            var name = obj.get("name").getAsString();
                            var outline = obj.get("outlineColor").getAsNumber().intValue();
                            var fill = obj.get("fillColor").getAsNumber().intValue();
                            drawBadge(DrawContext, Text.literal(name).asOrderedText(), outline, fill, mouseX, mouseY);
                        });
            }catch(Exception ignored){}
        }
    }
}