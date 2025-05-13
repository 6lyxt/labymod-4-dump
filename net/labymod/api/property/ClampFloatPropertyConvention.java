// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.property;

import net.labymod.api.util.math.MathHelper;

public class ClampFloatPropertyConvention implements PropertyConvention<Float>
{
    private final float minimum;
    private final float maximum;
    
    public ClampFloatPropertyConvention(final float minimum, final float maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    @Override
    public Float convention(final Float value) {
        return MathHelper.clamp(value, this.minimum, this.maximum);
    }
}
