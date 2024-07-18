package com.ztrolix.zlibs.mixin;

import com.ztrolix.zlibs.ZtrolixLibs;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ExampleMixin {
	Integer logLevel = 1;
	String logText = "ZtrolixLibs - Loaded World!";

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		FMLLog.log(ZtrolixLibs.MOD_ID, logLevel, logText);
	}
}