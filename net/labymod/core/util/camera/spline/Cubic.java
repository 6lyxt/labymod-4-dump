// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.camera.spline;

public class Cubic
{
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    
    public Cubic(final double a, final double b, final double c, final double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public double eval(final double u) {
        return ((this.d * u + this.c) * u + this.b) * u + this.a;
    }
}
