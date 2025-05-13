// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GameMathMapper
{
    FloatMatrix4 fromMatrix4f(final Object p0);
    
     <T> T toMatrix4f(final FloatMatrix4 p0);
    
    FloatMatrix3 fromMatrix3f(final Object p0);
    
     <T> T toMatrix3f(final FloatMatrix3 p0);
    
    void applyFloatMatrix4(final FloatMatrix4 p0, final Object p1);
    
    void applyMatrix4f(final Object p0, final FloatMatrix4 p1);
    
    void applyFloatMatrix3(final FloatMatrix3 p0, final Object p1);
    
    void applyMatrix3f(final Object p0, final FloatMatrix3 p1);
    
    Quaternion fromQuaternion(final Object p0);
    
     <T> T toQuaternion(final Quaternion p0);
    
    AxisAlignedBoundingBox fromAABB(final Object p0);
    
     <T> T toAABB(final AxisAlignedBoundingBox p0);
}
