// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai;

import net.labymod.core.main.user.shop.item.items.pet.ai.goal.NoGoal;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.Goal;
import java.util.HashSet;
import java.util.HashMap;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.GoalAction;
import java.util.Map;
import java.util.Set;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.PriorityGoal;

public class Brain
{
    private static final PriorityGoal NO_GOAL;
    private final Set<PriorityGoal> goals;
    private final Map<GoalAction, PriorityGoal> lockedActions;
    private final Set<GoalAction> disabledActions;
    
    public Brain() {
        this.lockedActions = new HashMap<GoalAction, PriorityGoal>();
        this.disabledActions = new HashSet<GoalAction>();
        this.goals = new HashSet<PriorityGoal>();
    }
    
    public void addGoal(final int priority, final Goal goal) {
        this.goals.add(new PriorityGoal(priority, goal));
    }
    
    public void removeGoal(final Goal goal) {
        final Iterator<PriorityGoal> iterator = this.goals.iterator();
        PriorityGoal priorityGoal = null;
        while (iterator.hasNext()) {
            priorityGoal = iterator.next();
            if (priorityGoal.getGoal() == goal && priorityGoal.isRunning()) {
                priorityGoal.stop();
            }
        }
        this.goals.removeIf(priorityGoal -> priorityGoal.getGoal() == goal);
    }
    
    public void tick() {
        this.processGoals();
    }
    
    private void processGoals() {
        this.stopGoals();
        this.cleanUpLockedActions();
        this.startGoals();
        this.tickGoals();
    }
    
    private void cleanUpLockedActions() {
        this.lockedActions.entrySet().removeIf(entry -> !entry.getValue().isRunning());
    }
    
    private void startGoals() {
        for (final PriorityGoal goal : this.goals) {
            if (!this.canStartGoal(goal)) {
                continue;
            }
            for (final GoalAction action : goal.getActions()) {
                final PriorityGoal lockedActionGoal = this.lockedActions.getOrDefault(action, Brain.NO_GOAL);
                lockedActionGoal.stop();
                this.lockedActions.put(action, goal);
            }
            goal.start();
        }
    }
    
    private void stopGoals() {
        for (final PriorityGoal goal : this.goals) {
            if (!this.shouldStopGoal(goal)) {
                continue;
            }
            goal.stop();
        }
    }
    
    private boolean canStartGoal(@NotNull final PriorityGoal goal) {
        return !goal.isRunning() && !this.hasDisabledAction(goal) && this.isActionCompatible(goal) && goal.canUse();
    }
    
    private boolean shouldStopGoal(@NotNull final PriorityGoal goal) {
        return goal.isRunning() && (this.hasDisabledAction(goal) || !goal.canContinueToUse());
    }
    
    private void tickGoals() {
        for (final PriorityGoal goal : this.goals) {
            if (!goal.isRunning()) {
                continue;
            }
            goal.tick();
        }
    }
    
    private boolean hasDisabledAction(@NotNull final PriorityGoal goal) {
        for (final GoalAction action : goal.getActions()) {
            if (this.disabledActions.contains(action)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isActionCompatible(@NotNull final PriorityGoal goal) {
        for (final GoalAction action : goal.getActions()) {
            if (!this.lockedActions.getOrDefault(action, Brain.NO_GOAL).canBeReplacedBy(goal)) {
                return false;
            }
        }
        return true;
    }
    
    static {
        NO_GOAL = new PriorityGoal(Integer.MAX_VALUE, new NoGoal());
    }
}
