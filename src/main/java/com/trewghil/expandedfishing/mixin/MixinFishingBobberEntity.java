package com.trewghil.expandedfishing.mixin;

import com.trewghil.expandedfishing.loot.ExpandedFishingLootTables;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(FishingBobberEntity.class)
public class MixinFishingBobberEntity {

    @ModifyVariable(method = "use(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/loot/LootManager;getTable(Lnet/minecraft/util/Identifier;)Lnet/minecraft/loot/LootTable;"))
    private LootTable nightTimeFish(LootTable lootTable) {

        FishingBobberEntity ent = ((FishingBobberEntity) (Object) this);

        if(!ent.world.isDay()) {
            lootTable = ent.world.getServer().getLootManager().getTable(ExpandedFishingLootTables.NIGHT_FISHING);
        } else {
            lootTable = ent.world.getServer().getLootManager().getTable(LootTables.FISHING_GAMEPLAY);
        }

        return lootTable;
    }

}
