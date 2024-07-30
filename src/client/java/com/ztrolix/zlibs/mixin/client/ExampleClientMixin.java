package com.ztrolix.zlibs.mixin.client;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.ztrolix.zlibs.config.ZLibsConfig;

@Mixin(MinecraftClient.class)
public class ExampleClientMixin {
	@Unique
	ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();
	@Unique
	Logger LOGGER = LoggerFactory.getLogger("ztrolix-libs");

	@Inject(at = @At("HEAD"), method = "joinWorld")
	private void init(CallbackInfo info) {
		//if (config.injectToWorld) {
		//	LOGGER.info("-----------------------------------");
		//	LOGGER.info("ZtrolixLibs - Injected to World!");
		//	LOGGER.info("-----------------------------------");
		//}
	}
}