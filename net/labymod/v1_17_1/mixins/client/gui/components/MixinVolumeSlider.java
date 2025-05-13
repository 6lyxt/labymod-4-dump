// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.components;

import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dxu.class })
public class MixinVolumeSlider extends MixinAbstractSliderButton
{
    @Override
    public float getMaxValue() {
        return 100.0f;
    }
    
    @Override
    public float getSteps() {
        return 1.0f;
    }
}
