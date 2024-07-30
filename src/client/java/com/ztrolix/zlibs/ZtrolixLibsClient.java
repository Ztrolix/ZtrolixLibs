package com.ztrolix.zlibs;

import com.ztrolix.zlibs.config.ZLibsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.gui.screen.Screen;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZtrolixLibsClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
	public static final ZLibsConfig CONFIG = new ZLibsConfig();

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ZLibsConfig.class, GsonConfigSerializer::new);
		ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();

		if (config.modEnabled) {
			LOGGER.info("-----------------------------------");
			LOGGER.info("Ztrolix Libs - Mod Enabled!");
			LOGGER.info("-----------------------------------");
		} else {
			LOGGER.info("-----------------------------------");
			LOGGER.info("Ztrolix Libs - Mod Disabled!");
			LOGGER.info("-----------------------------------");
		}

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("zlibs").executes(context -> {
				context.getSource().sendFeedback(Text.literal("-------------------------------"));
				context.getSource().sendFeedback(Text.literal("          Welcome to ZLibs!"));
				context.getSource().sendFeedback(Text.literal("-------------------------------"));
				return 1;
			}));
		});
	}

	public static Screen getConfigScreen(Screen parent) {
		return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
	}
}