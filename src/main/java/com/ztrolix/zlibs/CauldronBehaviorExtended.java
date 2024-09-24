package com.ztrolix.zlibs;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.DyeColor;
import net.minecraft.util.ItemActionResult;

public interface CauldronBehaviorExtended {
    CauldronBehavior WASH_SHIELD = (state, world, pos, player, hand, stack) -> {
        BannerPatternsComponent bannerPatternsComponent = stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
        DyeColor bannerColor = stack.get(DataComponentTypes.BASE_COLOR);
        assert bannerColor != null;

        if (bannerPatternsComponent.layers().isEmpty()) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (world.isClient) return ItemActionResult.success(true);

        ItemStack shieldItemStack = stack.copy();

        shieldItemStack.remove(DataComponentTypes.BASE_COLOR);
        shieldItemStack.set(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);

        ItemStack bannerItemStack = new ItemStack(bannerItemFromColor(bannerColor));

        bannerItemStack.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent);

        stack.decrementUnlessCreative(1, player);
        if (stack.isEmpty()) {
            player.setStackInHand(hand, shieldItemStack);
        } else if (player.getInventory().insertStack(shieldItemStack)) {
            player.playerScreenHandler.syncState();
        } else {
            player.dropItem(shieldItemStack, false);
        }
        if (player.getInventory().insertStack(bannerItemStack)) {
            player.playerScreenHandler.syncState();
        } else {
            player.dropItem(bannerItemStack, false);
        }

        player.incrementStat(Stats.USE_CAULDRON);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        return ItemActionResult.success(false);
    };

    private static Item bannerItemFromColor(DyeColor color) {
        switch (color) {
            case RED -> {
                return Items.RED_BANNER;
            }
            case ORANGE -> {
                return Items.ORANGE_BANNER;
            }
            case YELLOW -> {
                return Items.YELLOW_BANNER;
            }
            case LIME -> {
                return Items.LIME_BANNER;
            }
            case GREEN -> {
                return Items.GREEN_BANNER;
            }
            case CYAN -> {
                return Items.CYAN_BANNER;
            }
            case LIGHT_BLUE -> {
                return Items.LIGHT_BLUE_BANNER;
            }
            case BLUE -> {
                return Items.BLUE_BANNER;
            }
            case MAGENTA -> {
                return Items.MAGENTA_BANNER;
            }
            case PURPLE -> {
                return Items.PURPLE_BANNER;
            }
            case PINK -> {
                return Items.PINK_BANNER;
            }
            case BROWN -> {
                return Items.BROWN_BANNER;
            }
            case WHITE -> {
                return Items.WHITE_BANNER;
            }
            case LIGHT_GRAY -> {
                return Items.LIGHT_GRAY_BANNER;
            }
            case GRAY -> {
                return Items.GRAY_BANNER;
            }
            case BLACK -> {
                return Items.BLACK_BANNER;
            }
        }
        return Items.PURPLE_BANNER;
    }
}