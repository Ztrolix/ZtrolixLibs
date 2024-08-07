package com.ztrolix.zlibs;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordRPCHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    private static Thread callbackThread;

    public static void init() {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "1268895788558319626";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> LOGGER.info("ZtrolixLibs - Started Discord RPC!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = "Playing Minecraft";
        lib.Discord_UpdatePresence(presence);

        callbackThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            LOGGER.info("ZtrolixLibs - Callback thread interrupted.");
        }, "RPC-Callback-Handler");
        callbackThread.start();
    }

    public static void shutdown() {
        if (callbackThread != null && callbackThread.isAlive()) {
            callbackThread.interrupt();
            try {
                callbackThread.join();
            } catch (InterruptedException e) {
                LOGGER.error("ZtrolixLibs - Failed to join callback thread.", e);
                Thread.currentThread().interrupt();
            }
        }
        DiscordRPC.INSTANCE.Discord_Shutdown();
    }
}