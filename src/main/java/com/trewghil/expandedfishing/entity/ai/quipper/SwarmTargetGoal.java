package com.trewghil.expandedfishing.entity.ai.quipper;

import com.trewghil.expandedfishing.entity.QuipperEntity;
import net.minecraft.entity.ai.goal.Goal;

public class SwarmTargetGoal extends Goal {

    protected final QuipperEntity mob;
    protected final double speed;
    private int moveDelay;

    public SwarmTargetGoal(QuipperEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        return this.mob.hasTarget();
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
            if(this.mob.hasTarget()) {
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
        return !this.mob.getNavigation().isIdle() && !this.mob.hasPassengers() && this.mob.hasTarget() && this.mob.getTarget().isTouchingWater();
    }
}
