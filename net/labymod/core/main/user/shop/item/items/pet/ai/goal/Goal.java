// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import java.util.Collection;
import net.labymod.api.util.math.MathHelper;
import java.util.HashSet;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import java.util.Set;
import java.util.Random;

public abstract class Goal
{
    protected static final Random RANDOM;
    private final Set<GoalAction> actions;
    private final PetBehavior behavior;
    
    protected Goal(final PetBehavior behavior) {
        this.actions = new HashSet<GoalAction>();
        this.behavior = behavior;
    }
    
    public void start() {
    }
    
    public void stop() {
    }
    
    public void tick() {
    }
    
    public abstract boolean canUse();
    
    public boolean canContinueToUse() {
        return this.canUse();
    }
    
    public PetBehavior behavior() {
        return this.behavior;
    }
    
    protected int adjustedTickDelay(final int value) {
        return MathHelper.positiveCeilDiv(value, 2);
    }
    
    public Set<GoalAction> getActions() {
        return this.actions;
    }
    
    public void setActions(final GoalAction... actions) {
        this.setActions(Set.of(actions));
    }
    
    public void setActions(final Set<GoalAction> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
    }
    
    static {
        RANDOM = new Random();
    }
}
