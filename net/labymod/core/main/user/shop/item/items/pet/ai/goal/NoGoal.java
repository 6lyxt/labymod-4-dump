// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;

public class NoGoal extends Goal
{
    public NoGoal() {
        super(null);
    }
    
    @Override
    public boolean canUse() {
        return false;
    }
}
