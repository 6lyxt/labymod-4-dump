// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.mojang.blaze3d.vertex;

import net.labymod.api.util.color.format.ColorFormat;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

@Mixin({ fkz.class })
@Implements({ @Interface(iface = BufferConsumer.class, remap = Interface.Remap.NONE, prefix = "bufferConsumer$") })
public abstract class MixinBufferBuilder implements BufferConsumer
{
    @Shadow
    public abstract void a(final float p0, final float p1, final float p2, final int p3, final float p4, final float p5, final int p6, final int p7, final float p8, final float p9, final float p10);
    
    @Intrinsic
    public BufferConsumer bufferConsumer$pos(final float x, final float y, final float z) {
        ((fkz)this).a(x, y, z);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final int red, final int green, final int blue, final int alpha) {
        ((fkz)this).a(red, green, blue, alpha);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final float red, final float green, final float blue, final float alpha) {
        ((fkz)this).a(red, green, blue, alpha);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$uv(final float u, final float v) {
        ((fkz)this).a(u, v);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$packedOverlay(final int packedOverlay) {
        ((fkz)this).b(packedOverlay);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$light(final int packedLight) {
        ((fkz)this).c(packedLight);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$normal(final float x, final float y, final float z) {
        ((fkz)this).b(x, y, z);
        return this;
    }
    
    @Intrinsic
    public void bufferConsumer$vertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        this.a(x, y, z, ColorFormat.ARGB32.pack(red, green, blue, alpha), u, v, packedOverlay, packedLight, normalX, normalY, normalZ);
    }
}
