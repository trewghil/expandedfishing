package com.trewghil.expandedfishing.entity;

import com.trewghil.expandedfishing.entity.ai.quipper.QuipperAttackGoal;
import com.trewghil.expandedfishing.entity.ai.quipper.SwarmTargetGoal;
import com.trewghil.expandedfishing.network.EntitySpawnPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;

import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import java.util.ArrayList;
import java.util.List;

public class QuipperEntity extends SchoolingFishEntity implements IAnimatedEntity {

    private QuipperEntity swarmLeader;
    private List<QuipperEntity> swarm = new ArrayList<>();
    private EntityAnimationManager manager = new EntityAnimationManager();
    private AnimationController controller = new EntityAnimationController(this, "moveController", 20, this::animationPredicate);

    public QuipperEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);

        registerAnimationControllers();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new QuipperAttackGoal(this, 1.6F));
        this.goalSelector.add(2, new SwarmTargetGoal(this, 6.0F));
        this.goalSelector.add(4, new SwimToRandomPlaceGoal(this));
        this.goalSelector.add(5, new FollowLeaderGoal(this));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class));
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            if(this.hasTarget()) {
                this.updateVelocity(0.05F, movementInput);
            } else {
                this.updateVelocity(0.01F, movementInput);
            }

            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));

            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.goalSelector.getRunningGoals().forEach(goal -> {
            System.out.println(goal.getGoal().toString() + ", ");
        });
        System.out.println("\n");
    }

    @Override
    public SchoolingFishEntity joinGroupOf(SchoolingFishEntity groupLeader) {
        if(groupLeader instanceof QuipperEntity) {
            this.swarmLeader = (QuipperEntity) groupLeader;

            ((QuipperEntity) groupLeader).addToSwarm(this);

            return super.joinGroupOf(groupLeader);
        } else {
            return groupLeader;
        }
    }

    public void alertLeaderOfTarget() {
        if(this.swarmLeader != null) {
            this.swarmLeader.setTarget(this.getTarget());

            this.swarmLeader.targetSelector.getRunningGoals().filter(goal -> goal.getGoal() instanceof FollowTargetGoal).forEach(goal -> {
                ((FollowTargetGoal) goal.getGoal()).setTargetEntity(this.getTarget());
            });

            this.swarmLeader.alertSwarm();
        }
    }

    public void alertSwarm() {
        if(this.hasOtherFishInGroup()) {
            for(QuipperEntity e : this.swarm) {
                e.setTarget(this.getTarget());

                e.targetSelector.getRunningGoals().filter(goal -> goal.getGoal() instanceof FollowTargetGoal).forEach(goal -> {
                    ((FollowTargetGoal) goal.getGoal()).setTargetEntity(this.getTarget());
                });
            }
        }
    }

    public void addToSwarm(QuipperEntity e) {
        this.swarm.add(e);
    }

    public boolean hasTarget() {
        if(this.getTarget() == null) {
            return false;
        } else return this.getTarget().isAlive();
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    @Override
    protected ItemStack getFishBucketItem() {
        return null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.createPacket(this);
    }

    private <E extends QuipperEntity> boolean animationPredicate(AnimationTestEvent<E> event) {
        if(event.isWalking()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.quipper.swim").addAnimation("animation.quipper.swim", true));
            return true;
        } else {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.quipper.idle", true));
            return true;
        }
    }

    private void registerAnimationControllers() {
        manager.addAnimationController(controller);
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    static class FollowTargetGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.FollowTargetGoal<T> {
        public FollowTargetGoal(QuipperEntity quipperEntity, Class<T> targetEntityClass) {
            super(quipperEntity, targetEntityClass, true);
        }

        public boolean canStart() {
            return super.canStart();
        }

        @Override
        protected void findClosestTarget() {
            LivingEntity target;

            if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
                target = this.mob.world.getClosestEntityIncludingUngeneratedChunks(this.targetClass, this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.getSearchBox(this.getFollowRange()));
            } else {
                target = this.mob.world.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            }

            if(target != null && target.isTouchingWater()) {
                this.targetEntity = target;
                ((QuipperEntity) this.mob).alertLeaderOfTarget();
            }
        }
    }

    static class FollowLeaderGoal extends FollowGroupLeaderGoal {
        private QuipperEntity fish;

        public FollowLeaderGoal(QuipperEntity fish) {
            super(fish);
            this.fish = fish;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && !this.fish.hasTarget();
        }
    }

    static class SwimToRandomPlaceGoal extends SwimAroundGoal {
        private final QuipperEntity fish;

        public SwimToRandomPlaceGoal(QuipperEntity fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        public boolean canStart() {
            return this.fish.hasSelfControl() &&  !this.fish.hasTarget() && super.canStart();
        }
    }
}

