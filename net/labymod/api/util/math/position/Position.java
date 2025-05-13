// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.position;

import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.MathHelper;

public interface Position
{
    double getX();
    
    void setX(final double p0);
    
    double getY();
    
    void setY(final double p0);
    
    double getZ();
    
    void setZ(final double p0);
    
    default double distanceSquared(final Position other) {
        return MathHelper.distanceSquared(this.getX(), this.getY(), this.getZ(), other.getX(), other.getY(), other.getZ());
    }
    
    default double distanceSquared(final double otherX, final double otherY, final double otherZ) {
        return MathHelper.distanceSquared(this.getX(), this.getY(), this.getZ(), otherX, otherY, otherZ);
    }
    
    default FloatVector3 toFloatVector3() {
        return new FloatVector3((float)this.getX(), (float)this.getY(), (float)this.getZ());
    }
    
    default DoubleVector3 toDoubleVector3() {
        return new DoubleVector3(this.getX(), this.getY(), this.getZ());
    }
    
    default double lerpX(final Position previous, final float delta) {
        return MathHelper.lerp(this.getX(), previous.getX(), delta);
    }
    
    default double lerpY(final Position previous, final float delta) {
        return MathHelper.lerp(this.getY(), previous.getY(), delta);
    }
    
    default double lerpZ(final Position previous, final float delta) {
        return MathHelper.lerp(this.getZ(), previous.getZ(), delta);
    }
}
