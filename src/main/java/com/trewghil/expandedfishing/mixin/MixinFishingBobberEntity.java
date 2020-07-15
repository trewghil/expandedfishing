package com.trewghil.expandedfishing.mixin;

import com.trewghil.expandedfishing.loot.ExpandedFishingLootTables;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;

import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;


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

    @Inject(method = "use(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContext;)Ljava/util/List;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addBountifulCatch(ItemStack usedItem, CallbackInfoReturnable<Integer> cir, PlayerEntity playerEntity, int i, LootContext.Builder builder, LootTable lootTable, List list) {

        ItemStack heldItem = playerEntity.getMainHandStack();
        Random r = new Random();

        heldItem.getEnchantments().forEach(enchantment ->  {
            if(((CompoundTag)enchantment).get("id").asString().equals("expandedfishing:bountiful")) {

                int level = Integer.parseInt(((CompoundTag) enchantment).get("lvl").asString().substring(0, 1));

                if(level <= 3) {
                    if(r.nextInt(100) < (level * 20)) {
                        list.add(lootTable.generateLoot(builder.build(LootContextTypes.FISHING)).remove(0));
                    }
                } else if(level < 6) {
                    if(r.nextInt(100) < (level * 15)) {
                        list.add(lootTable.generateLoot(builder.build(LootContextTypes.FISHING)).remove(0));
                        list.add(lootTable.generateLoot(builder.build(LootContextTypes.FISHING)).remove(0));
                    }
                }
            }
        });

    }

}
