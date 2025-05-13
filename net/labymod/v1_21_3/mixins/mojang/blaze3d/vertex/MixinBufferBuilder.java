// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.mojang.blaze3d.vertex;

import org.lwjgl.system.MemoryUtil;
import net.labymod.api.util.color.format.ColorFormat;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

@Mixin({ fgn.class })
@Implements({ @Interface(iface = BufferConsumer.class, remap = Interface.Remap.NONE, prefix = "bufferConsumer$") })
public abstract class MixinBufferBuilder implements BufferConsumer
{
    @Shadow
    private long e;
    @Shadow
    private int n;
    
    @Shadow
    public abstract void a(final float p0, final float p1, final float p2, final int p3, final float p4, final float p5, final int p6, final int p7, final float p8, final float p9, final float p10);
    
    @Shadow
    protected abstract long a(final fgy p0);
    
    @Shadow
    protected static void a(final long par1, final int par2) {
    }
    
    @Shadow
    protected abstract void f();
    
    @Intrinsic
    public BufferConsumer bufferConsumer$pos(final float x, final float y, final float z) {
        ((fgn)this).a(x, y, z);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final int red, final int green, final int blue, final int alpha) {
        ((fgn)this).a(red, green, blue, alpha);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final float red, final float green, final float blue, final float alpha) {
        ((fgn)this).a(red, green, blue, alpha);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$uv(final float u, final float v) {
        ((fgn)this).a(u, v);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$packedOverlay(final int packedOverlay) {
        ((fgn)this).b(packedOverlay);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$light(final int packedLight) {
        ((fgn)this).c(packedLight);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$normal(final float x, final float y, final float z) {
        ((fgn)this).b(x, y, z);
        return this;
    }
    
    @Intrinsic
    public void bufferConsumer$vertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        this.a(x, y, z, ColorFormat.ARGB32.pack(red, green, blue, alpha), u, v, packedOverlay, packedLight, normalX, normalY, normalZ);
    }
    
    @Override
    public BufferConsumer endVertex() {
        this.n = 0;
        return this;
    }
    
    @Override
    public BufferConsumer packedColor(final int color) {
        final long pointer = this.a(fgy.c);
        if (pointer != -1L) {
            a(pointer, color);
        }
        return this;
    }
    
    @Override
    public BufferConsumer putFloat(final float v0) {
        MemoryUtil.memPutFloat(this.e + 28L, v0);
        return this;
    }
    
    @Override
    public BufferConsumer putInt(final int v0) {
        MemoryUtil.memPutInt(this.e, v0);
        return this;
    }
}
