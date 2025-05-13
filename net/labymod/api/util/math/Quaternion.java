// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import net.labymod.api.util.math.vector.FloatVector3;

public class Quaternion
{
    public static final Quaternion ONE;
    private float x;
    private float y;
    private float z;
    private float w;
    
    public Quaternion() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }
    
    public Quaternion(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Quaternion(final FloatVector3 axis, final float rotationAngle, final boolean degrees) {
        this.setRotation(axis, rotationAngle, degrees);
    }
    
    public Quaternion(float x, float y, float z, final boolean degrees) {
        if (degrees) {
            x *= 0.017453292f;
            y *= 0.017453292f;
            z *= 0.017453292f;
        }
        final float sinX = sin(0.5f * x);
        final float cosX = cos(0.5f * x);
        final float sinY = sin(0.5f * y);
        final float cosY = cos(0.5f * y);
        final float sinZ = sin(0.5f * z);
        final float cosZ = cos(0.5f * z);
        this.x = sinX * cosY * cosZ + cosX * sinY * sinZ;
        this.y = cosX * sinY * cosZ - sinX * cosY * sinZ;
        this.z = sinX * sinY * cosZ + cosX * cosY * sinZ;
        this.w = cosX * cosY * cosZ - sinX * sinY * sinZ;
    }
    
    public Quaternion(final Quaternion other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }
    
    public static Quaternion fromYXZ(final float x, final float y, final float z) {
        final Quaternion one = Quaternion.ONE.copy();
        one.multiply(new Quaternion(0.0f, sin(x / 2.0f), 0.0f, cos(x / 2.0f)));
        one.multiply(new Quaternion(sin(y / 2.0f), 0.0f, 0.0f, cos(y / 2.0f)));
        one.multiply(new Quaternion(0.0f, 0.0f, sin(z / 2.0f), cos(z / 2.0f)));
        return one;
    }
    
    public static Quaternion fromXYZDegrees(final FloatVector3 axis) {
        return fromXYZ((float)Math.toRadians(axis.getX()), (float)Math.toRadians(axis.getY()), (float)Math.toRadians(axis.getZ()));
    }
    
    public static Quaternion fromXYZ(final FloatVector3 axis) {
        return fromXYZ(axis.getX(), axis.getY(), axis.getZ());
    }
    
    public static Quaternion fromXYZ(final float x, final float y, final float z) {
        final Quaternion one = Quaternion.ONE.copy();
        one.multiply(new Quaternion(sin(x / 2.0f), 0.0f, 0.0f, cos(x / 2.0f)));
        one.multiply(new Quaternion(0.0f, sin(y / 2.0f), 0.0f, cos(y / 2.0f)));
        one.multiply(new Quaternion(0.0f, 0.0f, sin(z / 2.0f), cos(z / 2.0f)));
        return one;
    }
    
    public static Quaternion fromEuler(float yaw, float pitch, float roll) {
        yaw /= 2.0f;
        pitch /= 2.0f;
        roll /= 2.0f;
        final double qx = sin(roll) * cos(pitch) * cos(yaw) - cos(roll) * sin(pitch) * sin(yaw);
        final double qy = cos(roll) * sin(pitch) * cos(yaw) + sin(roll) * cos(pitch) * sin(yaw);
        final double qz = cos(roll) * cos(pitch) * sin(yaw) - sin(roll) * sin(pitch) * cos(yaw);
        final double qw = cos(roll) * cos(pitch) * cos(yaw) + sin(roll) * sin(pitch) * sin(yaw);
        return new Quaternion((float)qx, (float)qy, (float)qz, (float)qw);
    }
    
    public void setRotation(final FloatVector3 axis, float rotationAngle, final boolean degrees) {
        if (degrees) {
            rotationAngle *= 0.017453292f;
        }
        final float sinRotationAngle = sin(rotationAngle / 2.0f);
        this.x = axis.getX() * sinRotationAngle;
        this.y = axis.getY() * sinRotationAngle;
        this.z = axis.getZ() * sinRotationAngle;
        this.w = cos(rotationAngle / 2.0f);
    }
    
    public FloatVector3 toXYZ() {
        final float squareW = this.getW() * this.getW();
        final float squareX = this.getX() * this.getX();
        final float squareY = this.getY() * this.getY();
        final float squareZ = this.getZ() * this.getZ();
        final float sum = squareW + squareX + squareY + squareZ;
        final float value = 2.0f * this.getW() * this.getX() - 2.0f * this.getY() * this.getZ();
        final float arcSin = (float)Math.asin(value / sum);
        return (Math.abs(value) > 0.999f * sum) ? new FloatVector3(2.0f * (float)Math.atan2(this.getX(), this.getW()), arcSin, 0.0f) : new FloatVector3((float)Math.atan2(2.0f * this.getY() * this.getZ() + 2.0f * this.getX() * this.getW(), squareW - squareX - squareY + squareZ), arcSin, (float)Math.atan2(2.0f * this.getX() * this.getY() + 2.0f * this.getW() * this.getZ(), squareW + squareX - squareY - squareZ));
    }
    
    public FloatVector3 toXYZDegrees() {
        final FloatVector3 axis = this.toXYZ();
        return new FloatVector3((float)Math.toDegrees(axis.getX()), (float)Math.toDegrees(axis.getY()), (float)Math.toDegrees(axis.getZ()));
    }
    
    public FloatVector3 toYXZ() {
        final float squareW = this.getW() * this.getW();
        final float squareX = this.getX() * this.getX();
        final float squareY = this.getY() * this.getY();
        final float squareZ = this.getZ() * this.getZ();
        final float sum = squareW + squareX + squareY + squareZ;
        final float value = 2.0f * this.getW() * this.getX() - 2.0f * this.getY() * this.getZ();
        final float arcSin = (float)Math.asin(value / sum);
        return (Math.abs(value) > 0.999f * sum) ? new FloatVector3(arcSin, 2.0f * (float)Math.atan2(this.getY(), this.getW()), 0.0f) : new FloatVector3(arcSin, (float)Math.atan2(2.0f * this.getX() * this.getZ() + 2.0f * this.getY() * this.getW(), squareW - squareX - squareY + squareZ), (float)Math.atan2(2.0f * this.getX() * this.getY() + 2.0f * this.getW() * this.getZ(), squareW - squareX + squareY - squareZ));
    }
    
    public FloatVector3 toYXZDegrees() {
        final FloatVector3 axis = this.toYXZ();
        return new FloatVector3((float)Math.toDegrees(axis.getX()), (float)Math.toDegrees(axis.getY()), (float)Math.toDegrees(axis.getZ()));
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && this.getClass() == obj.getClass()) {
            final Quaternion quaternion = (Quaternion)obj;
            return Float.compare(quaternion.x, this.x) == 0 && Float.compare(quaternion.y, this.y) == 0 && Float.compare(quaternion.z, this.z) == 0 && Float.compare(quaternion.w, this.w) == 0;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int bits = Float.floatToIntBits(this.x);
        bits = 31 * bits + Float.floatToIntBits(this.y);
        bits = 31 * bits + Float.floatToIntBits(this.z);
        return 31 * bits + Float.floatToIntBits(this.w);
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
    
    public float getW() {
        return this.w;
    }
    
    public void setW(final float w) {
        this.w = w;
    }
    
    public void multiply(final Quaternion other) {
        final float x = this.getX();
        final float y = this.getY();
        final float z = this.getZ();
        final float w = this.getW();
        final float otherX = other.getX();
        final float otherY = other.getY();
        final float otherZ = other.getZ();
        final float otherW = other.getW();
        this.x = w * otherX + x * otherW + y * otherZ - z * otherY;
        this.y = w * otherY - x * otherZ + y * otherW + z * otherX;
        this.z = w * otherZ + x * otherY - y * otherX + z * otherW;
        this.w = w * otherW - x * otherX - y * otherY - z * otherZ;
    }
    
    public void multiply(final float factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        this.w *= factor;
    }
    
    public Quaternion conj() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }
    
    public void set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public void set(final Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }
    
    private static float cos(final float value) {
        return MathHelper.cos(value);
    }
    
    private static float sin(final float value) {
        return MathHelper.sin(value);
    }
    
    public float getYaw() {
        return (float)(-Math.toDegrees(Math.atan2(2.0f * (this.y * this.w - this.x * this.z), 1.0f - 2.0f * (this.y * this.y + this.z * this.z))));
    }
    
    public float getPitch() {
        return (float)Math.toDegrees(Math.asin(MathHelper.clamp(2.0f * (this.x * this.w - this.y * this.z), -1.0f, 1.0f)));
    }
    
    public float getRoll() {
        return (float)Math.toDegrees(Math.atan2(2.0f * (this.x * this.y + this.z * this.w), 1.0f - 2.0f * (this.x * this.x + this.z * this.z)));
    }
    
    public void normalize() {
        final float product = this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() + this.getW() * this.getW();
        if (product > 1.0E-6f) {
            final float inverseSqrt = MathHelper.fastInverseSqrt(product);
            this.x *= inverseSqrt;
            this.y *= inverseSqrt;
            this.z *= inverseSqrt;
            this.w *= inverseSqrt;
        }
        else {
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
            this.w = 0.0f;
        }
    }
    
    public boolean hasNoRotation() {
        return this.x == 0.0f && this.y == 0.0f && this.z == 0.0f;
    }
    
    public Quaternion copy() {
        return new Quaternion(this);
    }
    
    static {
        ONE = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    }
}
