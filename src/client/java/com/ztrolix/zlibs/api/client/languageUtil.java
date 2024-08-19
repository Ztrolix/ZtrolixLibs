package com.ztrolix.zlibs.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;

@Environment(EnvType.CLIENT)
public class languageUtil {
    public static String getLang() {
        return MinecraftClient.getInstance().getLanguageManager().getLanguage();
    }
}