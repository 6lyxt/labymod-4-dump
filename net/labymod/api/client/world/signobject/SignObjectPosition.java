// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.block.BlockPosition;

public interface SignObjectPosition
{
    BlockPosition position();
    
    SignBlockEntity.SignSide side();
    
    float rotationYaw();
    
    FloatVector3 calculatePosition(final float p0, final float p1, final float p2);
}
