// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

public class FloatVector4
{
    public static final FloatVector4 ZERO;
    private float x;
    private float y;
    private float z;
    private float w;
    
    public FloatVector4() {
    }
    
    public FloatVector4(final FloatVector4 vector) {
        this(vector.x, vector.y, vector.z, vector.w);
    }
    
    public FloatVector4(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public void transform(final FloatMatrix4 matrix) {
        final float x = this.x;
        final float y = this.y;
        final float z = this.z;
        final float w = this.w;
        this.x = matrix.getM00() * x + matrix.getM01() * y + matrix.getM02() * z + matrix.getM03() * w;
        this.y = matrix.getM10() * x + matrix.getM11() * y + matrix.getM12() * z + matrix.getM13() * w;
        this.z = matrix.getM20() * x + matrix.getM21() * y + matrix.getM22() * z + matrix.getM23() * w;
        this.w = matrix.getM30() * x + matrix.getM31() * y + matrix.getM32() * z + matrix.getM33() * w;
    }
    
    public void transform(final FloatVector3 other, final FloatMatrix4 matrix, final float scale) {
        final float x = other.getX() * scale;
        final float y = other.getY() * scale;
        final float z = other.getZ() * scale;
        this.x = matrix.getM00() * x + matrix.getM01() * y + matrix.getM02() * z + matrix.getM03();
        this.y = matrix.getM10() * x + matrix.getM11() * y + matrix.getM12() * z + matrix.getM13();
        this.z = matrix.getM20() * x + matrix.getM21() * y + matrix.getM22() * z + matrix.getM23();
        this.w = matrix.getM30() * x + matrix.getM31() * y + matrix.getM32() * z + matrix.getM33();
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getZ() {
        return this.z;
    }
    
    public float getW() {
        return this.w;
    }
    
    public void set(final float x, final float y, final float z, final float w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }
    
    public void set(final FloatVector4 other) {
        this.set(other.getX(), other.getY(), other.getZ(), other.getW());
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public void setZ(final float z) {
        this.z = z;
    }
    
    public void setW(final float w) {
        this.w = w;
    }
    
    static {
        ZERO = new FloatVector4(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
