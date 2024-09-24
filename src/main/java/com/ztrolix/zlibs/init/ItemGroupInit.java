package com.ztrolix.zlibs.init;

import com.ztrolix.zlibs.ZtrolixLibs;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.Optional;

public class ItemGroupInit {
    public static final Text TITLE = Text.translatable("itemGroup." + ZtrolixLibs.MOD_ID + ".zlibs_group");

    public static final ItemGroup EXAMPLE_GROUP = register("ztrolix-libs", FabricItemGroup.builder()
            .displayName(TITLE)
            .icon(ItemInit.EXAMPLE_ITEM::getDefaultStack)
            .entries((displayContext, entries) -> Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(ZtrolixLibs.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .forEach(entries::add))
            .build());

    public static <T extends ItemGroup> T register(String name, T itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, ZtrolixLibs.id(name), itemGroup);
    }

    public static void load() {
    }
}