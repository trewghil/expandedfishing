package com.trewghil.expandedfishing;

import com.trewghil.expandedfishing.enchantment.ExpandedFishingEnchantments;
import com.trewghil.expandedfishing.item.ExpandedFishingItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ExpandedFishing implements ModInitializer {

	public static final String MOD_ID = "expandedfishing";

	public static Identifier identify(String id) {
		return new Identifier(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		ExpandedFishingItems.init();
		ExpandedFishingEnchantments.init();
	}
}
