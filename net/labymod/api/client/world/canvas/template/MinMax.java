// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas.template;

public class MinMax
{
    private static final MinMax UNRESTRICTED;
    private static final MinMax POSITIVE;
    private final Float min;
    private final Float max;
    
    private MinMax(final Float min, final Float max) {
        this.min = min;
        this.max = max;
    }
    
    public static MinMax min(final float min) {
        return new MinMax(min, null);
    }
    
    public static MinMax max(final float max) {
        return new MinMax(null, max);
    }
    
    public static MinMax range(final float min, final float max) {
        return new MinMax(min, max);
    }
    
    public static MinMax unrestricted() {
        return MinMax.UNRESTRICTED;
    }
    
    public static MinMax positive() {
        return MinMax.POSITIVE;
    }
    
    public Float getMin() {
        return this.min;
    }
    
    public Float getMax() {
        return this.max;
    }
    
    public boolean isValid(final float value) {
        return (this.min == null || value >= this.min) && (this.max == null || value <= this.max);
    }
    
    static {
        UNRESTRICTED = new MinMax(null, null);
        POSITIVE = new MinMax(0.0f, null);
    }
}
