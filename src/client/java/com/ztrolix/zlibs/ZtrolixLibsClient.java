package com.ztrolix.zlibs;

import com.ztrolix.zlibs.config.ZLibsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

public class ZtrolixLibsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

	}

	public static Screen getConfigScreen(Screen parent) {
		return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
	}
}