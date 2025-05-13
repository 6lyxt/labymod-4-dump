// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.font;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_6.client.font.StringRenderOutputAccessor;

@Mixin({ fgr.b.class })
public class MixinStringRenderOutput implements StringRenderOutputAccessor
{
    @Mutable
    @Shadow
    @Final
    private boolean c;
    @Mutable
    @Shadow
    @Final
    private float d;
    @Mutable
    @Shadow
    @Final
    private float e;
    @Mutable
    @Shadow
    @Final
    private float f;
    @Mutable
    @Shadow
    @Final
    private float g;
    @Mutable
    @Shadow
    @Final
    private float h;
    @Mutable
    @Shadow
    @Final
    private Matrix4f i;
    
    @Override
    public void setModelViewMatrix(final Matrix4f modelViewMatrix) {
        this.i = modelViewMatrix;
    }
    
    @Override
    public void setColor(final int color, final boolean dropShadow) {
        this.c = dropShadow;
        this.d = (dropShadow ? 0.25f : 1.0f);
        this.e = (color >> 16 & 0xFF) / 255.0f * this.d;
        this.f = (color >> 8 & 0xFF) / 255.0f * this.d;
        this.g = (color & 0xFF) / 255.0f * this.d;
        this.h = (color >> 24 & 0xFF) / 255.0f;
    }
}
