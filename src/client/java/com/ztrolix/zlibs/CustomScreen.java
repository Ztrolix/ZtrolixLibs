package com.ztrolix.zlibs;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    private final Screen parent;
    private static boolean toastShown = false;

    public CustomScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    @Override
    protected void init() {
        if (!toastShown) {
            toastShown = true;
            this.client.getToastManager().add(
                    SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.of("ZtrolixLibs"), Text.of("ZtrolixLibs has started."))
            );

            if (isModLoaded("sodium") && !isModLoaded("reeses-sodium-options")) {
                LOGGER.warn("-----------------------------------");
                LOGGER.warn("Ztrolix Libs - Recommends 'reeses-sodium-options' when using 'sodium'");
                LOGGER.warn("-----------------------------------");

                this.client.getToastManager().add(
                        SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.of("ZtrolixLibs"), Text.of("Recommends the use of 'Reese's Sodium Options' when using Sodium."))
                );
            }
        }

        int centerX = this.width / 2;
        int centerY = this.height / 2 - 20;

        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Dismiss"), (btn) -> {
            close();
        }).dimensions(centerX - 60, centerY + 20, 120, 20).build();
        this.addDrawableChild(buttonWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null || this.textRenderer == null) {
            return;
        }
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int buttonY = this.height / 2;

        context.drawText(this.textRenderer, "Welcome to ZtrolixLibs!", centerX - this.textRenderer.getWidth("Welcome to ZtrolixLibs!") / 2, buttonY - this.textRenderer.fontHeight - 30, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}