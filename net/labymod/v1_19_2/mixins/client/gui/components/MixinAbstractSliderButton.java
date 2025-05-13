// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.gui.components;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;

@Mixin({ ehm.class })
public class MixinAbstractSliderButton implements SliderButtonAccessor
{
    @Shadow
    protected double b;
    
    @Override
    public double getRawValue() {
        return this.b;
    }
    
    @Override
    public float getMinValue() {
        return 0.0f;
    }
    
    @Override
    public float getMaxValue() {
        return 1.0f;
    }
    
    @Override
    public float getSteps() {
        return 0.0f;
    }
}
