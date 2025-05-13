// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GameTickProvider
{
    float get();
    
    float getPaused();
    
    int tickCount();
    
    int pausedTickCount();
}
