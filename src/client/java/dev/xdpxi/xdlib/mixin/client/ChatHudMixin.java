package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.accessor.AbstractClientPlayerEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Environment(EnvType.CLIENT)
@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Shadow
    @Final
    @Mutable
    private MinecraftClient client;

    @Inject(method = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"))
    private void addMessageMixin(Text message, @Nullable MessageSignatureData signature, @Nullable MessageIndicator indicator, CallbackInfo info) {
        if (client != null && client.player != null) {
            String detectedSenderName = extractSender(message);
            if (!detectedSenderName.isEmpty()) {
                UUID senderUUID = this.client.getSocialInteractionsManager().getUuid(detectedSenderName);

                List<AbstractClientPlayerEntity> list = client.world.getEntitiesByClass(AbstractClientPlayerEntity.class, client.player.getBoundingBox().expand(30.0D),
                        EntityPredicates.EXCEPT_SPECTATOR);

                list.remove(client.player);
                for (AbstractClientPlayerEntity abstractClientPlayerEntity : list)
                    if (abstractClientPlayerEntity.getUuid().equals(senderUUID)) {
                        String stringMessage = message.getString();
                        stringMessage = stringMessage.replaceFirst("[\\s\\S]*" + detectedSenderName + "([^\\p{L}§]|(§.)?)+\\s+", "");
                        String[] string = stringMessage.split(" ");
                        List<String> stringList = new ArrayList<>();
                        String stringCollector = "";

                        int width = 0;
                        int height = 0;
                        for (int u = 0; u < string.length; u++) {
                            if (client.textRenderer.getWidth(stringCollector) < 180
                                    && client.textRenderer.getWidth(stringCollector) + client.textRenderer.getWidth(string[u]) <= 180) {
                                stringCollector = stringCollector + " " + string[u];
                                if (u == string.length - 1) {
                                    stringList.add(stringCollector);
                                    height++;
                                    if (width < client.textRenderer.getWidth(stringCollector)) {
                                        width = client.textRenderer.getWidth(stringCollector);
                                    }
                                }
                            } else {
                                stringList.add(stringCollector);

                                height++;
                                if (width < client.textRenderer.getWidth(stringCollector)) {
                                    width = client.textRenderer.getWidth(stringCollector);
                                }

                                stringCollector = string[u];

                                if (u == string.length - 1) {
                                    stringList.add(stringCollector);
                                    height++;
                                    if (width < client.textRenderer.getWidth(stringCollector)) {
                                        width = client.textRenderer.getWidth(stringCollector);
                                    }
                                }
                            }
                        }

                        if (width % 2 != 0) {
                            width++;
                        }
                        ((AbstractClientPlayerEntityAccessor) abstractClientPlayerEntity).setChatText(stringList, abstractClientPlayerEntity.age, width, height);
                        break;
                    }
            }
        }
    }

    @Unique
    private String extractSender(Text text) {
        String[] words = text.getString().split("(§.)|[^\\w§]+");
        String[] parts = text.toString().split("key='");

        if (parts.length > 1) {
            String translationKey = parts[1].split("'")[0];
            if (translationKey.contains("commands")) {
                return "";
            } else if (translationKey.contains("advancement")) {
                return "";
            }
        }

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            UUID possibleUUID = this.client.getSocialInteractionsManager().getUuid(word);
            if (possibleUUID != Util.NIL_UUID) {
                return word;
            }
        }

        return "";
    }
}