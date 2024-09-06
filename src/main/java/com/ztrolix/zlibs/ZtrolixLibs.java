package com.ztrolix.zlibs;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ztrolix.zlibs.api.custom;
import com.ztrolix.zlibs.init.ItemGroupInit;
import com.ztrolix.zlibs.init.ItemInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.*;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.command.argument.MessageArgumentType.getMessage;
import static net.minecraft.command.argument.MessageArgumentType.message;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ZtrolixLibs implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
    public static final String MOD_ID = "ztrolix-libs";

    public static GameRules.Key<GameRules.IntRule> VAULT_BLOCK_COOLDOWN;
    public static GameRules.Key<GameRules.IntRule> OMINOUS_VAULT_BLOCK_COOLDOWN;

    private static boolean eulaAccepted = false;

    public static void acceptEula() {
        eulaAccepted = true;
    }

    public static boolean isEulaAccepted() {
        return eulaAccepted;
    }

    @Contract("_ -> new")
    public static @NotNull Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static int broadcast(ServerCommandSource source, String message) throws CommandSyntaxException {
        try {
            ServerPlayerEntity player = source.getPlayer();

            if (message.contains(" ") || !(message.contains("https://") || message.contains("http://"))) {
                final Text error = Text.literal("Links cannot have spaces and must have http://").formatted(Formatting.RED, Formatting.ITALIC);
                assert player != null;
                player.sendMessage(error, false);
                return -1;
            }

            assert player != null;
            AbstractTeam abstractTeam = player.getScoreboardTeam();
            Formatting playerColor = abstractTeam != null && abstractTeam.getColor() != null ? abstractTeam.getColor() : Formatting.WHITE;

            final Text announceText = Text.literal("")
                    .append(Text.literal(source.getName()).formatted(playerColor).formatted())
                    .append(Text.literal(" has a link to share!").formatted());
            final Text text = Text.literal(message).styled(s ->
                    s.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, message))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to Open Link!")))
                            .withColor(Formatting.BLUE).withUnderline(true));

            source.getServer().getPlayerManager().broadcast(announceText, false);
            source.getServer().getPlayerManager().broadcast(text, false);
            return Command.SINGLE_SUCCESS; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int whisper(ServerCommandSource source, String message, ServerPlayerEntity target) throws CommandSyntaxException {
        try {
            ServerPlayerEntity player = source.getPlayer();

            if (message.contains(" ") || !(message.contains("https://") || message.contains("http://"))) {
                final Text error = Text.literal("Links cannot have spaces and must have http://").formatted(Formatting.RED, Formatting.ITALIC);
                assert player != null;
                player.sendMessage(error, false);
                return -1;
            }

            assert player != null;
            AbstractTeam abstractTeam = player.getScoreboardTeam();
            Formatting playerColor = abstractTeam != null && abstractTeam.getColor() != null ? abstractTeam.getColor() : Formatting.WHITE;

            if (!player.equals(target)) {
                final Text senderText = Text.literal("")
                        .append(Text.literal("You whisper a link to ").formatted(Formatting.GRAY, Formatting.ITALIC))
                        .append(Text.literal(source.getName()).formatted(playerColor).formatted(Formatting.ITALIC));

                final Text senderLink = Text.literal(message).styled(s ->
                        s.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, message))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to Open Link!")))
                                .withColor(Formatting.BLUE).withItalic(true));
                player.sendMessage(senderText);
                player.sendMessage(senderLink);
            }

            final Text announceText = Text.literal("")
                    .append(Text.literal(source.getName()).formatted(playerColor).formatted(Formatting.ITALIC))
                    .append(Text.literal(" whispers a link to you!").formatted(Formatting.GRAY, Formatting.ITALIC));
            final Text text = Text.literal(message).styled(s ->
                    s.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, message))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to Open Link!")))
                            .withColor(Formatting.BLUE).withItalic(true).withUnderline(true));

            target.sendMessage(announceText);
            target.sendMessage(text);
            return Command.SINGLE_SUCCESS; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("-----------------------------------");
        LOGGER.info("ZtrolixLibs - Loading...");
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            LOGGER.info("Running on MacOS");
        } else if (osName.contains("win")) {
            LOGGER.info("Running on Windows");
        } else {
            LOGGER.info("Running on an unsupported OS: " + osName);
        }
        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");

        //ItemGroupInit.load();
        LOGGER.info("Loaded: ItemGroupInit");
        //ItemInit.load();
        LOGGER.info("Loaded: ItemInit");
        LOGGER.info("Loaded: Config");
        ModNetworkHandler.registerServer();
        LOGGER.info("Loaded: ModNetworkHandler");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, registrationEnvironment) -> {
            dispatcher.register(literal("link")
                    .then(argument("message", message())
                            .executes(ctx -> broadcast(ctx.getSource(), getMessage(ctx, "message").getString())))
            );
            dispatcher.register(literal("linkwhisper")
                    .then(argument("player", EntityArgumentType.player())
                            .then(argument("message", message())
                                    .executes(ctx -> whisper(ctx.getSource(), getMessage(ctx, "message").getString(), EntityArgumentType.getPlayer(ctx, "player")))))
            );
        });
        LOGGER.info("Loaded: ChatLinks");
        VAULT_BLOCK_COOLDOWN = GameRuleRegistry.register("vaultBlockCooldown", GameRules.Category.MISC, GameRuleFactory.createIntRule(720_000, 1));
        OMINOUS_VAULT_BLOCK_COOLDOWN = GameRuleRegistry.register("ominousVaultBlockCooldown", GameRules.Category.MISC, GameRuleFactory.createIntRule(864_000, 1));
        LOGGER.info("Loaded: GameRules");

        LOGGER.info("Loaded: SodiumOptions");
        LOGGER.info("Loaded: DiscordRPC");
        LOGGER.info("Loaded: Commands");
        LOGGER.info("Loaded: Text");
        LOGGER.info("Loaded: Textures");
        LOGGER.info("Loaded: Models");
        LOGGER.info("Loaded: Mixin");
        LOGGER.info("Loaded: ModmenuCustomBadges");
        LOGGER.info("Loaded: LinuxKeyboardFix");
        LOGGER.info("Loaded: MiddleClickFix");
        LOGGER.info("Loaded: CustomBlocks");
        LOGGER.info("Loaded: CustomItems");
        LOGGER.info("Loaded: PopupAlert");
        LOGGER.info("Loaded: LanguageUtil");
        LOGGER.info("Loaded: Sounds");

        Item zlibs_item = custom.Item("zlibs_item", MOD_ID, ItemGroups.TOOLS);
        List<Item> items = Arrays.asList(
                zlibs_item
        );
        custom.ItemGroup("zlibs_group", MOD_ID, zlibs_item, items);

        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        LOGGER.warn("Warning: 'Error loading parent data from mod: ztrolix-libs' is a allowed error please do not report this error!");

        LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
        LOGGER.info("ZtrolixLibs - Loaded!");
        LOGGER.info("-----------------------------------");
    }
}