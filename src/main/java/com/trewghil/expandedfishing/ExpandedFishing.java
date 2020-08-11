package com.trewghil.expandedfishing;

import com.trewghil.expandedfishing.enchantment.ExpandedFishingEnchantments;
import com.trewghil.expandedfishing.entity.ExpandedFishingEntities;
import com.trewghil.expandedfishing.item.ExpandedFishingItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class ExpandedFishing implements ModInitializer {

	public static final String MOD_ID = "expandedfishing";

	private static final Identifier BURIED_TREASURE_LOOT_TABLE_ID = new Identifier("minecraft", "chests/buried_treasure");

	public static Identifier identify(String id) {
		return new Identifier(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		ExpandedFishingItems.init();
		ExpandedFishingEntities.init();
		ExpandedFishingEnchantments.init();

		//LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
		//	if (BURIED_TREASURE_LOOT_TABLE_ID.equals(id)) {
		//		FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder().rolls(ConstantLootTableRange.create(1)).withEntry(ItemEntry.builder(ExpandedFishingItems.QUIPPER));
		//	}
		//});
	}
}
