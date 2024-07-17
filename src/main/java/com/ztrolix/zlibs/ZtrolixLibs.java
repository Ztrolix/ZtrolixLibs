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
	public static final String MOD_ID = "zlibs";

	@Override
	public void onInitialize() {
		LOGGER.info("ZtrolixLibs - Loading...");

		ItemGroupInit.load();
		LOGGER.info("ZtrolixLibs - Loaded: ItemGroupInit");
		ItemInit.load();
		LOGGER.info("ZtrolixLibs - Loaded: ItemInit");

		LOGGER.info("ZtrolixLibs - Loaded!");
	}

	@Contract("_ -> new")
	public static @NotNull Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}