// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning;

import net.labymod.api.util.math.vector.FloatVector3;

public interface LightSource
{
    FloatVector3 getPosition();
    
    default void tick() {
    }
}
