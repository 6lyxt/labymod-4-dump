// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import net.labymod.api.util.function.Functional;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector3;

public final class MathHelper
{
    private static final FloatVector3 NORMAL_TRANSFORM;
    private static final FloatVector3 MATRIX_TRANSFORM;
    public static final int[] POWER_OF_TWO;
    private static final float FULL_ROT_RADIANS = 6.2831855f;
    private static final float MAGIC_PIXEL_NUMBER = 3.0f;
    private static final float POINT_SIZE_NUMBER = 1.333f;
    
    private MathHelper() {
    }
    
    public static float applyFloatingPointPosition(final boolean useFloatingPointPosition, final float value) {
        return useFloatingPointPosition ? value : ((float)(int)value);
    }
    
    public static long clamp(final long value, final long minimum, final long maximum) {
        return (value < minimum) ? minimum : Math.min(value, maximum);
    }
    
    public static byte clamp(final byte value, final byte minimum, final byte maximum) {
        return (value < minimum) ? minimum : ((value > maximum) ? maximum : value);
    }
    
    public static int clamp(final int value, final int minimum, final int maximum) {
        return (value < minimum) ? minimum : Math.min(value, maximum);
    }
    
    public static float clamp(final float value, final float minimum, final float maximum) {
        return (value < minimum) ? minimum : Math.min(value, maximum);
    }
    
    public static double clamp(final double value, final double minimum, final double maximum) {
        return (value < minimum) ? minimum : Math.min(value, maximum);
    }
    
    public static int ceil(final float value) {
        final int tmpValue = (int)value;
        return (value > tmpValue) ? (tmpValue + 1) : tmpValue;
    }
    
    public static int ceil(final double value) {
        final int tmpValue = (int)value;
        return (value > tmpValue) ? (tmpValue + 1) : tmpValue;
    }
    
    public static int ceilOrFloor(final float value) {
        return ceilOrFloor(value, 0.5f);
    }
    
    public static int ceilOrFloor(final float value, final float average) {
        final int flooredValue = floor(value);
        final int ceiledValue = ceil(value);
        final float averageValue = flooredValue + clamp(average, 0.0f, 1.0f);
        return (value < averageValue) ? flooredValue : ceiledValue;
    }
    
    public static float interpolateRotation(final float previousOffset, final float offset, final float delta) {
        float rotated;
        for (rotated = offset - previousOffset; rotated < -180.0f; rotated += 360.0f) {}
        while (rotated >= 180.0f) {
            rotated -= 360.0f;
        }
        return previousOffset + delta * rotated;
    }
    
    public static float rotateTowards(final float currentAngle, final float targetAngle, final float maxRotation) {
        final float angleDiff = degreesDifference(currentAngle, targetAngle);
        final float clampedAngleDiff = clamp(angleDiff, -maxRotation, maxRotation);
        return currentAngle + clampedAngleDiff;
    }
    
    public static float degreesDifference(final float a, final float b) {
        return wrapDegrees(b - a);
    }
    
    public static float wrapDegrees(final float angle) {
        float rotated = angle % 360.0f;
        if (rotated >= 180.0f) {
            rotated -= 360.0f;
        }
        if (rotated < -180.0f) {
            rotated += 360.0f;
        }
        return rotated;
    }
    
    public static float wrapRadians(final float angle) {
        float rotated = angle % 6.2831855f;
        if (rotated < 0.0f) {
            rotated += 6.2831855f;
        }
        return rotated;
    }
    
    public static float angleDifference(final float angle1, final float angle2) {
        final float angleDifference = (angle1 - angle2) % 360.0f;
        final float shortestDistance = 180.0f - Math.abs(Math.abs(angleDifference) - 180.0f);
        return ((angleDifference + 360.0f) % 360.0f < 180.0f) ? shortestDistance : (shortestDistance * -1.0f);
    }
    
    public static float normalizeRadians(final float angle) {
        return angle % 6.2831855f;
    }
    
    public static float roundRadians(final float angle) {
        return Math.round(angle / 6.2831855f) * 6.2831855f;
    }
    
    public static float convertRadians(final float sourceAngle, final float targetAngle) {
        final float normalizedDistanceAngle = wrapRadians(targetAngle - sourceAngle);
        final float invertedDistanceAngle = normalizedDistanceAngle - 6.2831855f;
        final float distanceAngle = (Math.abs(normalizedDistanceAngle) < Math.abs(invertedDistanceAngle)) ? normalizedDistanceAngle : invertedDistanceAngle;
        return sourceAngle + distanceAngle;
    }
    
    public static float convertDegrees(final float sourceAngle, final float targetAngle) {
        return toDegreesFloat(convertRadians(toRadiansFloat(sourceAngle), toRadiansFloat(targetAngle)));
    }
    
    public static int floor(final float value) {
        final int intValue = (int)value;
        return (value < intValue) ? (intValue - 1) : intValue;
    }
    
    public static int floor(final double value) {
        final int intValue = (int)value;
        return (value < intValue) ? (intValue - 1) : intValue;
    }
    
    public static boolean isAngleBetween(double angle, final double startAngle, double endAngle, final double fullRot) {
        endAngle = ((endAngle - startAngle < 0.0) ? (endAngle - startAngle + fullRot) : (endAngle - startAngle));
        angle = ((angle - startAngle < 0.0) ? (angle - startAngle + fullRot) : (angle - startAngle));
        return angle < endAngle;
    }
    
    public static double lerp(final double value, final double previousValue) {
        return lerp(value, previousValue, Laby.labyAPI().minecraft().getPartialTicks());
    }
    
    public static double lerp(final double value, final double previousValue, final float partialTicks) {
        return previousValue + (value - previousValue) * partialTicks;
    }
    
    public static float lerp(final float value, final float previousValue) {
        return lerp(value, previousValue, Laby.labyAPI().minecraft().getPartialTicks());
    }
    
    public static float lerp(final float value, final float previousValue, final float partialTicks) {
        return previousValue + (value - previousValue) * partialTicks;
    }
    
    public static int lerp(final int value, final int previousValue) {
        return lerp(value, previousValue, Laby.labyAPI().minecraft().getPartialTicks());
    }
    
    public static int lerp(final int value, final int previousValue, final float partialTicks) {
        return (int)(previousValue + (value - previousValue) * partialTicks);
    }
    
    public static double toDegreesDouble(final double angrad) {
        return Math.toDegrees(angrad);
    }
    
    public static float toDegreesFloat(final float angrad) {
        return (float)Math.toDegrees(angrad);
    }
    
    public static double toRadiansDouble(final double angle) {
        return Math.toRadians(angle);
    }
    
    public static float toRadiansFloat(final float angle) {
        return (float)Math.toRadians(angle);
    }
    
    public static float radiansToTarget(final float cameraYaw, final double cameraX, final double cameraZ, final double targetX, final double targetZ) {
        final double xDifference = targetX - cameraX;
        final double zDifference = targetZ - cameraZ;
        final double distance = Math.sqrt(xDifference * xDifference + zDifference * zDifference);
        if (distance == 0.0) {
            return 1.5707964f;
        }
        final float rotationRadians = (float)Math.toRadians(wrapDegrees(cameraYaw - 90.0f) + 180.0f);
        float positionRadians = (float)Math.acos(xDifference / distance) / 2.0f;
        if (zDifference < 0.0) {
            positionRadians = 3.1415927f - positionRadians;
        }
        final float fullRadians = positionRadians * 2.0f - rotationRadians + 1.5707964f;
        final float degrees = wrapDegrees((float)Math.toDegrees(fullRadians));
        return (float)Math.toRadians(-degrees + 180.0f);
    }
    
    public static float distanceSquared(final float startX, final float startY, final float startZ, final float endX, final float endY, final float endZ) {
        return square(startX - endX) + square(startY - endY) + square(startZ - endZ);
    }
    
    public static double distanceSquared(final Player startPlayer, final Player endPlayer) {
        return startPlayer.position().distanceSquared(endPlayer.position());
    }
    
    public static double distanceSquared(final double startX, final double startY, final double startZ, final double endX, final double endY, final double endZ) {
        return square(startX - endX) + square(startY - endY) + square(startZ - endZ);
    }
    
    public static float square(final float number) {
        return number * number;
    }
    
    public static double square(final double number) {
        return number * number;
    }
    
    public static float sigmoid(final float value) {
        if (value <= 0.0f || value >= 1.0f) {
            return value;
        }
        return (float)(1.0 / (1.0 + Math.pow(2.718281828459045, -1.0 * (value - 0.5) * 16.0)));
    }
    
    public static float catmullrom(final float t, final float p0, final float p1, final float p2, final float p3) {
        final float v0 = (p2 - p0) * 0.5f;
        final float v2 = (p3 - p1) * 0.5f;
        final float t2 = t * t;
        final float t3 = t * t2;
        return (2.0f * p1 - 2.0f * p2 + v0 + v2) * t3 + (-3.0f * p1 + 3.0f * p2 - 2.0f * v0 - v2) * t2 + v0 * t + p1;
    }
    
    public static float fastInverseSqrt(float value) {
        final float v = 0.5f * value;
        int bits = Float.floatToIntBits(value);
        bits = 1597463007 - (bits >> 1);
        value = Float.intBitsToFloat(bits);
        return value * (1.5f - v * value * value);
    }
    
    public static float fastInverseCubeRoot(final float value) {
        int intBits = Float.floatToIntBits(value);
        intBits = 1419967116 - intBits / 3;
        float floatBits = Float.intBitsToFloat(intBits);
        floatBits = 0.6666667f * floatBits + 1.0f / (3.0f * floatBits * floatBits * value);
        return 0.6666667f * floatBits + 1.0f / (3.0f * floatBits * floatBits * value);
    }
    
    public static float linearSin(final float value) {
        final float factor = (float)(0.6366197723675814 * (value % 6.283185307179586));
        return (factor < -1.0f) ? (-2.0f - factor) : ((factor > 1.0f) ? (2.0f - factor) : factor);
    }
    
    public static float toPixel(final float value) {
        return value / 3.0f;
    }
    
    public static float pixelToPointSize(final float value) {
        return value / 1.333f;
    }
    
    public static float pointSizeToPixel(final float value) {
        return value * 1.333f;
    }
    
    public static float scaleToFit(final float parentWidth, final float componentWidth) {
        return scaleToFit(parentWidth, componentWidth, 1.0f);
    }
    
    public static float scaleToFit(final float parentWidth, float componentWidth, final float scale) {
        componentWidth *= scale;
        if (parentWidth != 0.0f && componentWidth > parentWidth) {
            return parentWidth / componentWidth - 0.05f;
        }
        return scale;
    }
    
    public static byte normalIntValue(final float value) {
        return (byte)((int)(clamp(value, -1.0f, 1.0f) * 127.0f) & 0xFF);
    }
    
    public static int roundToward(final int a, final int b) {
        return positiveCeilDiv(a, b) * b;
    }
    
    public static int positiveCeilDiv(final int a, final int b) {
        return -Math.floorDiv(-a, b);
    }
    
    public static float sin(final double value) {
        return sin((float)value);
    }
    
    public static float sin(final float value) {
        return (float)Math.sin(value);
    }
    
    public static float cos(final double value) {
        return cos((float)value);
    }
    
    public static float cos(final float value) {
        return (float)Math.cos(value);
    }
    
    public static float fma(final float a, final float b, final float c) {
        return a * b + c;
    }
    
    public static FloatVector3 transform(final FloatMatrix3 matrix, final float srcX, final float srcY, final float srcZ) {
        MathHelper.NORMAL_TRANSFORM.set(fma(matrix.getM00(), srcX, fma(matrix.getM10(), srcY, matrix.getM20() * srcZ)), fma(matrix.getM01(), srcX, fma(matrix.getM11(), srcY, matrix.getM21() * srcZ)), fma(matrix.getM02(), srcX, fma(matrix.getM12(), srcY, matrix.getM22() * srcZ)));
        return MathHelper.NORMAL_TRANSFORM;
    }
    
    public static FloatVector3 transform(final FloatMatrix4 matrix, final float srcX, final float srcY, final float srcZ) {
        MathHelper.MATRIX_TRANSFORM.set(fma(matrix.getM00(), srcX, fma(matrix.getM10(), srcY, fma(matrix.getM20(), srcZ, matrix.getM30()))), fma(matrix.getM01(), srcX, fma(matrix.getM11(), srcY, fma(matrix.getM21(), srcZ, matrix.getM31()))), fma(matrix.getM02(), srcX, fma(matrix.getM12(), srcY, fma(matrix.getM22(), srcZ, matrix.getM32()))));
        return MathHelper.MATRIX_TRANSFORM;
    }
    
    public static boolean isPowerOfTwo(final int n) {
        return n > 0 && (n & n - 1) == 0x0;
    }
    
    public static GameMathMapper mapper() {
        return Laby.references().gameMathMapper();
    }
    
    public static boolean isBox(final AxisAlignedBoundingBox boundingBox) {
        return boundingBox.getMinX() == 0.0 && boundingBox.getMinY() == 0.0 && boundingBox.getMinZ() == 0.0 && boundingBox.getMaxX() == 1.0 && boundingBox.getMaxY() == 1.0 && boundingBox.getMaxZ() == 1.0;
    }
    
    static {
        NORMAL_TRANSFORM = new FloatVector3();
        MATRIX_TRANSFORM = new FloatVector3();
        POWER_OF_TWO = Functional.of(() -> {
            final IntArrayList list = new IntArrayList();
            int prev = 1;
            for (int i = 0; i < 16; ++i) {
                final int value = prev * 2;
                ((IntList)list).add(value);
                prev = value;
            }
            return ((IntList)list).toIntArray();
        });
    }
}
