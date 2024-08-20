package com.ztrolix.zlibs.api;

import com.ztrolix.zlibs.ZtrolixLibs;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class custom {
    private static final String name = "ztrolix-libs";

    public static void Item(String itemId) {
        Item item = new Item(new Item.Settings());

        Registry.register(Registries.ITEM, itemId, item);
    }
}