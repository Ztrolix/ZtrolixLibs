package com.ztrolix.zlibs;

import com.ztrolix.zlibs.config.ZLibsConfig;
import com.ztrolix.zlibs.sodium.CustomOptions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZtrolixLibsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    public static final ZLibsConfig CONFIG = new ZLibsConfig();

    public static Screen getConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
    }

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ZLibsConfig.class, GsonConfigSerializer::new);
        ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();

        LOGGER.info("-----------------------------------");
        LOGGER.info("Ztrolix Libs - Applying Config...");
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        if (config.main.modEnabled) {
            LOGGER.info("Mod: Enabled!");
        } else {
            LOGGER.info("Mod: Disabled!");
        }
        if (config.main.injectToWorld) {
            LOGGER.info("Inject: Enabled!");
        } else {
            LOGGER.info("Inject: Disabled!");
        }
        if (config.main.contributeToPlayerCount) {
            LOGGER.info("Player Count: Enabled!");
        } else {
            LOGGER.info("Player Count: Disabled!");
        }
        if (config.compatibility.discordRPC) {
            DiscordRPCHandler.init();
            LOGGER.info("Discord RPC: Enabled!");
        } else {
            LOGGER.info("Discord RPC: Disabled!");
        }
        if (config.compatibility.sodiumIntegration) {
            CustomOptions.integrate();
            LOGGER.info("Sodium Integration: Enabled!");
        } else {
            LOGGER.info("Sodium Integration: Disabled!");
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        LOGGER.info("Ztrolix Libs - Applied Config!");
        LOGGER.info("-----------------------------------");

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("zlibs").executes(context -> {
                context.getSource().sendFeedback(Text.literal("-------------------------------"));
                context.getSource().sendFeedback(Text.literal("          Welcome to ZLibs!"));
                context.getSource().sendFeedback(Text.literal("-------------------------------"));
                return 1;
            }));
        });
    }
}