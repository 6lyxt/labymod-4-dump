// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.util.function.FloatSupplier;
import net.labymod.api.util.math.vector.FloatVector4;

public class Fog
{
    private final FloatVector4 color;
    private FloatSupplier start;
    private FloatSupplier end;
    
    public Fog() {
        this(() -> 0.0f, () -> 1.0f, new FloatVector4());
    }
    
    public Fog(final FloatSupplier start, final FloatSupplier end, final FloatVector4 color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }
    
    public float getSuppliedStart() {
        return this.start.get();
    }
    
    public FloatSupplier getStart() {
        return this.start;
    }
    
    public void setStart(final FloatSupplier start) {
        this.start = start;
    }
    
    public float getSuppliedEnd() {
        return this.end.get();
    }
    
    public FloatSupplier getEnd() {
        return this.end;
    }
    
    public void setEnd(final FloatSupplier end) {
        this.end = end;
    }
    
    public FloatVector4 color() {
        return this.color;
    }
    
    public void updateColor(final float red, final float green, final float blue, final float alpha) {
        this.color.set(red, green, blue, alpha);
    }
    
    public void updateColor(final FloatVector4 color) {
        this.updateColor(color.getX(), color.getY(), color.getZ(), color.getW());
    }
}
