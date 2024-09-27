package dev.xdpxi.xdlib;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("xdlib");
    private final Screen parent;
    private final MutableText changelogText;

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
        boolean shownToast = false;
        if (!shownToast) {
            shownToast = true;
            if (isModLoaded("iris") || isModLoaded("optifine")) {
                LOGGER.warn("[XDLib] - Does not support shaders!");

                this.client.getToastManager().add(
                        new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("XD's Library"), Text.of("Does not support shaders, somethings won't render!"))
                );
            }
        }

        int centerX = this.width / 2;
        int centerY = this.height / 2 - 20;

        ButtonWidget buttonWidget = ButtonWidget.builder(Text.literal("Dismiss"), (btn) -> {
            close();
        }).dimensions(centerX - 60, centerY + 170, 120, 20).build();
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
        if (changelogText != null) {
            int boxWidth = 625;
            int boxHeight = 350;
            int boxX = centerX - boxWidth / 2;
            int boxY = buttonY - boxHeight / 2;

            context.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xFF333333);
            context.drawBorder(boxX, boxY, boxWidth, boxHeight, 0xFF000000);

            if (this.textRenderer != null) {
                context.drawTextWrapped(this.textRenderer, this.changelogText, boxX + 5, boxY + 5, boxWidth - 10, 0xFFFFFFFF);
            }
        } else {
            LOGGER.error("Changelog text is null. Skipping changelog rendering.");
        }
    }

    private MutableText createChangelogText() {
        MutableText text = Text.literal("Changelog:\n\n")
                .setStyle(Style.EMPTY.withColor(Formatting.WHITE).withBold(true));

        text.append(Text.literal("- Reformatted Code\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated Package Name\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated Mod ID\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated Icon\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated Description\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated Name\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed Modmenu Errors\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));

        return text;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}