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

		LOGGER.info("Loaded: Commands");
		LOGGER.info("Loaded: Config");

		LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
		LOGGER.warn("Warning: 'Error loading parent data from mod: ztrolix-libs' is a allowed error please do not report this error!");
		LOGGER.warn("Warning: 'Unsupported root entry \"author\" at line 10 column 10' is a allowed error please do not report this error!");

		LOGGER.info("-- -- -- -- -- -- -- -- -- -- -- --");
		LOGGER.info("ZtrolixLibs - Loaded!");
		LOGGER.info("-----------------------------------");
	}

	@Contract("_ -> new")
	public static @NotNull Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}