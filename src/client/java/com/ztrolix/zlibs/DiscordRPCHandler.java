package com.ztrolix.zlibs;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordRPCHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");

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
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();
    }
}