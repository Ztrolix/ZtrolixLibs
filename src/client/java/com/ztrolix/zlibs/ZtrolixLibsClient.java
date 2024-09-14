package com.ztrolix.zlibs;

import com.google.common.io.Files;
import com.ztrolix.zlibs.config.ZLibsConfig;
import com.ztrolix.zlibs.gui.PreLaunchWindow;
import com.ztrolix.zlibs.sodium.CustomOptions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.WorldSavePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ZtrolixLibsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    public static int duration = -1;
    public static List<HostileEntity> list = new ArrayList<>();
    private static final boolean isInServerList = false;
    public static boolean lastLocal = true;
    public static String serverName = "";
    public static String serverAddress = "";

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    public boolean isNoEarlyLoaders() {
        return !(isModLoaded("early-loading-screen") ||
                isModLoaded("early_loading_bar") ||
                isModLoaded("earlyloadingscreen") ||
                isModLoaded("mindful-loading-info") ||
                isModLoaded("neoforge") ||
                isModLoaded("connector") ||
                isModLoaded("mod-loading-screen"));
    }

    @Override
    public void onInitializeClient() {
        String osName = System.getProperty("os.name").toLowerCase();
        AutoConfig.register(ZLibsConfig.class, GsonConfigSerializer::new);
        ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();

        LOGGER.info("-----------------------------------");
        LOGGER.info("Ztrolix Libs - Applying Config...");
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
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

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.isIntegratedServerRunning()) {
                lastLocal = true;
                String levelName = client.getServer().getSaveProperties().getLevelName();
                Path pathtoSave = Path.of(Files.simplifyPath(client.getServer().getSavePath(WorldSavePath.ROOT).toString()));
                String folderName = pathtoSave.normalize().toFile().getName();
                serverName = levelName;
                serverAddress = folderName;
            } else {
                ServerInfo serverInfo = client.getCurrentServerEntry();
                lastLocal = false;
                serverName = serverInfo.name;
                serverAddress = serverInfo.address;
            }
            config.lastServer.serverName = serverName;
            config.lastServer.serverAddress = serverAddress;
            AutoConfig.getConfigHolder(ZLibsConfig.class).save();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                StatusEffectInstance darknessEffect = client.player.getStatusEffect(StatusEffects.DARKNESS);
                if (darknessEffect != null) {
                    client.player.removeStatusEffect(StatusEffects.DARKNESS);
                }
            }
        });

        if (isWindows() && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && isNoEarlyLoaders()) {
            ClientLifecycleEvents.CLIENT_STARTED.register(client -> PreLaunchWindow.remove());
        }
    }

    public void applyConfig() {
        ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();
        String osName = System.getProperty("os.name").toLowerCase();

        LOGGER.info("-----------------------------------");
        LOGGER.info("Ztrolix Libs - Applying Config...");
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
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

            if (osName.contains("win")) {
                DiscordRPCHandler.init();
                ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
                    DiscordRPCHandler.shutdown();
                });
                LOGGER.info("Discord RPC: Enabled!");
            } else {
                LOGGER.info("Discord RPC: Enabled!");
                LOGGER.info("Running on an unsupported OS: " + osName);
            }
        } else {
            LOGGER.info("Discord RPC: Disabled!");
            DiscordRPCHandler.shutdown();
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