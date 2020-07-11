package com.trewghil.expandedfishing.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class MixinFishingBobberEntity {

    @Inject(method = "use", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContext;)Ljava/util/List;"))
    private void nightTimeFish(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        System.out.println("Loot table generated at ");

        if(!((FishingBobberEntity) (Object) this).world.isDay()) {
            System.out.print("NIGHT");
        } else {
            System.out.print("DAY");
        }
    }

}
