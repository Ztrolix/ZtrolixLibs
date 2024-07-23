package com.ztrolix.zlibs;

import net.fabricmc.api.ClientModInitializer;
import me.shedaniel.autoconfig.AutoConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import com.ztrolix.zlibs.config.ZLibsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ZtrolixLibsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

	}

	public static Screen getConfigScreen(Screen parent) {
		return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
	}
}