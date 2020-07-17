package com.trewghil.expandedfishing.item;

import com.trewghil.expandedfishing.entity.SeaUrchinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SeaUrchinItem extends Item {

    public SeaUrchinItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.isSneaking()) {
            world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));

            if (!world.isClient) {
                SeaUrchinEntity seaUrchinEntity = new SeaUrchinEntity(world, user);
                seaUrchinEntity.setItem(itemStack);
                seaUrchinEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
                world.spawnEntity(seaUrchinEntity);
            }
            if (!user.abilities.creativeMode) {
                itemStack.decrement(1);
            }

            return TypedActionResult.method_29237(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }

}
