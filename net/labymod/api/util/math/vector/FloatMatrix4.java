// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

import net.labymod.api.util.math.MathHelper;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import net.labymod.api.util.math.Quaternion;

public class FloatMatrix4 implements Matrix4
{
    private static final FloatVector3 ROTATION_VECTOR;
    private static final FloatMatrix4 SCALE_MATRIX;
    private static final FloatMatrix4 QUATERNION_MATRIX;
    private float m00;
    private float m01;
    private float m02;
    private float m03;
    private float m10;
    private float m11;
    private float m12;
    private float m13;
    private float m20;
    private float m21;
    private float m22;
    private float m23;
    private float m30;
    private float m31;
    private float m32;
    private float m33;
    
    public FloatMatrix4() {
    }
    
    public FloatMatrix4(final float m00, final float m01, final float m02, final float m03, final float m10, final float m11, final float m12, final float m13, final float m20, final float m21, final float m22, final float m23, final float m30, final float m31, final float m32, final float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }
    
    public FloatMatrix4(final FloatMatrix4 matrix) {
        this.m00 = matrix.m00;
        this.m01 = matrix.m01;
        this.m02 = matrix.m02;
        this.m03 = matrix.m03;
        this.m10 = matrix.m10;
        this.m11 = matrix.m11;
        this.m12 = matrix.m12;
        this.m13 = matrix.m13;
        this.m20 = matrix.m20;
        this.m21 = matrix.m21;
        this.m22 = matrix.m22;
        this.m23 = matrix.m23;
        this.m30 = matrix.m30;
        this.m31 = matrix.m31;
        this.m32 = matrix.m32;
        this.m33 = matrix.m33;
    }
    
    public FloatMatrix4(final Quaternion quaternion) {
        this.setQuaternion(quaternion);
    }
    
    public static FloatMatrix4 newIdentity() {
        return new FloatMatrix4().identity();
    }
    
    public void store(final FloatBuffer buffer) {
        this.store(0, buffer);
    }
    
    public void store(final int position, final FloatBuffer buffer) {
        buffer.put(position, this.m00);
        buffer.put(position + 4, this.m01);
        buffer.put(position + 8, this.m02);
        buffer.put(position + 12, this.m03);
        buffer.put(position + 1, this.m10);
        buffer.put(position + 5, this.m11);
        buffer.put(position + 9, this.m12);
        buffer.put(position + 13, this.m13);
        buffer.put(position + 2, this.m20);
        buffer.put(position + 6, this.m21);
        buffer.put(position + 10, this.m22);
        buffer.put(position + 14, this.m23);
        buffer.put(position + 3, this.m30);
        buffer.put(position + 7, this.m31);
        buffer.put(position + 11, this.m32);
        buffer.put(position + 15, this.m33);
    }
    
    public void store(final float[] buffer) {
        this.store(0, buffer);
    }
    
    public void store(final int position, final float[] buffer) {
        buffer[position] = this.m00;
        buffer[position + 4] = this.m01;
        buffer[position + 8] = this.m02;
        buffer[position + 12] = this.m03;
        buffer[position + 1] = this.m10;
        buffer[position + 5] = this.m11;
        buffer[position + 9] = this.m12;
        buffer[position + 13] = this.m13;
        buffer[position + 2] = this.m20;
        buffer[position + 6] = this.m21;
        buffer[position + 10] = this.m22;
        buffer[position + 14] = this.m23;
        buffer[position + 3] = this.m30;
        buffer[position + 7] = this.m31;
        buffer[position + 11] = this.m32;
        buffer[position + 15] = this.m33;
    }
    
    public void load(final FloatBuffer buffer) {
        this.load(0, buffer);
    }
    
    public void load(final int position, final FloatBuffer buffer) {
        this.m00 = buffer.get(position);
        this.m01 = buffer.get(position + 4);
        this.m02 = buffer.get(position + 8);
        this.m03 = buffer.get(position + 12);
        this.m10 = buffer.get(position + 1);
        this.m11 = buffer.get(position + 5);
        this.m12 = buffer.get(position + 9);
        this.m13 = buffer.get(position + 13);
        this.m20 = buffer.get(position + 2);
        this.m21 = buffer.get(position + 6);
        this.m22 = buffer.get(position + 10);
        this.m23 = buffer.get(position + 14);
        this.m30 = buffer.get(position + 3);
        this.m31 = buffer.get(position + 7);
        this.m32 = buffer.get(position + 11);
        this.m33 = buffer.get(position + 15);
    }
    
    public void load(final DoubleBuffer buffer) {
        this.m00 = (float)buffer.get(0);
        this.m01 = (float)buffer.get(4);
        this.m02 = (float)buffer.get(8);
        this.m03 = (float)buffer.get(12);
        this.m10 = (float)buffer.get(1);
        this.m11 = (float)buffer.get(5);
        this.m12 = (float)buffer.get(9);
        this.m13 = (float)buffer.get(13);
        this.m20 = (float)buffer.get(2);
        this.m21 = (float)buffer.get(6);
        this.m22 = (float)buffer.get(10);
        this.m23 = (float)buffer.get(14);
        this.m30 = (float)buffer.get(3);
        this.m31 = (float)buffer.get(7);
        this.m32 = (float)buffer.get(11);
        this.m33 = (float)buffer.get(15);
    }
    
    public void setScale(final FloatVector3 scaleVector) {
        this.m00 = scaleVector.getX();
        this.m11 = scaleVector.getY();
        this.m22 = scaleVector.getZ();
        this.m33 = 1.0f;
    }
    
    public void setScale(final float x, final float y, final float z) {
        this.m00 = x;
        this.m11 = y;
        this.m22 = z;
        this.m33 = 1.0f;
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        FloatMatrix4.SCALE_MATRIX.identity();
        FloatMatrix4.SCALE_MATRIX.m00 = x;
        FloatMatrix4.SCALE_MATRIX.m11 = y;
        FloatMatrix4.SCALE_MATRIX.m22 = z;
        this.multiply(FloatMatrix4.SCALE_MATRIX);
    }
    
    public static FloatMatrix4 scaleMatrix(final float scaleFactor) {
        return scaleMatrix(scaleFactor, scaleFactor, scaleFactor);
    }
    
    public static FloatMatrix4 scaleMatrix(final float scaleX, final float scaleY, final float scaleZ) {
        FloatMatrix4.SCALE_MATRIX.identity();
        FloatMatrix4.SCALE_MATRIX.m00 = scaleX;
        FloatMatrix4.SCALE_MATRIX.m11 = scaleY;
        FloatMatrix4.SCALE_MATRIX.m22 = scaleZ;
        return FloatMatrix4.SCALE_MATRIX;
    }
    
    public static FloatMatrix4 createTranslateMatrix(final float x, final float y, final float z) {
        final FloatMatrix4 translateMatrix = new FloatMatrix4();
        translateMatrix.m00 = 1.0f;
        translateMatrix.m11 = 1.0f;
        translateMatrix.m22 = 1.0f;
        translateMatrix.m33 = 1.0f;
        translateMatrix.m03 = x;
        translateMatrix.m13 = y;
        translateMatrix.m23 = z;
        return translateMatrix;
    }
    
    public static FloatMatrix4 orthographic(final float width, final float height, final float zNear, final float zFar) {
        final FloatMatrix4 var0 = new FloatMatrix4();
        var0.m00 = 2.0f / width;
        var0.m11 = 2.0f / height;
        final float var2 = zFar - zNear;
        var0.m22 = -2.0f / var2;
        var0.m33 = 1.0f;
        var0.m03 = -1.0f;
        var0.m13 = 1.0f;
        var0.m23 = -(zFar + zNear) / var2;
        return var0;
    }
    
    public static FloatMatrix4 orthographic(final float left, final float right, final float bottom, final float top, final float zNear, final float zFar) {
        final FloatMatrix4 matrix = new FloatMatrix4();
        final float var1 = right - left;
        final float var2 = bottom - top;
        final float var3 = zFar - zNear;
        matrix.m00 = 2.0f / var1;
        matrix.m11 = 2.0f / var2;
        matrix.m22 = -2.0f / var3;
        matrix.m03 = -(right + left) / var1;
        matrix.m13 = -(bottom + top) / var2;
        matrix.m23 = -(zFar + zNear) / var3;
        matrix.m33 = 1.0f;
        return matrix;
    }
    
    public FloatMatrix4 setOrthographic(final float width, final float height, final float zNear, final float zFar) {
        this.m00 = 2.0f / width;
        this.m11 = 2.0f / height;
        final float var1 = zFar - zNear;
        this.m22 = -2.0f / var1;
        this.m33 = 1.0f;
        this.m03 = -1.0f;
        this.m13 = 1.0f;
        this.m23 = -(zFar + zNear) / var1;
        return this;
    }
    
    public FloatMatrix4 setOrthographic(final float left, final float right, final float bottom, final float top, final float zNear, final float zFar) {
        final float var1 = right - left;
        final float var2 = -(bottom - top);
        final float var3 = zFar - zNear;
        this.m00 = 2.0f / var1;
        this.m11 = 2.0f / var2;
        this.m22 = -2.0f / var3;
        this.m03 = -(right + left) / var1;
        this.m13 = -(bottom + top) / var2;
        this.m23 = -(zFar + zNear) / var3;
        this.m33 = 1.0f;
        return this;
    }
    
    public void setPerspective(final double fov, final float aspect, final float nearFar, final float depthFar) {
        final float var0 = (float)(1.0 / Math.tan(fov * 0.01745329238474369 / 2.0));
        this.m00 = var0 / aspect;
        this.m11 = var0;
        this.m22 = (depthFar + nearFar) / (nearFar - depthFar);
        this.m32 = -1.0f;
        this.m23 = 2.0f * depthFar * nearFar / (nearFar - depthFar);
    }
    
    public FloatMatrix4 copy() {
        return new FloatMatrix4(this);
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        this.m03 += x;
        this.m13 += y;
        this.m23 += z;
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        FloatMatrix4.ROTATION_VECTOR.set(x, y, z);
        this.multiply(FloatMatrix4.ROTATION_VECTOR.rotationDegrees(angle));
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        FloatMatrix4.ROTATION_VECTOR.set(x, y, z);
        this.multiply(FloatMatrix4.ROTATION_VECTOR.rotation(radians, false));
    }
    
    public void translate(final FloatVector3 vector) {
        this.translate(vector.getX(), vector.getY(), vector.getZ());
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        final float m00 = MathHelper.fma(this.m00, matrix.m00, MathHelper.fma(this.m01, matrix.m10, MathHelper.fma(this.m02, matrix.m20, this.m03 * matrix.m30)));
        final float m2 = MathHelper.fma(this.m00, matrix.m01, MathHelper.fma(this.m01, matrix.m11, MathHelper.fma(this.m02, matrix.m21, this.m03 * matrix.m31)));
        final float m3 = MathHelper.fma(this.m00, matrix.m02, MathHelper.fma(this.m01, matrix.m12, MathHelper.fma(this.m02, matrix.m22, this.m03 * matrix.m32)));
        final float m4 = MathHelper.fma(this.m00, matrix.m03, MathHelper.fma(this.m01, matrix.m13, MathHelper.fma(this.m02, matrix.m23, this.m03 * matrix.m33)));
        final float m5 = MathHelper.fma(this.m10, matrix.m00, MathHelper.fma(this.m11, matrix.m10, MathHelper.fma(this.m12, matrix.m20, this.m13 * matrix.m30)));
        final float m6 = MathHelper.fma(this.m10, matrix.m01, MathHelper.fma(this.m11, matrix.m11, MathHelper.fma(this.m12, matrix.m21, this.m13 * matrix.m31)));
        final float m7 = MathHelper.fma(this.m10, matrix.m02, MathHelper.fma(this.m11, matrix.m12, MathHelper.fma(this.m12, matrix.m22, this.m13 * matrix.m32)));
        final float m8 = MathHelper.fma(this.m10, matrix.m03, MathHelper.fma(this.m11, matrix.m13, MathHelper.fma(this.m12, matrix.m23, this.m13 * matrix.m33)));
        final float m9 = MathHelper.fma(this.m20, matrix.m00, MathHelper.fma(this.m21, matrix.m10, MathHelper.fma(this.m22, matrix.m20, this.m23 * matrix.m30)));
        final float m10 = MathHelper.fma(this.m20, matrix.m01, MathHelper.fma(this.m21, matrix.m11, MathHelper.fma(this.m22, matrix.m21, this.m23 * matrix.m31)));
        final float m11 = MathHelper.fma(this.m20, matrix.m02, MathHelper.fma(this.m21, matrix.m12, MathHelper.fma(this.m22, matrix.m22, this.m23 * matrix.m32)));
        final float m12 = MathHelper.fma(this.m20, matrix.m03, MathHelper.fma(this.m21, matrix.m13, MathHelper.fma(this.m22, matrix.m23, this.m23 * matrix.m33)));
        final float m13 = MathHelper.fma(this.m30, matrix.m00, MathHelper.fma(this.m31, matrix.m10, MathHelper.fma(this.m32, matrix.m20, this.m33 * matrix.m30)));
        final float m14 = MathHelper.fma(this.m30, matrix.m01, MathHelper.fma(this.m31, matrix.m11, MathHelper.fma(this.m32, matrix.m21, this.m33 * matrix.m31)));
        final float m15 = MathHelper.fma(this.m30, matrix.m02, MathHelper.fma(this.m31, matrix.m12, MathHelper.fma(this.m32, matrix.m22, this.m33 * matrix.m32)));
        final float m16 = MathHelper.fma(this.m30, matrix.m03, MathHelper.fma(this.m31, matrix.m13, MathHelper.fma(this.m32, matrix.m23, this.m33 * matrix.m33)));
        this.m00 = m00;
        this.m01 = m2;
        this.m02 = m3;
        this.m03 = m4;
        this.m10 = m5;
        this.m11 = m6;
        this.m12 = m7;
        this.m13 = m8;
        this.m20 = m9;
        this.m21 = m10;
        this.m22 = m11;
        this.m23 = m12;
        this.m30 = m13;
        this.m31 = m14;
        this.m32 = m15;
        this.m33 = m16;
    }
    
    public void set(final FloatMatrix4 matrix4) {
        this.m00 = matrix4.m00;
        this.m01 = matrix4.m01;
        this.m02 = matrix4.m02;
        this.m03 = matrix4.m03;
        this.m10 = matrix4.m10;
        this.m11 = matrix4.m11;
        this.m12 = matrix4.m12;
        this.m13 = matrix4.m13;
        this.m20 = matrix4.m20;
        this.m21 = matrix4.m21;
        this.m22 = matrix4.m22;
        this.m23 = matrix4.m23;
        this.m30 = matrix4.m30;
        this.m31 = matrix4.m31;
        this.m32 = matrix4.m32;
        this.m33 = matrix4.m33;
    }
    
    public void rotation(final Quaternion quaternion, final boolean degrees) {
        final float x = quaternion.getX();
        final float y = quaternion.getY();
        final float z = quaternion.getZ();
        final FloatVector3 xRotation = FloatVector3.XP;
        final FloatVector3 yRotation = FloatVector3.YP;
        final FloatVector3 zRotation = FloatVector3.ZP;
        if (degrees) {
            this.multiply(zRotation.rotationDegrees(z));
            this.multiply(yRotation.rotationDegrees(y));
            this.multiply(xRotation.rotationDegrees(x));
        }
        else {
            this.multiply(zRotation.rotation(z, false));
            this.multiply(yRotation.rotation(y, false));
            this.multiply(xRotation.rotation(x, false));
        }
    }
    
    public void set(final float m00, final float m01, final float m02, final float m03, final float m10, final float m11, final float m12, final float m13, final float m20, final float m21, final float m22, final float m23, final float m30, final float m31, final float m32, final float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }
    
    public void setQuaternion(final Quaternion quaternion) {
        final float x = quaternion.getX();
        final float y = quaternion.getY();
        final float z = quaternion.getZ();
        final float w = quaternion.getW();
        final float scaledX = 2.0f * x * x;
        final float scaledY = 2.0f * y * y;
        final float scaledZ = 2.0f * z * z;
        this.m00 = 1.0f - scaledY - scaledZ;
        this.m11 = 1.0f - scaledZ - scaledX;
        this.m22 = 1.0f - scaledX - scaledY;
        this.m33 = 1.0f;
        final float xy = x * y;
        final float yz = y * z;
        final float zx = z * x;
        final float xw = x * w;
        final float yw = y * w;
        final float zw = z * w;
        this.m10 = 2.0f * (xy + zw);
        this.m01 = 2.0f * (xy - zw);
        this.m20 = 2.0f * (zx - yw);
        this.m02 = 2.0f * (zx + yw);
        this.m21 = 2.0f * (yz + xw);
        this.m12 = 2.0f * (yz - xw);
    }
    
    public float getM00() {
        return this.m00;
    }
    
    public void setM00(final float m00) {
        this.m00 = m00;
    }
    
    public float getM01() {
        return this.m01;
    }
    
    public void setM01(final float m01) {
        this.m01 = m01;
    }
    
    public float getM02() {
        return this.m02;
    }
    
    public void setM02(final float m02) {
        this.m02 = m02;
    }
    
    public float getM03() {
        return this.m03;
    }
    
    public void setM03(final float m03) {
        this.m03 = m03;
    }
    
    public float getM10() {
        return this.m10;
    }
    
    public void setM10(final float m10) {
        this.m10 = m10;
    }
    
    public float getM11() {
        return this.m11;
    }
    
    public void setM11(final float m11) {
        this.m11 = m11;
    }
    
    public float getM12() {
        return this.m12;
    }
    
    public void setM12(final float m12) {
        this.m12 = m12;
    }
    
    public float getM13() {
        return this.m13;
    }
    
    public void setM13(final float m13) {
        this.m13 = m13;
    }
    
    public float getM20() {
        return this.m20;
    }
    
    public void setM20(final float m20) {
        this.m20 = m20;
    }
    
    public float getM21() {
        return this.m21;
    }
    
    public void setM21(final float m21) {
        this.m21 = m21;
    }
    
    public float getM22() {
        return this.m22;
    }
    
    public void setM22(final float m22) {
        this.m22 = m22;
    }
    
    public float getM23() {
        return this.m23;
    }
    
    public void setM23(final float m23) {
        this.m23 = m23;
    }
    
    public float getM30() {
        return this.m30;
    }
    
    public void setM30(final float m30) {
        this.m30 = m30;
    }
    
    public float getM31() {
        return this.m31;
    }
    
    public void setM31(final float m31) {
        this.m31 = m31;
    }
    
    public float getM32() {
        return this.m32;
    }
    
    public void setM32(final float m32) {
        this.m32 = m32;
    }
    
    public float getM33() {
        return this.m33;
    }
    
    public void setM33(final float m33) {
        this.m33 = m33;
    }
    
    public void multiplyWithTranslation(final float x, final float y, final float z) {
        this.m03 += this.fma(this.m00, x, this.fma(this.m01, y, this.m02 * z));
        this.m13 += this.fma(this.m10, x, this.fma(this.m11, y, this.m12 * z));
        this.m23 += this.fma(this.m20, x, this.fma(this.m21, y, this.m22 * z));
        this.m33 += this.fma(this.m30, x, this.fma(this.m31, y, this.m32 * z));
    }
    
    public void fastTranslate(final float x, final float y, final float z) {
        this.m03 = this.fma(this.m00, x, this.fma(this.m01, y, this.fma(this.m02, z, this.m03)));
        this.m13 = this.fma(this.m10, x, this.fma(this.m11, y, this.fma(this.m12, z, this.m13)));
        this.m23 = this.fma(this.m20, x, this.fma(this.m21, y, this.fma(this.m22, z, this.m23)));
        this.m33 = this.fma(this.m30, x, this.fma(this.m31, y, this.fma(this.m32, z, this.m33)));
    }
    
    public void fastTranslate(final FloatVector3 vector) {
        this.fastTranslate(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public float transformVectorX(final float x, final float y, final float z) {
        return this.m00 * x + this.m01 * y + this.m02 * z + this.m03;
    }
    
    public float transformVectorY(final float x, final float y, final float z) {
        return this.m10 * x + this.m11 * y + this.m12 * z + this.m13;
    }
    
    public float transformVectorZ(final float x, final float y, final float z) {
        return this.m20 * x + this.m21 * y + this.m22 * z + this.m23;
    }
    
    public void multiply(final Quaternion quaternion) {
        if (quaternion.hasNoRotation()) {
            return;
        }
        FloatMatrix4.QUATERNION_MATRIX.setQuaternion(quaternion);
        this.multiply(FloatMatrix4.QUATERNION_MATRIX);
    }
    
    public FloatMatrix4 identity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
        return this;
    }
    
    public FloatMatrix4 invert() {
        final float a = this.m00 * this.m11 - this.m01 * this.m10;
        final float b = this.m00 * this.m12 - this.m02 * this.m10;
        final float c = this.m00 * this.m13 - this.m03 * this.m10;
        final float d = this.m01 * this.m12 - this.m02 * this.m11;
        final float e = this.m01 * this.m13 - this.m03 * this.m11;
        final float f = this.m02 * this.m13 - this.m03 * this.m12;
        final float g = this.m20 * this.m31 - this.m21 * this.m30;
        final float h = this.m20 * this.m32 - this.m22 * this.m30;
        final float i = this.m20 * this.m33 - this.m23 * this.m30;
        final float j = this.m21 * this.m32 - this.m22 * this.m31;
        final float k = this.m21 * this.m33 - this.m23 * this.m31;
        final float l = this.m22 * this.m33 - this.m23 * this.m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        final float nm00 = this.fma(this.m11, l, this.fma(-this.m12, k, this.m13 * j)) * det;
        final float nm2 = this.fma(-this.m01, l, this.fma(this.m02, k, -this.m03 * j)) * det;
        final float nm3 = this.fma(this.m31, f, this.fma(-this.m32, e, this.m33 * d)) * det;
        final float nm4 = this.fma(-this.m21, f, this.fma(this.m22, e, -this.m23 * d)) * det;
        final float nm5 = this.fma(-this.m10, l, this.fma(this.m12, i, -this.m13 * h)) * det;
        final float nm6 = this.fma(this.m00, l, this.fma(-this.m02, i, this.m03 * h)) * det;
        final float nm7 = this.fma(-this.m30, f, this.fma(this.m32, c, -this.m33 * b)) * det;
        final float nm8 = this.fma(this.m20, f, this.fma(-this.m22, c, this.m23 * b)) * det;
        final float nm9 = this.fma(this.m10, k, this.fma(-this.m11, i, this.m13 * g)) * det;
        final float nm10 = this.fma(-this.m00, k, this.fma(this.m01, i, -this.m03 * g)) * det;
        final float nm11 = this.fma(this.m30, e, this.fma(-this.m31, c, this.m33 * a)) * det;
        final float nm12 = this.fma(-this.m20, e, this.fma(this.m21, c, -this.m23 * a)) * det;
        final float nm13 = this.fma(-this.m10, j, this.fma(this.m11, h, -this.m12 * g)) * det;
        final float nm14 = this.fma(this.m00, j, this.fma(-this.m01, h, this.m02 * g)) * det;
        final float nm15 = this.fma(-this.m30, d, this.fma(this.m31, b, -this.m32 * a)) * det;
        final float nm16 = this.fma(this.m20, d, this.fma(-this.m21, b, this.m22 * a)) * det;
        return new FloatMatrix4(nm00, nm2, nm3, nm4, nm5, nm6, nm7, nm8, nm9, nm10, nm11, nm12, nm13, nm14, nm15, nm16);
    }
    
    public FloatVector3 transformPosition(final float x, final float y, final float z, final FloatVector3 destination) {
        destination.set(x, y, z);
        destination.multiply(this);
        return destination;
    }
    
    private float fma(final float a, final float b, final float c) {
        return MathHelper.fma(a, b, c);
    }
    
    @Override
    public String toString() {
        return "FloatMatrix4:\n" + this.m00 + " " + this.m01 + " " + this.m02 + " " + this.m03 + "\n" + this.m10 + " " + this.m11 + " " + this.m12 + " " + this.m13 + "\n" + this.m20 + " " + this.m21 + " " + this.m22 + " " + this.m23 + "\n" + this.m30 + " " + this.m31 + " " + this.m32 + " " + this.m33;
    }
    
    static {
        ROTATION_VECTOR = new FloatVector3(0.0f, 0.0f, 0.0f);
        SCALE_MATRIX = newIdentity();
        QUATERNION_MATRIX = newIdentity();
    }
}
