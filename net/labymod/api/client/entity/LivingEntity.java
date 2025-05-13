// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Collection;
import net.labymod.api.client.world.item.ItemStack;

public interface LivingEntity extends Entity
{
    float getHealth();
    
    float getMaximalHealth();
    
    float getAbsorptionHealth();
    
    int getItemUseDurationTicks();
    
    Hand getUsedItemHand();
    
    default boolean isMainHandRight() {
        return true;
    }
    
    ItemStack getMainHandItemStack();
    
    ItemStack getOffHandItemStack();
    
    ItemStack getRightHandItemStack();
    
    ItemStack getLeftHandItemStack();
    
    ItemStack getEquipmentItemStack(final EquipmentSpot p0);
    
    float getBodyRotationY();
    
    float getPreviousBodyRotationY();
    
    void setBodyRotationY(final float p0);
    
    void setPreviousBodyRotationY(final float p0);
    
    float getHeadRotationY();
    
    float getPreviousHeadRotationY();
    
    void setHeadRotationY(final float p0);
    
    void setPreviousHeadRotationY(final float p0);
    
    default boolean isWearingElytra() {
        return false;
    }
    
    boolean isSleeping();
    
    int getHurtTime();
    
    int getDeathTime();
    
    boolean isHostile();
    
    Collection<PotionEffect> getActivePotionEffects();
    
    public enum Hand
    {
        MAIN_HAND, 
        OFF_HAND;
    }
    
    public enum HandSide
    {
        LEFT, 
        RIGHT;
    }
    
    public enum EquipmentSpot
    {
        HEAD, 
        CHEST, 
        LEGS, 
        FEET;
    }
}
