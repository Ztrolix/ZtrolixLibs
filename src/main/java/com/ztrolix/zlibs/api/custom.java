package com.ztrolix.zlibs.api;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class custom {
    public static void Item(String itemID, String modID) {
        itemID = itemID.toLowerCase();
        modID = modID.toLowerCase();
        Item.Settings settings = new Item.Settings();
        Identifier identifier = Identifier.of(modID.replace("-", "_"), itemID);
        Registry.register(Registries.ITEM, identifier, new Item(settings));
    }

    public static void Block(String blockID, String modID) {
        blockID = blockID.toLowerCase();
        modID = modID.toLowerCase();
        Block block = new Block(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(1.5F, 6.0F));
        Identifier blockIdentifier = Identifier.of(modID.replace("-", "_"), blockID);
        Block registeredBlock = Registry.register(Registries.BLOCK, blockIdentifier, block);
        BlockItem blockItem = new BlockItem(registeredBlock, new Item.Settings());
        Registry.register(Registries.ITEM, blockIdentifier, blockItem);
    }
}