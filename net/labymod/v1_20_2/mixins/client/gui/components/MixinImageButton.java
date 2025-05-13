// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.gui.components;

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

@Mixin({ etc.class })
public abstract class MixinImageButton extends MixinAbstractButton implements ImageButtonAccessor
{
    @Shadow
    @Final
    protected euc a;
    
    @Insert(method = { "renderWidget" }, at = @At("HEAD"), cancellable = true)
    @Override
    public void render(final esf graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((eso)this).m());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)graphics.c()).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Override
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation)this.a.a(this.aD_(), this.o());
    }
    
    @Override
    public int getXTexStart() {
        return this.r();
    }
    
    @Override
    public int getYTexStart() {
        return this.t();
    }
    
    @Override
    public int getYDiffTex() {
        return 0;
    }
    
    @Override
    public int getTextureWidth() {
        return this.f;
    }
    
    @Override
    public int getTextureHeight() {
        return this.g;
    }
}
