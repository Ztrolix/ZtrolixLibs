package com.ztrolix.zlibs;

import com.ztrolix.zlibs.config.ZLibsConfig;
import com.ztrolix.zlibs.sodium.CustomOptions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ZtrolixLibsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    public static final ZLibsConfig CONFIG = new ZLibsConfig();

    public static Screen getConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(ZLibsConfig.class, parent).get();
    }

    @Override
    public void onInitializeClient() {
        String osName = System.getProperty("os.name").toLowerCase();
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
            clientOnline();
            ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
                clientOffline();
            });
            LOGGER.info("Player Count: Enabled!");
        } else {
            LOGGER.info("Player Count: Disabled!");
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        if (config.compatibility.discordRPC) {

            if (osName.contains("win")) {
                DiscordRPCHandler.init();
                ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
                    DiscordRPCHandler.shutdown();
                });
                LOGGER.info("Discord RPC: Enabled!");
            } else {
                LOGGER.info("Discord RPC: Enabled!");
                LOGGER.info("Running on an unsupported OS: " + osName);
                LOGGER.info("Disabled DiscordRPC you can ReEnable it in config!");
                LOGGER.info("Discord RPC: Disabled!");

                config.compatibility.discordRPC = false;
            }
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
        if (config.features.customBlocks) {
            LOGGER.info("Custom Blocks: Enabled!");
        } else {
            LOGGER.info("Custom Blocks: Disabled!");
        }
        if (config.features.uiFramework) {
            LOGGER.info("UI Framework: Enabled!");
        } else {
            LOGGER.info("UI Framework: Disabled!");
        }
        if (config.features.worldGen) {
            LOGGER.info("World Gen: Enabled!");
        } else {
            LOGGER.info("World Gen: Disabled!");
        }
        if (config.features.modmenuCustomBadges) {
            LOGGER.info("Custom Badges: Enabled!");
        } else {
            LOGGER.info("Custom Badges: Disabled!");
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        LOGGER.info("Ztrolix Libs - Applied Config!");
        LOGGER.info("-----------------------------------");

        ConfigHolder<ZLibsConfig> holder = AutoConfig.getConfigHolder(ZLibsConfig.class);
        holder.registerSaveListener((configHolder, config1) -> {
            applyConfig();
            return ActionResult.SUCCESS;
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("zlibs").executes(context -> {
                context.getSource().sendFeedback(Text.literal("-------------------------------"));
                context.getSource().sendFeedback(Text.literal("          Welcome to ZLibs!"));
                context.getSource().sendFeedback(Text.literal("-------------------------------"));
                return 1;
            }));
        });
    }

    public void applyConfig() {
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
            clientOnline();
            ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
                clientOffline();
            });
            LOGGER.info("Player Count: Enabled!");
        } else {
            clientOffline();
            LOGGER.info("Player Count: Disabled!");
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        if (config.compatibility.discordRPC) {
            DiscordRPCHandler.init();
            ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
                DiscordRPCHandler.shutdown();
            });
            LOGGER.info("Discord RPC: Enabled!");
        } else {
            DiscordRPCHandler.shutdown();
            LOGGER.info("Discord RPC: Disabled!");
        }
        if (config.compatibility.sodiumIntegration) {
            CustomOptions.integrate();
            LOGGER.info("Sodium Integration: Enabled!");
        } else {
            LOGGER.info("Sodium Integration: Disabled!");
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        if (config.features.customBlocks) {
            LOGGER.info("Custom Blocks: Enabled!");
        } else {
            LOGGER.info("Custom Blocks: Disabled!");
        }
        if (config.features.uiFramework) {
            LOGGER.info("UI Framework: Enabled!");
        } else {
            LOGGER.info("UI Framework: Disabled!");
        }
        if (config.features.worldGen) {
            LOGGER.info("World Gen: Enabled!");
        } else {
            LOGGER.info("World Gen: Disabled!");
        }
        if (config.features.modmenuCustomBadges) {
            LOGGER.info("Custom Badges: Enabled!");
        } else {
            LOGGER.info("Custom Badges: Disabled!");
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        LOGGER.info("Ztrolix Libs - Applied Config!");
        LOGGER.info("-----------------------------------");
    }

    private void clientOnline() {
        try {
            URL url = new URL("https://ztrolix-server.vercel.app/clientOnline");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"mod\":\"ztrolix-libs\", \"status\":\"online\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clientOffline() {
        try {
            URL url = new URL("https://ztrolix-server.vercel.app/clientOffline");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"mod\":\"ztrolix-libs\", \"status\":\"offline\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}