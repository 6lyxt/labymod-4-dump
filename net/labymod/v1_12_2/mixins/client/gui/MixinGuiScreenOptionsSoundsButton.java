// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;

@Mixin(targets = { "net/minecraft/client/gui/GuiScreenOptionsSounds$Button" })
public abstract class MixinGuiScreenOptionsSoundsButton extends bja implements GuiSliderAccessor
{
    @Shadow
    public float o;
    @Shadow
    public boolean p;
    @Final
    @Shadow
    private qg r;
    
    public MixinGuiScreenOptionsSoundsButton(final int p_i1844_1_, final int p_i1844_2_, final int p_i1844_3_, final String p_i1844_4_) {
        super(p_i1844_1_, p_i1844_2_, p_i1844_3_, p_i1844_4_);
    }
    
    @Shadow
    public abstract boolean b(final bib p0, final int p1, final int p2);
    
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
    
    public void labymod$mouseDragged(final bib minecraft, final MutableMouse mouse) {
        if (this.p) {
            this.b(minecraft, mouse.getX(), this.i);
        }
    }
}
