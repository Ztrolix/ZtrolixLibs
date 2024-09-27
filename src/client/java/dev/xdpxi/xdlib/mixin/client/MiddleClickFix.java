package dev.xdpxi.xdlib.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MiddleClickFix {
    @Shadow
    public HitResult crosshairTarget;
    @Shadow
    public ClientPlayerEntity player;
    @Shadow
    public ClientPlayerInteractionManager interactionManager;

    @Inject(
            method = "doItemPick",
            at = @At(value = "HEAD", target = "Lnet/minecraft/client/MinecraftClient;bl:Z", ordinal = 0)
    )
    public void inject(CallbackInfo ci) {
        HitResult target = crosshairTarget;
        if (player == null) return;
        boolean bl = player.getAbilities().creativeMode;
        if (!bl && target != null && target.getType() == HitResult.Type.ENTITY) {
            ItemStack itemStack = ((EntityHitResult) target).getEntity().getPickBlockStack();
            if (itemStack == null) return;
            PlayerInventory inventory = player.getInventory();
            int i = inventory.getSlotWithStack(itemStack);
            if (i == -1) return;
            if (PlayerInventory.isValidHotbarIndex(i)) {
                inventory.selectedSlot = i;
            } else {
                interactionManager.pickFromInventory(i);
            }
        }
    }
}