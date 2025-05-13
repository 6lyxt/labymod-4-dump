// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

import net.labymod.api.util.math.MathHelper;

public class IntVector3
{
    public static final IntVector3 ZERO;
    private int x;
    private int y;
    private int z;
    
    public IntVector3() {
        this(0, 0, 0);
    }
    
    public IntVector3(final FloatVector3 other) {
        this(MathHelper.floor(other.getX()), MathHelper.floor(other.getY()), MathHelper.floor(other.getZ()));
    }
    
    public IntVector3(final DoubleVector3 other) {
        this(MathHelper.floor(other.getX()), MathHelper.floor(other.getY()), MathHelper.floor(other.getZ()));
    }
    
    public IntVector3(final IntVector3 other) {
        this(other.getX(), other.getY(), other.getZ());
    }
    
    public IntVector3(final float x, final float y, final float z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
    
    public IntVector3(final double x, final double y, final double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
    
    public IntVector3(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public void setZ(final int z) {
        this.z = z;
    }
    
    public void set(final int x, final int y, final int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    
    public void add(final int x, final int y, final int z) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
        this.setZ(this.getZ() + z);
    }
    
    public void sub(final int x, final int y, final int z) {
        this.setX(this.getX() - x);
        this.setY(this.getY() - y);
        this.setZ(this.getZ() - z);
    }
    
    public void mul(final int scalar) {
        this.mul(scalar, scalar, scalar);
    }
    
    public void mul(final int x, final int y, final int z) {
        this.setX(this.getX() * x);
        this.setY(this.getY() * y);
        this.setZ(this.getZ() * z);
    }
    
    public void div(final int scalar) {
        this.div(scalar, scalar, scalar);
    }
    
    public void div(final int x, final int y, final int z) {
        this.setX(this.getX() / x);
        this.setY(this.getY() / y);
        this.setZ(this.getZ() / z);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final IntVector3 that = (IntVector3)o;
        return this.x == that.x && this.y == that.y && this.z == that.z;
    }
    
    @Override
    public int hashCode() {
        int result = this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }
    
    @Override
    public String toString() {
        return "IntVector3[x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ();
    }
    
    static {
        ZERO = new IntVector3();
    }
}
