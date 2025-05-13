// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.components;

import net.labymod.core.client.accessor.gui.ProgressOptionAccessor;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eav.class })
public class MixinSliderButton extends MixinAbstractSliderButton
{
    @Final
    @Shadow
    private dyz c;
    
    @Override
    public float getMinValue() {
        return (float)this.c.b();
    }
    
    @Override
    public float getMaxValue() {
        return (float)this.c.c();
    }
    
    @Override
    public float getSteps() {
        return ((ProgressOptionAccessor)this.c).getSteps();
    }
}
