package com.ztrolix.zlibs;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    private final Screen parent;
    private static boolean toastShown = false;
    private int progress = 0;
    private SystemToast currentToast;
    private final MutableText changelogText;
    private final boolean Shown = false;

    public CustomScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
        this.changelogText = createChangelogText();
    }

    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    @Override
    protected void init() {
        if (!toastShown) {
            toastShown = true;
            this.client.execute(() -> {
                ToastManager toastManager = this.client.getToastManager();
                currentToast = new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("ZtrolixLibs"), Text.of("Updating..."));
                toastManager.add(currentToast);
            });
            new Thread(this::simulateLoading, "Update-Thread").start();
        }

        int centerX = this.width / 2;
        int centerY = this.height / 2 - 20;

        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Dismiss"), (btn) -> {
            close();
        }).dimensions(centerX - 60, centerY + 110, 120, 20).build();
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

        context.drawText(this.textRenderer, "Welcome to ZtrolixLibs!", centerX - this.textRenderer.getWidth("Welcome to ZtrolixLibs!") / 2, buttonY - this.textRenderer.fontHeight - 130, 0xFFFFFFFF, true);

        if (changelogText != null) {
            int boxWidth = 300;
            int boxHeight = 200;
            int boxX = centerX - boxWidth / 2;
            int boxY = buttonY - boxHeight / 2 - 20;

            renderChangelogBox(context, boxX, boxY);
        } else {
            LOGGER.error("Changelog text is null. Skipping changelog rendering.");
        }
    }

    private void renderChangelogBox(DrawContext context, int x, int y) {
        int boxWidth = 300;
        int boxHeight = 200;

        context.fill(x, y, x + boxWidth, y + boxHeight, 0xFF333333);
        context.drawBorder(x, y, boxWidth, boxHeight, 0xFF000000);

        if (this.textRenderer != null && this.changelogText != null) {
            context.drawTextWrapped(this.textRenderer, this.changelogText, x + 5, y + 5, boxWidth - 10, 0xFFFFFFFF);
        }
    }

    private MutableText createChangelogText() {
        MutableText text = Text.literal("Changelog:\n\n")
                .setStyle(Style.EMPTY.withColor(Formatting.WHITE).withBold(true));

        text.append(Text.literal("- Updated Installer -> #20\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added NeoForge Support\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed Config with NeoForge\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Removed Custom Bubbles\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));

        return text;
    }

    private void simulateLoading() {
        try {
            LOGGER.info("-----------------------------------");
            LOGGER.info("Ztrolix Libs - Updating...");
            Thread.sleep(1000);
            while (progress < 100) {
                progress++;
                updateToastProgress();
                Thread.sleep(35);
            }
            Thread.sleep(1000);
            LOGGER.info("Ztrolix Libs - Updated!");
            LOGGER.info("-----------------------------------");

            this.client.execute(() -> {
                if (currentToast != null) {
                    currentToast.setContent(Text.of("ZtrolixLibs"), Text.of("ZtrolixLibs has updated!"));
                }

                if (isModLoaded("sodium") && !isModLoaded("reeses-sodium-options")) {
                    LOGGER.warn("-----------------------------------");
                    LOGGER.warn("Ztrolix Libs - Recommends 'reeses-sodium-options' when using 'sodium'");
                    LOGGER.warn("-----------------------------------");

                    this.client.getToastManager().add(
                            new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("ZtrolixLibs"), Text.of("Recommends the use of 'Reese's Sodium Options' when using Sodium."))
                    );
                }
                if (isModLoaded("iris") || isModLoaded("optifine")) {
                    LOGGER.warn("-----------------------------------");
                    LOGGER.warn("Ztrolix Libs - Does not support shaders!");
                    LOGGER.warn("-----------------------------------");

                    this.client.getToastManager().add(
                            new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("ZtrolixLibs"), Text.of("Does not support shaders, somethings won't render!"))
                    );
                }
            });

        } catch (InterruptedException e) {
            LOGGER.error("Loading simulation interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void updateToastProgress() {
        this.client.execute(() -> {
            if (currentToast != null) {
                currentToast.setContent(Text.of("Updating... " + progress + "%"), null);
            }
        });
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}