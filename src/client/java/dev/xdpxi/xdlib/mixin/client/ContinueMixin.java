package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.XDsLibraryClient;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.QuickPlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = TitleScreen.class, priority = 1001)
public class ContinueMixin extends Screen {
    @Unique
    private final MultiplayerServerListPinger serverListPinger = new MultiplayerServerListPinger();

    @Unique
    ButtonWidget continueButtonWidget = null;

    @Unique
    private ServerInfo serverInfo = null;

    @Unique
    private boolean isFirstRender = false;

    @Unique
    private boolean readyToShow = false;

    @Unique
    private boolean showButton = true;

    protected ContinueMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "initWidgetsNormal(II)V")
    public void drawMenuButton(int y, int spacingY, CallbackInfo info) {
        showButton = !XDsLibraryClient.serverAddress.isBlank();
        if (showButton) {
            ButtonWidget.Builder continueButtonBuilder = ButtonWidget.builder(Text.translatable("button.xdlib.continuebutton"), button -> {
                if (XDsLibraryClient.lastLocal) {
                    if (!XDsLibraryClient.serverName.isBlank()) {
                        QuickPlay.startSingleplayer(client, XDsLibraryClient.serverAddress);
                    } else {
                        showButton = false;
                    }
                } else {
                    QuickPlay.startMultiplayer(client, XDsLibraryClient.serverAddress);
                }
            });
            continueButtonBuilder.dimensions(this.width / 2 - 100, y, 98, 20);
            continueButtonWidget = continueButtonBuilder.build();
            Screens.getButtons(this).add(continueButtonWidget);
        }
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    public void initAtHead(CallbackInfo info) {
        showButton = !XDsLibraryClient.serverAddress.isBlank();
        this.isFirstRender = true;
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    public void init(CallbackInfo info) {
        showButton = !XDsLibraryClient.serverAddress.isBlank();
        if (showButton) {
            for (ClickableWidget button : Screens.getButtons(this)) {
                if (button.visible && !button.getMessage().equals(Text.translatable("button.xdlib.continuebutton"))) {
                    button.setX(this.width / 2 + 2);
                    button.setWidth(98);
                    break;
                }
            }
        }
    }

    @Unique
    private void atFirstRender() {
        showButton = !XDsLibraryClient.serverAddress.isBlank();
        if (showButton) {
            new Thread(() -> {
                if (!XDsLibraryClient.lastLocal) {
                    serverInfo = new ServerInfo(XDsLibraryClient.serverName, XDsLibraryClient.serverAddress, ServerInfo.ServerType.OTHER);
                    serverInfo.label = Text.translatable("multiplayer.status.pinging");
                    try {
                        Runnable donothing = () -> {
                        };
                        serverListPinger.add(serverInfo, donothing, donothing);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                readyToShow = true;
            }).start();
        }
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void renderAtHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        showButton = !XDsLibraryClient.serverAddress.isBlank();
        if (isFirstRender) {
            isFirstRender = false;
            atFirstRender();
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void renderAtTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        showButton = !XDsLibraryClient.serverAddress.isBlank();
        if (showButton) {
            if (continueButtonWidget.isHovered() && this.readyToShow) {
                if (XDsLibraryClient.lastLocal) {
                    if (!XDsLibraryClient.serverAddress.isEmpty()) {
                        List<OrderedText> list = new ArrayList<>();
                        list.add(Text.translatable("menu.singleplayer").formatted(Formatting.GRAY).asOrderedText());
                        list.add(Text.literal(XDsLibraryClient.serverName).asOrderedText());
                        context.drawOrderedTooltip(this.textRenderer, list, mouseX, mouseY);
                    }
                } else {
                    List<OrderedText> list = new ArrayList<>(this.client.textRenderer.wrapLines(serverInfo.label, 270));
                    list.add(0, Text.literal(serverInfo.name).formatted(Formatting.GRAY).asOrderedText());
                    context.drawOrderedTooltip(this.textRenderer, list, mouseX, mouseY);
                }
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "tick()V")
    public void tick(CallbackInfo info) {
        serverListPinger.tick();
    }

    @Inject(at = @At("RETURN"), method = "removed()V")
    public void removed(CallbackInfo info) {
        serverListPinger.cancel();
    }
}