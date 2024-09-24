package com.ztrolix.zlibs;

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
    private static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
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

        text.append(Text.literal("- Cleaned 'build.gradle'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Cleaned All Code\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Rewrote entire config system\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- 'Cloth Config' No Longer Required\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Secret Terminal'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Changelog Config'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Config Helper'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Dark Window'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Dynamic Icon'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Blank Server Name -> IP'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Stop Rain Above Clouds'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Added 'Custom Death Screen'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Working on Custom Config Serializer\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated 'Installer' to '#21'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated 'Early Loading Bar'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated 'Loader' API\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Updated 'LangUtil' API\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Modmenu Custom Badges'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'MacOS Mouse Bugs'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Markdown' API\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Fish Bobber Entity'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'OpenGL Errors'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Continue' Button\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'LangUtil' API\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Discord RPC'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Server Connector'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Fixed 'Server Resource Packs'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Renamed 'Mod' API -> 'Loader' API\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Removed 'Contribute to Player Count'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Removed 'Backup Screen'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Removed 'Terrain Screen'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Removed 'Contribute to Player Count'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));
        text.append(Text.literal("- Removed 'Extra Mixin Logs'\n").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withBold(false)));

        return text;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}