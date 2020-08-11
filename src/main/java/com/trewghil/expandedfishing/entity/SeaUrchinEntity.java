package com.trewghil.expandedfishing.entity;

import com.trewghil.expandedfishing.item.ExpandedFishingItems;
import com.trewghil.expandedfishing.network.EntitySpawnPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class SeaUrchinEntity extends ThrownItemEntity implements IAnimatedEntity {

    private EntityAnimationManager manager = new EntityAnimationManager();
    private AnimationController controller = new EntityAnimationController(this, "moveController", 20, this::animationPredicate);
    private boolean collided = false;

    public SeaUrchinEntity(EntityType<? extends SeaUrchinEntity> entityType, World world) {
        super(entityType, world);

        registerAnimationControllers();
    }

    public SeaUrchinEntity(World world, LivingEntity owner) {
        super(ExpandedFishingEntities.SEA_URCHIN_ENTITY, owner, world);

        registerAnimationControllers();
    }

    public SeaUrchinEntity(World world, double x, double y, double z) {
        super(ExpandedFishingEntities.SEA_URCHIN_ENTITY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ExpandedFishingItems.SEA_URCHIN;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if(!collided) {
            super.onPlayerCollision(player);

            player.damage(DamageSource.CACTUS, 1.0F);

            this.world.sendEntityStatus(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.collided = true;

        Vec3d hit = blockHitResult.getPos();

        this.setVelocity(Vec3d.ZERO);
        this.setNoGravity(true);
        this.setPos(hit.getX(), hit.getY() - 0.1F, hit.getZ());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(!collided) {
            super.onEntityHit(entityHitResult);

            entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 4.0F);

            this.world.sendEntityStatus(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.createPacket(this);
    }

    private void registerAnimationControllers() {
        manager.addAnimationController(controller);
    }

    private <E extends SeaUrchinEntity> boolean animationPredicate(AnimationTestEvent<E> event) {
        controller.setAnimation(new AnimationBuilder().addAnimation("animation.model.urchin", true));
        return true;
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }
}
