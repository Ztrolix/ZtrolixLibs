package com.ztrolix.zlibs.init;

import com.ztrolix.zlibs.ZtrolixLibs;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemInit {
    public static final Item EXAMPLE_ITEM = register("zlibs_item", new Item(new Item.Settings()));

    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, ZtrolixLibs.id(name), item);
    }

    public static void load() {
    }
}