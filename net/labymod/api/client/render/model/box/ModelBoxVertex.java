// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.box;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector3;

public interface ModelBoxVertex
{
    FloatVector3 getPosition();
    
    FloatVector2 getUV();
    
    ModelBoxVertex remap(final float p0, final float p1);
    
    ModelBoxVertex remap(final FloatVector2 p0);
}
