// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;

@Mixin({ enx.class })
public class MixinAbstractSliderButton extends MixinAbstractWidget implements SliderButtonAccessor
{
    @Shadow
    protected double k;
    
    @Insert(method = { "renderWidget" }, at = @At("HEAD"), cancellable = true)
    public void render(final ehe poseStack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((enz)this).k());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Override
    public double getRawValue() {
        return this.k;
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
