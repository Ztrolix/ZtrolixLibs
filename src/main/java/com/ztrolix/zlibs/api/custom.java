package com.ztrolix.zlibs.api;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class custom {
    public static void ItemGroup(String itemGroupID, String modID, Item itemIconID) {
        itemGroupID = itemGroupID.toLowerCase();
        modID = modID.toLowerCase();
        modID = modID.replace("-", "_");
        RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modID, "item_group"));
        ItemGroup ITEM_GROUP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(itemIconID))
                .displayName(Text.translatable("itemGroup." + modID + "." + itemGroupID))
                .build();
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP);
    }

    public static Item Item(String itemID, String modID, RegistryKey<ItemGroup> itemGroup) {
        itemID = itemID.toLowerCase();
        modID = modID.toLowerCase();
        modID = modID.replace("-", "_");
        Item.Settings settings = new Item.Settings();
        Identifier identifier = Identifier.of(modID, itemID);
        Item item = Registry.register(Registries.ITEM, identifier, new Item(settings));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        return item;
    }

    public static Block Block(String blockID, String modID, RegistryKey<ItemGroup> itemGroup) {
        blockID = blockID.toLowerCase();
        modID = modID.toLowerCase();
        modID = modID.replace("-", "_");
        Block block = new Block(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(1.5F, 6.0F));
        Identifier blockIdentifier = Identifier.of(modID, blockID);
        Block registeredBlock = Registry.register(Registries.BLOCK, blockIdentifier, block);
        BlockItem blockItem = new BlockItem(registeredBlock, new Item.Settings());
        Registry.register(Registries.ITEM, blockIdentifier, blockItem);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(blockItem));
        return block;
    }

    public static Item Weapon(String weaponID, String modID, ToolMaterial material, RegistryKey<ItemGroup> itemGroup) {
        weaponID = weaponID.toLowerCase();
        modID = modID.toLowerCase();
        modID = modID.replace("-", "_");
        Item.Settings settings = new Item.Settings().maxDamage(material.getDurability());
        Identifier identifier = Identifier.of(modID, weaponID);
        SwordItem weapon = Registry.register(Registries.ITEM, identifier, new SwordItem(material, settings));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(weapon));
        return weapon;
    }

    public static Item Armor(String armorID, String modID, ArmorItem.Type armorType, RegistryKey<ItemGroup> itemGroup) {
        armorID = armorID.toLowerCase();
        modID = modID.toLowerCase();
        modID = modID.replace("-", "_");
        Item.Settings settings = new Item.Settings();
        Identifier identifier = Identifier.of(modID, armorID);
        ArmorItem armor = Registry.register(Registries.ITEM, identifier, new ArmorItem(ArmorMaterials.IRON, armorType, settings));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(armor));
        return armor;
    }
}