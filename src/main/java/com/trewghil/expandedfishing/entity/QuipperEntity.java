package com.trewghil.expandedfishing.entity;

import com.trewghil.expandedfishing.network.EntitySpawnPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;

import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class QuipperEntity extends SchoolingFishEntity {

    private QuipperEntity swarmLeader;
    private List<QuipperEntity> swarm = new ArrayList<>();
    public boolean dashed = false;

    public QuipperEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new AttackGoal(this, 1.6F));
        this.goalSelector.add(2, new SwarmTargetGoal(this, 6.0F));
        this.goalSelector.add(4, new SwimToRandomPlaceGoal(this));
        this.goalSelector.add(5, new FollowLeaderGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class));
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            if(this.hasTarget()) {
                this.updateVelocity(0.06F, movementInput);
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
            System.out.print(goal.getGoal().toString() + ", ");
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


    static class SwarmTargetGoal extends Goal {

        protected final QuipperEntity mob;
        protected final double speed;
        private int moveDelay;

        public SwarmTargetGoal(QuipperEntity mob, double speed) {
            this.mob = mob;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            if(this.mob.getTarget() == null) {
                return false;
            } else return this.mob.getTarget().isAlive();
        }

        @Override
        public void start() {
            this.moveDelay = 0;
        }

        @Override
        public void tick() {
            super.tick();

            if (--this.moveDelay <= 0) {
                this.moveDelay = 10;
                if(this.mob.hasTarget() && !this.mob.dashed) {
                    this.mob.getLookControl().lookAt(this.mob.getTarget().getX(), this.mob.getTarget().getEyeY(), this.mob.getTarget().getZ());

                    this.mob.getNavigation().startMovingTo(this.mob.getTarget(), this.speed);
                }
            }
        }

        @Override
        public void stop() {
            this.mob.getNavigation().stop();
            super.stop();
        }

        @Override
        public boolean shouldContinue() {
            return !this.mob.getNavigation().isIdle() && !this.mob.hasPassengers() && this.mob.hasTarget() && !this.mob.dashed;
        }
    }

    static class AttackGoal extends MeleeAttackGoal {

        private double dashVelocity;
        private int cooldown;
        private boolean jumped;
        private boolean terminate;

        public AttackGoal(QuipperEntity quipperEntity, double dashVelocity) {
            super(quipperEntity, 1.0D, true);

            this.dashVelocity = dashVelocity;
        }

        @Override
        public void start() {
            super.start();

            this.terminate = false;
            this.jumped = false;

            cooldown = 30; //ticks

            Vec3d vec3d = this.mob.getVelocity();
            Vec3d direction = new Vec3d(this.mob.getTarget().getX() - this.mob.getX(), this.mob.getTarget().getY() - this.mob.getY(), this.mob.getTarget().getZ() - this.mob.getZ());
            if (direction.lengthSquared() > 1.0E-7D) {
                direction = direction.normalize().multiply(0.5D).add(vec3d.multiply(0.3D));
            }

            this.mob.setVelocity(direction.multiply(dashVelocity).x, direction.multiply(dashVelocity).y, direction.multiply(dashVelocity).z);
            jumped = true;
            ((QuipperEntity) this.mob).dashed = true;
        }

        @Override
        public void tick() {
            //super.tick();

            if(this.mob.distanceTo(this.mob.getTarget()) <= 1.2f) {
                this.mob.tryAttack(this.mob.getTarget());
            }

            if(this.jumped && cooldown > 0) {
                cooldown--;
            }
            if(cooldown <= 0) {
                this.jumped = false;
                ((QuipperEntity) this.mob).dashed = false;
                this.terminate = true;
            }
        }

        public boolean canStart() {
            return super.canStart() && !this.mob.hasPassengers() && this.mob.distanceTo(this.mob.getTarget()) <= 3f;
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && ((QuipperEntity) this.mob).hasTarget() && !this.terminate; //&& this.mob.distanceTo(this.mob.getTarget()) < 0.25f
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(4.0F + entity.getWidth());
        }
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
            return this.fish.hasSelfControl() &&  (this.fish.getTarget() == null || !this.fish.getTarget().isAlive()) && super.canStart();
        }
    }
}

