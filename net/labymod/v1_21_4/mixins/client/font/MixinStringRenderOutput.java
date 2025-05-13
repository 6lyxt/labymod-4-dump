// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.font;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.font.StringRenderOutputAccessor;

@Mixin({ fod.b.class })
public class MixinStringRenderOutput implements StringRenderOutputAccessor
{
    @Mutable
    @Shadow
    @Final
    private Matrix4f f;
    @Mutable
    @Shadow
    @Final
    private int d;
    @Mutable
    @Shadow
    @Final
    private boolean c;
    
    @Override
    public void setModelViewMatrix(final Matrix4f modelViewMatrix) {
        this.f = modelViewMatrix;
    }
    
    @Override
    public void setColor(final int color, final boolean dropShadow) {
        this.c = dropShadow;
        this.d = color;
    }
}
