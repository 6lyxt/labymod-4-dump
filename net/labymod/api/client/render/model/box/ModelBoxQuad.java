// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.box;

import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;

public interface ModelBoxQuad
{
    ModelBoxVertex[] getVertices();
    
    FloatVector3 getNormal();
    
    Direction getDirection();
    
    FloatVector3 getNormal(final FloatMatrix3 p0);
    
    boolean isVisible();
    
    void setVisible(final boolean p0);
    
    float getMinU();
    
    float getMinV();
    
    float getMaxU();
    
    float getMaxV();
}
