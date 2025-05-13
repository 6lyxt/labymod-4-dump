// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import net.labymod.api.client.entity.LivingEntity;

public interface ClientPlayer extends Player
{
    boolean isAbilitiesFlying();
    
    float getAbilitiesWalkingSpeed();
    
    boolean isHandActive();
    
    boolean isUsingBow();
    
    void swingArm(final LivingEntity.Hand p0);
    
    void swingArm(final LivingEntity.Hand p0, final boolean p1);
    
    Inventory inventory();
    
    void setDistanceWalked(final float p0);
    
    float getForwardMovingSpeed();
    
    float getStrafeMovingSpeed();
}
