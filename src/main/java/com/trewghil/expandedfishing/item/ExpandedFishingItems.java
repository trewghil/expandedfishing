package com.trewghil.expandedfishing.item;

import com.trewghil.expandedfishing.ExpandedFishing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ExpandedFishingItems {

    public static final Item SNAPPER = new Item(new Item.Settings().group(ItemGroup.FOOD));

    public ExpandedFishingItems() {

    }

    public static void init() {
        Registry.register(Registry.ITEM, ExpandedFishing.identify("snapper"), SNAPPER);
    }

}
