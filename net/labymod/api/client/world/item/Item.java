// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;

public interface Item
{
    ResourceLocation getIdentifier();
    
    int getMaximumStackSize();
    
    int getMaximumDamage();
    
    boolean isAir();
}
