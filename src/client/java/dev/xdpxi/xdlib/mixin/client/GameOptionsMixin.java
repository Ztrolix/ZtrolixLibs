package dev.xdpxi.xdlib.mixin.client;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Unique
    public boolean onboardAccessibility = false;
}