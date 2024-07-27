package com.ztrolix.zlibs;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.ztrolix.zlibs.config.ZLibsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.gui.screen.Screen;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.text.Text;

public class ZtrolixLibsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AutoConfig.register(ZLibsConfig.class, GsonConfigSerializer::new);
		ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("zlibs").executes(context -> {
				context.getSource().sendFeedback(Text.literal("-------------------------------"));
				context.getSource().sendFeedback(Text.literal("          Welcome to ZLibs!"));
				context.getSource().sendFeedback(Text.literal("   To get started run /zlibs help!"));
				context.getSource().sendFeedback(Text.literal("-------------------------------"));
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("zlibs")
				.then(ClientCommandManager.argument("help", StringArgumentType.string())
					.executes(context -> {
						int value1 = StringArgumentType.getString(context,);
							context.getSource().sendFeedback(Text.literal("-------------------------------"));
							context.getSource().sendFeedback(Text.literal("          Welcome to ZLibs!"));
							context.getSource().sendFeedback(Text.literal("   To get started run /zlibs help!"));
							context.getSource().sendFeedback(Text.literal("-------------------------------"));
						return 1;
					})
				)
			);
		});
	}

	public static Screen getConfigScreen(Screen parent) {
		return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
	}
}