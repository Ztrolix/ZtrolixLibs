package com.ztrolix.zlibs.api;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class custom {
    public static void Item(String itemId) {
        Item item = new Item(new Item.Settings());
        Registry.register(Registries.ITEM, itemId, item);
    }

    public static void Block(String blockID) {
        Block block = new Block(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(1.5F, 6.0F));
        Registry.register(Registries.BLOCK, blockID, block);
    }
}