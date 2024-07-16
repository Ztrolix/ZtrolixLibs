package com.ztrolix.zlibs;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZtrolixLibs implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");

	@Override
	public void onInitialize() {
		LOGGER.info("Ztrolix Libs has been Initialized!");
	}
}