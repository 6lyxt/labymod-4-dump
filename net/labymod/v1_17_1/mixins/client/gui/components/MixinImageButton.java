// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.components;

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

@Mixin({ dxj.class })
public abstract class MixinImageButton extends MixinAbstractWidget implements ImageButtonAccessor
{
    @Shadow
    @Final
    private ww a;
    @Shadow
    @Final
    private int b;
    @Shadow
    @Final
    private int c;
    @Shadow
    @Final
    private int d;
    @Shadow
    @Final
    private int e;
    @Shadow
    @Final
    private int u;
    
    @Insert(method = { "renderButton" }, at = @At("HEAD"), cancellable = true)
    @Override
    public void render(final dql stack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((dwy)this).g());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)stack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Override
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation)this.a;
    }
    
    @Override
    public int getXTexStart() {
        return this.b;
    }
    
    @Override
    public int getYTexStart() {
        return this.c;
    }
    
    @Override
    public int getYDiffTex() {
        return this.d;
    }
    
    @Override
    public int getTextureWidth() {
        return this.e;
    }
    
    @Override
    public int getTextureHeight() {
        return this.u;
    }
}
