// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;

@Mixin({ bjf.class })
public abstract class MixinGuiSlider implements GuiSliderAccessor
{
    @Final
    @Shadow
    private float r;
    @Final
    @Shadow
    private float s;
    @Shadow
    public boolean o;
    
    @Shadow
    public abstract float c();
    
    @Override
    public boolean isDragging() {
        return this.o;
    }
    
    @Override
    public float getMinValue() {
        return this.r;
    }
    
    @Override
    public float getMaxValue() {
        return this.s;
    }
    
    @Override
    public float getValue() {
        return this.c();
    }
    
    @Override
    public float getStep() {
        return 0.0f;
    }
    
    @Override
    public void labymod$mouseDragged(final bib minecraft, final MutableMouse mouse) {
    }
}
