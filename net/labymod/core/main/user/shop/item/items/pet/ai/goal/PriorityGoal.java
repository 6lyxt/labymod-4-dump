// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import java.util.Set;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;

public class PriorityGoal extends Goal
{
    private final int priority;
    private final Goal delegate;
    private boolean running;
    
    public PriorityGoal(final int priority, final Goal delegate) {
        super(null);
        this.priority = priority;
        this.delegate = delegate;
    }
    
    @Override
    public boolean canUse() {
        return this.delegate.canUse();
    }
    
    @Override
    public boolean canContinueToUse() {
        return this.delegate.canContinueToUse();
    }
    
    public int adjustedTickDelay(final int value) {
        return this.delegate.adjustedTickDelay(value);
    }
    
    @Override
    public Set<GoalAction> getActions() {
        return this.delegate.getActions();
    }
    
    @Override
    public void setActions(final Set<GoalAction> actions) {
        this.delegate.setActions(actions);
    }
    
    @Override
    public void start() {
        if (!this.running) {
            this.running = true;
            this.delegate.start();
        }
    }
    
    @Override
    public void stop() {
        if (this.running) {
            this.running = false;
            this.delegate.stop();
        }
    }
    
    @Override
    public void tick() {
        this.delegate.tick();
    }
    
    @Override
    public PetBehavior behavior() {
        return this.delegate.behavior();
    }
    
    public Goal getGoal() {
        return this.delegate;
    }
    
    public boolean isRunning() {
        return this.running;
    }
    
    public boolean canBeReplacedBy(final PriorityGoal other) {
        return other.getPriority() < this.getPriority();
    }
    
    public int getPriority() {
        return this.priority;
    }
}
