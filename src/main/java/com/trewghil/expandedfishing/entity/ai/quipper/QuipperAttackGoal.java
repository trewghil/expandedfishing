package com.trewghil.expandedfishing.entity.ai.quipper;

import com.trewghil.expandedfishing.entity.QuipperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.math.Vec3d;

public class QuipperAttackGoal extends MeleeAttackGoal {

    private double dashVelocity;
    private int cooldown;
    private boolean jumped;

    public QuipperAttackGoal(QuipperEntity quipperEntity, double dashVelocity) {
        super(quipperEntity, 1.0D, true);

        this.dashVelocity = dashVelocity;
    }

    @Override
    public void start() {
        this.jumped = false;

        cooldown = 16; //ticks

        if(((QuipperEntity) this.mob).hasTarget()) {
            if(this.mob.getTarget().isTouchingWater()) {
                Vec3d direction = new Vec3d(this.mob.getTarget().getX() - this.mob.getX(), (this.mob.getTarget().getEyeY() - 0.75F) - this.mob.getY(), this.mob.getTarget().getZ() - this.mob.getZ());
                if (direction.lengthSquared() > 1.0E-7D) {
                    direction = direction.normalize().multiply(0.5D);
                }

                this.mob.setVelocity(direction.multiply(dashVelocity).x, direction.multiply(dashVelocity).y, direction.multiply(dashVelocity).z);
                jumped = true;
            }
        }
    }

    @Override
    public void tick() {
        if(((QuipperEntity) this.mob).hasTarget()) {
            this.mob.getLookControl().lookAt(this.mob.getTarget().getX(), this.mob.getTarget().getEyeY(), this.mob.getTarget().getZ());
        }

        if(this.mob.distanceTo(this.mob.getTarget()) <= 1.0f) {
            this.mob.tryAttack(this.mob.getTarget());
        }

        if(this.jumped && cooldown > 0) {
            cooldown--;
        }
        if(cooldown <= 0) {
            this.jumped = false;
        }
    }

    public boolean canStart() {
        return super.canStart() && !this.mob.hasPassengers() && this.mob.distanceTo(this.mob.getTarget()) <= 4.0f;
    }

    public boolean shouldContinue() {
        return super.shouldContinue() && ((QuipperEntity) this.mob).hasTarget() && this.mob.distanceTo(this.mob.getTarget()) <= 1.5f;
    }

    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return (double)(4.0F + entity.getWidth());
    }
}
