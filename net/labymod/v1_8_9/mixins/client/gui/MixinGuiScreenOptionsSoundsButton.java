// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.v1_8_9.client.gui.GuiScreenOptionsSoundsAccessor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.gui.GuiSliderAccessor;

@Mixin(targets = { "net.minecraft.client.gui.GuiScreenOptionsSounds$Button" })
public abstract class MixinGuiScreenOptionsSoundsButton extends avs implements GuiSliderAccessor
{
    @Shadow
    @Final
    private axz q;
    @Final
    @Shadow
    private bpg r;
    @Shadow
    public float o;
    @Shadow
    public boolean p;
    @Shadow
    @Final
    private String s;
    
    public MixinGuiScreenOptionsSoundsButton(final int p_i1844_1_, final int p_i1844_2_, final int p_i1844_3_, final String p_i1844_4_) {
        super(p_i1844_1_, p_i1844_2_, p_i1844_3_, p_i1844_4_);
    }
    
    @Shadow
    protected abstract void b(final ave p0, final int p1, final int p2);
    
    public boolean isDragging() {
        return this.p;
    }
    
    public float getMinValue() {
        return 0.0f;
    }
    
    public float getMaxValue() {
        return 1.0f;
    }
    
    public float getValue() {
        return this.o;
    }
    
    public float getStep() {
        return 0.0f;
    }
    
    public void labymod$mouseDragged(final ave minecraft, final MutableMouse mouse) {
        if (this.m && this.p) {
            this.o = (mouse.getX() - (this.h + 4)) / (float)(this.f - 8);
            this.o = ns.a(this.o, 0.0f, 1.0f);
            ave.A().t.a(this.r, this.o);
            this.j = this.s + ": " + ((GuiScreenOptionsSoundsAccessor)this.q).labymod$getSoundVolume(this.r);
        }
    }
}
