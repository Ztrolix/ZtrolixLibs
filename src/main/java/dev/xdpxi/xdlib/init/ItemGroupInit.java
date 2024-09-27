package dev.xdpxi.xdlib.init;

import dev.xdpxi.xdlib.XDsLibrary;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.Optional;

public class ItemGroupInit {
    public static final Text TITLE = Text.translatable("itemGroup." + XDsLibrary.MOD_ID + ".xdlib_group");

    public static final ItemGroup EXAMPLE_GROUP = register("xdlib", FabricItemGroup.builder()
            .displayName(TITLE)
            .icon(ItemInit.EXAMPLE_ITEM::getDefaultStack)
            .entries((displayContext, entries) -> Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(XDsLibrary.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .forEach(entries::add))
            .build());

    public static <T extends ItemGroup> T register(String name, T itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, XDsLibrary.id(name), itemGroup);
    }

    public static void load() {
    }
}