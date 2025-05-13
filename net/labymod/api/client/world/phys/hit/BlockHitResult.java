// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.phys.hit;

import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

public interface BlockHitResult extends HitResult
{
    FloatVector3 getBlockPosition();
    
    Direction getBlockDirection();
    
    boolean isInside();
}
