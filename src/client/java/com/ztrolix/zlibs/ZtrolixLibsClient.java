package com.ztrolix.zlibs;

import com.ztrolix.zlibs.config.ZLibsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Screen;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class ZtrolixLibsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AutoConfig.register(ZLibsConfig.class, GsonConfigSerializer::new);
		ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();
	}

	public static Screen getConfigScreen(Screen parent) {
		return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
	}
}