// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import java.util.Objects;

public final class GoalAction
{
    public static final GoalAction MOVE;
    public static final GoalAction LOOK;
    private final String name;
    
    public GoalAction(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final GoalAction that = (GoalAction)o;
        return Objects.equals(this.name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }
    
    static {
        MOVE = new GoalAction("move");
        LOOK = new GoalAction("look");
    }
}
