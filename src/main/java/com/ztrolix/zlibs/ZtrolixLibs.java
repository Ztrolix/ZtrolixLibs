package com.ztrolix.zlibs;

import net.fabricmc.api.ModInitializer;
import com.ztrolix.zlibs.init.ItemGroupInit;
import com.ztrolix.zlibs.init.ItemInit;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZtrolixLibs implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");
	public static final String MOD_ID = "ztrolix-libs";

	@Override
	public void onInitialize() {
		LOGGER.info("-----------------------------------");
		LOGGER.info("ZtrolixLibs - Loading...");
		LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");

		ItemGroupInit.load();
		LOGGER.info("Loaded: ItemGroupInit");
		ItemInit.load();
		LOGGER.info("Loaded: ItemInit");
		//AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		//config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		//if (config.modEnabled) {
		//	registerItems();
		//	registerItemGroups();
		//	registerCommands();
		//}
		LOGGER.info("Loaded: Config");
		ModNetworkHandler.registerServer();
		LOGGER.info("Loaded: ModNetworkHandler");

		LOGGER.info("Loaded: Commands");
		LOGGER.info("Loaded: Text");
		LOGGER.info("Loaded: Textures");
		LOGGER.info("Loaded: Models");

		LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
		LOGGER.warn("Warning: 'Error loading parent data from mod: ztrolix-libs' is a allowed error please do not report this error!");
		LOGGER.warn("Warning: 'Unsupported root entry \"author\" at line 10 column 10' is a allowed warning please do not report this warning!");

		LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
		LOGGER.info("ZtrolixLibs - Loaded!");
		LOGGER.info("-----------------------------------");
	}

	@Contract("_ -> new")
	public static @NotNull Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}