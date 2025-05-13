// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;

public interface Block
{
    ResourceLocation id();
    
    boolean isAir();
    
    BlockState defaultState();
}
