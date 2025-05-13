// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.phys.hit;

import net.labymod.api.util.math.vector.FloatVector3;

public interface HitResult
{
    FloatVector3 location();
    
    HitType type();
    
    public enum HitType
    {
        MISS, 
        BLOCK, 
        ENTITY;
    }
}
