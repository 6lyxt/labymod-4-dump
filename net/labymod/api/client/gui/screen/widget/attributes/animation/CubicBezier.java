// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.MathHelper;

public class CubicBezier
{
    public static final CubicBezier LINEAR;
    public static final CubicBezier EASE_IN_OUT;
    public static final CubicBezier EASE_IN;
    public static final CubicBezier EASE_OUT;
    public static final CubicBezier BOUNCE;
    private static final CubicBezier[] VALUES;
    private static final double EPSILON = 1.0E-6;
    private final String name;
    private final double cx;
    private final double bx;
    private final double ax;
    private final double cy;
    private final double by;
    private final double ay;
    
    public CubicBezier(final double p0, final double p1, final double p2, final double p3) {
        this("custom", p0, p1, p2, p3);
    }
    
    public CubicBezier(final String name, final double p1x, final double p1y, final double p2x, final double p2y) {
        this.name = name;
        this.cx = 3.0 * p1x;
        this.bx = 3.0 * (p2x - p1x) - this.cx;
        this.ax = 1.0 - this.cx - this.bx;
        this.cy = 3.0 * p1y;
        this.by = 3.0 * (p2y - p1y) - this.cy;
        this.ay = 1.0 - this.cy - this.by;
    }
    
    public double curve(final double t) {
        return this.solve(t, 1.0E-6);
    }
    
    private double solve(final double x, final double epsilon) {
        return this.sampleCurveY(this.solveCurveX(x, epsilon));
    }
    
    private double solveCurveX(final double x, final double epsilon) {
        double t2 = x;
        for (double i = 0.0; i < 8.0; ++i) {
            final double x2 = this.sampleCurveX(t2) - x;
            if (Math.abs(x2) < epsilon) {
                return t2;
            }
            final double d2 = this.sampleCurveDerivativeX(t2);
            if (Math.abs(d2) < epsilon) {
                break;
            }
            t2 -= x2 / d2;
        }
        double t3 = 0.0;
        double t4 = 1.0;
        t2 = x;
        if (t2 < t3) {
            return t3;
        }
        if (t2 > t4) {
            return t4;
        }
        while (t3 < t4) {
            final double x2 = this.sampleCurveX(t2);
            if (Math.abs(x2 - x) < epsilon) {
                return t2;
            }
            if (x > x2) {
                t3 = t2;
            }
            else {
                t4 = t2;
            }
            t2 = (t4 - t3) * 0.5 + t3;
        }
        return t2;
    }
    
    private double sampleCurveX(final double t) {
        return ((this.ax * t + this.bx) * t + this.cx) * t;
    }
    
    private double sampleCurveY(final double t) {
        return ((this.ay * t + this.by) * t + this.cy) * t;
    }
    
    private double sampleCurveDerivativeX(final double t) {
        return (3.0 * this.ax * t + 2.0 * this.bx) * t + this.cx;
    }
    
    public double interpolate(final double fromValue, final double toValue, final long fromMillis, long toMillis, long currentMillis) {
        currentMillis -= fromMillis;
        toMillis -= fromMillis;
        final double progressPercentage = MathHelper.clamp(currentMillis / (double)toMillis, 0.0, 1.0);
        return fromValue + (toValue - fromValue) * this.curve(progressPercentage);
    }
    
    public float interpolate(final float fromValue, final float toValue, final long fromMillis, long toMillis, long currentMillis) {
        currentMillis -= fromMillis;
        toMillis -= fromMillis;
        final double progressPercentage = MathHelper.clamp(currentMillis / (double)toMillis, 0.0, 1.0);
        return fromValue + (toValue - fromValue) * (float)this.curve(progressPercentage);
    }
    
    public FloatVector3 interpolateVector(FloatVector3 fromVector, FloatVector3 toVector, final long fromMillis, long toMillis, long currentMillis) {
        fromVector = new FloatVector3(fromVector);
        toVector = new FloatVector3(toVector);
        currentMillis -= fromMillis;
        toMillis -= fromMillis;
        final double progressPercentage = MathHelper.clamp(currentMillis / (double)toMillis, 0.0, 1.0);
        return fromVector.add(toVector.sub(fromVector).multiply((float)this.curve(progressPercentage)));
    }
    
    public String getName() {
        return this.name;
    }
    
    public static CubicBezier[] named() {
        return CubicBezier.VALUES;
    }
    
    @Nullable
    public static CubicBezier of(final String value) {
        if (value == null) {
            return null;
        }
        for (final CubicBezier cubicBezier : CubicBezier.VALUES) {
            if (value.equals(cubicBezier.getName())) {
                return cubicBezier;
            }
        }
        return null;
    }
    
    static {
        LINEAR = new CubicBezier("linear", 0.0, 0.0, 1.0, 1.0);
        EASE_IN_OUT = new CubicBezier("ease-in-out", 0.5, 0.0, 0.5, 1.0);
        EASE_IN = new CubicBezier("ease-in", 0.5, 0.0, 1.0, 1.0);
        EASE_OUT = new CubicBezier("ease-out", 0.0, 0.0, 0.5, 1.0);
        BOUNCE = new CubicBezier("bounce", 0.34, 1.36, 0.51, 1.16);
        VALUES = new CubicBezier[] { CubicBezier.LINEAR, CubicBezier.EASE_IN_OUT, CubicBezier.EASE_IN, CubicBezier.EASE_OUT };
    }
}
