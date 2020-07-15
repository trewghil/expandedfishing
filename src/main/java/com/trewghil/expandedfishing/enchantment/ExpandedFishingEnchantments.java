package com.trewghil.expandedfishing.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import static com.trewghil.expandedfishing.ExpandedFishing.identify;

public class ExpandedFishingEnchantments {

    public ExpandedFishingEnchantments() {

    }

    private static Enchantment BOUNTIFUL = Registry.register(
            Registry.ENCHANTMENT,
            identify("bountiful"),
            new BountifulEnchantment()
    );

    public static void init() {

    }

}
