// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.font;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.font.StringRenderOutputAccessor;

@Mixin({ fnq.b.class })
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
    private Matrix4f g;
    @Mutable
    @Shadow
    @Final
    private int e;
    
    @Override
    public void setModelViewMatrix(final Matrix4f modelViewMatrix) {
        this.g = modelViewMatrix;
    }
    
    @Override
    public void setColor(final int color, final boolean dropShadow) {
        this.c = dropShadow;
        this.d = (dropShadow ? 0.25f : 1.0f);
        this.e = ayp.a(color, this.d);
    }
}
