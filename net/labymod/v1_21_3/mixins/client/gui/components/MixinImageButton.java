// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;

@Mixin({ fos.class })
public abstract class MixinImageButton extends MixinAbstractButton implements ImageButtonAccessor
{
    @Shadow
    @Final
    protected fpt a;
    
    @Insert(method = { "renderWidget" }, at = @At("HEAD"), cancellable = true)
    @Override
    public void render(final fns graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((fod)this).z());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)graphics.c()).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Override
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation)this.a.a(this.C(), this.B());
    }
    
    @Override
    public int getXTexStart() {
        return this.D();
    }
    
    @Override
    public int getYTexStart() {
        return this.E();
    }
    
    @Override
    public int getYDiffTex() {
        return 0;
    }
    
    @Override
    public int getTextureWidth() {
        return this.g;
    }
    
    @Override
    public int getTextureHeight() {
        return this.h;
    }
}
