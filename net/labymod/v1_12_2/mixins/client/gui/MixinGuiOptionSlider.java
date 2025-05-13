// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_12_2.client.GameSettingsOptionsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;

@Mixin({ bjs.class })
public abstract class MixinGuiOptionSlider extends bja implements GuiSliderAccessor
{
    @Shadow
    @Final
    private bid.a q;
    @Shadow
    private float p;
    @Shadow
    public boolean o;
    
    public MixinGuiOptionSlider(final int a, final int b, final int c, final String d) {
        super(a, b, c, d);
    }
    
    public boolean isDragging() {
        return this.o;
    }
    
    public float getMinValue() {
        return ((GameSettingsOptionsAccessor)this.q).getMinValue();
    }
    
    public float getMaxValue() {
        return this.q.f();
    }
    
    public float getValue() {
        return this.q.d(this.p);
    }
    
    public float getStep() {
        return ((GameSettingsOptionsAccessor)this.q).getStep();
    }
    
    public void labymod$mouseDragged(final bib mc, final MutableMouse mouse) {
        if (this.m && this.o) {
            this.p = (mouse.getX() - (this.h + 4)) / (float)(this.f - 8);
            this.p = MathHelper.clamp(this.p, 0.0f, 1.0f);
            final float value = this.q.d(this.p);
            mc.t.a(this.q, value);
            this.p = this.q.c(value);
            this.j = mc.t.c(this.q);
        }
    }
}
