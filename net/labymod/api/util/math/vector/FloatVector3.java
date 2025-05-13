// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

import org.jetbrains.annotations.NotNull;
import java.nio.FloatBuffer;
import java.util.function.Function;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;

public class FloatVector3
{
    public static final FloatVector3 XN;
    public static final FloatVector3 XP;
    public static final FloatVector3 YN;
    public static final FloatVector3 YP;
    public static final FloatVector3 ZN;
    public static final FloatVector3 ZP;
    public static final FloatVector3 ZERO;
    public static final FloatVector3 ONE;
    private static final Quaternion ROTATION_QUATERNION;
    private static final Quaternion ROTATION_DEGREES_QUATERNION;
    protected float x;
    protected float y;
    protected float z;
    
    public FloatVector3() {
        this(0.0f, 0.0f, 0.0f);
    }
    
    public FloatVector3(final FloatVector3 vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public FloatVector3(final float value) {
        this(value, value, value);
    }
    
    public FloatVector3(final DoubleVector3 vector) {
        this((float)vector.getX(), (float)vector.getY(), (float)vector.getZ());
    }
    
    public FloatVector3(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public float getZ() {
        return this.z;
    }
    
    public void setZ(final float z) {
        this.z = z;
    }
    
    public void set(final float x, final float y, final float z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    
    public void set(final FloatVector3 vector) {
        this.set(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public void transform(final FloatMatrix3 matrix) {
        final float x = this.getX();
        final float y = this.getY();
        final float z = this.getZ();
        this.setX(matrix.getM00() * x + matrix.getM01() * y + matrix.getM02() * z);
        this.setY(matrix.getM10() * x + matrix.getM11() * y + matrix.getM12() * z);
        this.setZ(matrix.getM20() * x + matrix.getM21() * y + matrix.getM22() * z);
    }
    
    public void transform(final FloatVector3 other, final FloatMatrix3 matrix) {
        final float x = other.getX();
        final float y = other.getY();
        final float z = other.getZ();
        this.setX(matrix.getM00() * x + matrix.getM01() * y + matrix.getM02() * z);
        this.setY(matrix.getM10() * x + matrix.getM11() * y + matrix.getM12() * z);
        this.setZ(matrix.getM20() * x + matrix.getM21() * y + matrix.getM22() * z);
    }
    
    public void transform(final Quaternion quaternion) {
        final Quaternion quaternion2 = new Quaternion(quaternion);
        quaternion2.multiply(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0f));
        final Quaternion quaternion3 = new Quaternion(quaternion);
        quaternion3.conj();
        quaternion2.multiply(quaternion3);
        this.set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ());
    }
    
    public FloatVector3 subReverse(final FloatVector3 vector) {
        this.set(vector.getX() - this.getX(), vector.getY() - this.getY(), vector.getZ() - this.getZ());
        return this;
    }
    
    public FloatVector3 add(final FloatVector3 vector) {
        return this.add(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public FloatVector3 add(final float x, final float y, final float z) {
        this.set(this.getX() + x, this.getY() + y, this.getZ() + z);
        return this;
    }
    
    public FloatVector3 sub(final FloatVector3 vector) {
        return this.sub(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public FloatVector3 sub(final float x, final float y, final float z) {
        return this.add(-x, -y, -z);
    }
    
    public FloatVector3 multiply(final float x, final float y, final float z) {
        this.set(this.getX() * x, this.getY() * y, this.getZ() * z);
        return this;
    }
    
    public FloatVector3 multiply(final FloatMatrix4 matrix) {
        final float x = this.x;
        final float y = this.y;
        final float z = this.z;
        this.x = MathHelper.fma(matrix.getM00(), x, MathHelper.fma(matrix.getM10(), y, MathHelper.fma(matrix.getM20(), z, matrix.getM30())));
        this.y = MathHelper.fma(matrix.getM01(), x, MathHelper.fma(matrix.getM11(), y, MathHelper.fma(matrix.getM21(), z, matrix.getM31())));
        this.z = MathHelper.fma(matrix.getM02(), x, MathHelper.fma(matrix.getM12(), y, MathHelper.fma(matrix.getM22(), z, matrix.getM32())));
        return this;
    }
    
    public FloatVector3 multiply(final float scalar) {
        return this.multiply(scalar, scalar, scalar);
    }
    
    public FloatVector3 divide(final float scalar) {
        return this.divide(scalar, scalar, scalar);
    }
    
    public FloatVector3 divide(final float x, final float y, final float z) {
        this.set(this.getX() / x, this.getY() / y, this.getZ() / z);
        return this;
    }
    
    public FloatVector3 map(final Function<Float, Float> mapper) {
        this.set(mapper.apply(this.getX()), mapper.apply(this.getY()), mapper.apply(this.getZ()));
        return this;
    }
    
    public FloatVector3 cross(final FloatVector3 vector) {
        final float x = this.getX();
        final float y = this.getY();
        final float z = this.getZ();
        final float vecX = vector.getX();
        final float vecY = vector.getY();
        final float vecZ = vector.getZ();
        this.set(y * vecZ - z * vecY, z * vecX - x * vecZ, x * vecY - y * vecX);
        return this;
    }
    
    public FloatVector3 normalize() {
        final float x = this.getX();
        final float y = this.getY();
        final float z = this.getZ();
        final double sqrt = Math.sqrt(x * x + y * y + z * z);
        if (sqrt < 1.0E-4) {
            this.set(0.0f, 0.0f, 0.0f);
        }
        else {
            this.set(x / (float)sqrt, y / (float)sqrt, z / (float)sqrt);
        }
        return this;
    }
    
    public FloatVector3 copy() {
        return new FloatVector3(this.getX(), this.getY(), this.getZ());
    }
    
    public float distance(final FloatVector3 vector) {
        return (float)Math.sqrt(this.distanceSquared(vector));
    }
    
    public float distance(final IntVector3 vector) {
        return (float)Math.sqrt(this.distanceSquared(vector));
    }
    
    public float distanceSquared(final FloatVector3 vector) {
        return this.square(this.getX() - vector.getX()) + this.square(this.getY() - vector.getY()) + this.square(this.getZ() - vector.getZ());
    }
    
    public float distanceSquared(final IntVector3 vector) {
        return this.square(this.getX() - vector.getX()) + this.square(this.getY() - vector.getY()) + this.square(this.getZ() - vector.getZ());
    }
    
    private float square(final float number) {
        return number * number;
    }
    
    public Quaternion rotation(final float rotationAngle) {
        return this.rotation(rotationAngle, true);
    }
    
    public Quaternion rotation(final float rotationAngle, final boolean newObject) {
        if (newObject) {
            return new Quaternion(this, rotationAngle, false);
        }
        FloatVector3.ROTATION_QUATERNION.setRotation(this, rotationAngle, false);
        return FloatVector3.ROTATION_QUATERNION;
    }
    
    public Quaternion rotationDegrees(final float rotationAngle) {
        return this.rotationDegrees(rotationAngle, true);
    }
    
    public Quaternion rotationDegrees(final float rotationAngle, final boolean newObject) {
        if (newObject) {
            return new Quaternion(this, rotationAngle, true);
        }
        FloatVector3.ROTATION_DEGREES_QUATERNION.setRotation(this, rotationAngle, true);
        return FloatVector3.ROTATION_DEGREES_QUATERNION;
    }
    
    public void store(final FloatBuffer buffer) {
        buffer.position(0);
        buffer.put(0, this.getX());
        buffer.put(1, this.getY());
        buffer.put(2, this.getZ());
    }
    
    @Override
    public String toString() {
        return "FloatVector3[x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ();
    }
    
    @NotNull
    public static FloatVector3 calculateViewVector(final float xRot, final float yRot) {
        final float var0 = xRot * 0.017453292f;
        final float var2 = -yRot * 0.017453292f;
        final float var3 = (float)Math.cos(var2);
        final float var4 = (float)Math.sin(var2);
        final float var5 = (float)Math.cos(var0);
        final float var6 = (float)Math.sin(var0);
        return new FloatVector3(var4 * var5, -var6, var3 * var5);
    }
    
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }
    
    public double lengthSquared() {
        return this.square(this.getX()) + this.square(this.getY()) + this.square(this.getZ());
    }
    
    public double horizontalDistance() {
        return Math.sqrt(this.horizontalDistanceSquared());
    }
    
    public double horizontalDistanceSquared() {
        return this.getX() * this.getX() + this.getZ() * this.getZ();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FloatVector3 that = (FloatVector3)o;
        return Float.compare(that.getX(), this.getX()) == 0 && Float.compare(that.getY(), this.getY()) == 0 && Float.compare(that.getZ(), this.getZ()) == 0;
    }
    
    @Override
    public int hashCode() {
        int result = (this.getX() != 0.0f) ? Float.floatToIntBits(this.getX()) : 0;
        result = 31 * result + ((this.getY() != 0.0f) ? Float.floatToIntBits(this.getY()) : 0);
        result = 31 * result + ((this.getZ() != 0.0f) ? Float.floatToIntBits(this.getZ()) : 0);
        return result;
    }
    
    static {
        XN = new FloatVector3(-1.0f, 0.0f, 0.0f);
        XP = new FloatVector3(1.0f, 0.0f, 0.0f);
        YN = new FloatVector3(0.0f, -1.0f, 0.0f);
        YP = new FloatVector3(0.0f, 1.0f, 0.0f);
        ZN = new FloatVector3(0.0f, 0.0f, -1.0f);
        ZP = new FloatVector3(0.0f, 0.0f, 1.0f);
        ZERO = new FloatVector3(0.0f, 0.0f, 0.0f);
        ONE = new FloatVector3(1.0f, 1.0f, 1.0f);
        ROTATION_QUATERNION = new Quaternion();
        ROTATION_DEGREES_QUATERNION = new Quaternion();
    }
}
