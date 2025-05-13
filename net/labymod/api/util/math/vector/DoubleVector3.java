// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;

public class DoubleVector3
{
    private double x;
    private double y;
    private double z;
    
    public DoubleVector3() {
        this(0.0, 0.0, 0.0);
    }
    
    public DoubleVector3(final FloatVector3 vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public DoubleVector3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public DoubleVector3 add(final FloatVector3 v) {
        return this.add(v, this);
    }
    
    public DoubleVector3 add(final FloatVector3 v, final DoubleVector3 dest) {
        return this.add(v.getX(), v.getY(), v.getZ(), dest);
    }
    
    public DoubleVector3 add(final DoubleVector3 v) {
        return this.add(v, this);
    }
    
    public DoubleVector3 add(final DoubleVector3 v, final DoubleVector3 dest) {
        return this.add(v.getX(), v.getY(), v.getZ(), dest);
    }
    
    public DoubleVector3 add(final double x, final double y, final double z) {
        return this.add(x, y, z, this);
    }
    
    public DoubleVector3 add(final double x, final double y, final double z, final DoubleVector3 dest) {
        dest.set(this.getX() + x, this.getY() + y, this.getZ() + z);
        return dest;
    }
    
    public DoubleVector3 sub(final DoubleVector3 v) {
        return this.sub(v, this);
    }
    
    public DoubleVector3 sub(final DoubleVector3 v, final DoubleVector3 dest) {
        return this.sub(v.getX(), v.getY(), v.getZ(), dest);
    }
    
    public DoubleVector3 sub(final double x, final double y, final double z) {
        return this.sub(x, y, z, this);
    }
    
    public DoubleVector3 sub(final double x, final double y, final double z, final DoubleVector3 dest) {
        dest.set(this.getX() - x, this.getY() - y, this.getZ() - z);
        return dest;
    }
    
    public DoubleVector3 multiply(final double x, final double y, final double z) {
        return this.multiply(x, y, z, this);
    }
    
    public DoubleVector3 multiply(final double x, final double y, final double z, final DoubleVector3 dest) {
        dest.set(this.getX() * x, this.getY() * y, this.getZ() * z);
        return dest;
    }
    
    public DoubleVector3 multiply(final double scalar) {
        return this.multiply(scalar, this);
    }
    
    public DoubleVector3 multiply(final double scalar, final DoubleVector3 dest) {
        return this.multiply(scalar, scalar, scalar, dest);
    }
    
    public void set(final Position position) {
        this.set(position.getX(), position.getY(), position.getZ());
    }
    
    public void set(final double x, final double y, final double z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    
    public void set(final DoubleVector3 other) {
        this.set(other.getX(), other.getY(), other.getZ());
    }
    
    public DoubleVector3 normalize() {
        return this.normalize(this);
    }
    
    public DoubleVector3 normalize(final DoubleVector3 dest) {
        final double x = this.getX();
        final double y = this.getY();
        final double z = this.getZ();
        final double sqrt = Math.sqrt(x * x + y * y + z * z);
        if (sqrt < 1.0E-4) {
            dest.set(0.0, 0.0, 0.0);
        }
        else {
            dest.set(x / sqrt, y / sqrt, z / sqrt);
        }
        return this;
    }
    
    public double lerpX(final DoubleVector3 other, final float delta) {
        return MathHelper.lerp(this.getX(), other.getX(), delta);
    }
    
    public double lerpY(final DoubleVector3 other, final float delta) {
        return MathHelper.lerp(this.getY(), other.getY(), delta);
    }
    
    public double lerpZ(final DoubleVector3 other, final float delta) {
        return MathHelper.lerp(this.getZ(), other.getZ(), delta);
    }
    
    public double distanceSquared(final DoubleVector3 other) {
        return this.square(this.getX() - other.getX()) + this.square(this.getY() - other.getY()) + this.square(this.getZ() - other.getZ());
    }
    
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }
    
    public double lengthSquared() {
        return this.square(this.getX()) + this.square(this.getY()) + this.square(this.getZ());
    }
    
    public double horizontalDistanceSquared() {
        return this.square(this.getX()) + this.square(this.getZ());
    }
    
    public float distance(final DoubleVector3 vector) {
        return (float)Math.sqrt(this.distanceSquared(vector));
    }
    
    public DoubleVector3 copy() {
        return new DoubleVector3(this.getX(), this.getY(), this.getZ());
    }
    
    public DoubleVector3 transform(final Quaternion quaternion) {
        return this.transform(quaternion, this);
    }
    
    public DoubleVector3 transform(final Quaternion quaternion, final DoubleVector3 dest) {
        final Quaternion quaternion2 = new Quaternion(quaternion);
        quaternion2.multiply(new Quaternion((float)this.getX(), (float)this.getY(), (float)this.getZ(), 0.0f));
        final Quaternion quaternion3 = new Quaternion(quaternion);
        quaternion3.conj();
        quaternion2.multiply(quaternion3);
        dest.set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ());
        return dest;
    }
    
    @Override
    public String toString() {
        return "DoubleVector3[x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ();
    }
    
    private double square(final double v) {
        return v * v;
    }
}
