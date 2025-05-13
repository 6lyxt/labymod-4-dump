// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.mojang.blaze3d.vertex;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import java.nio.ByteBuffer;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

@Mixin({ edy.class })
@Implements({ @Interface(iface = BufferConsumer.class, remap = Interface.Remap.NONE, prefix = "bufferConsumer$") })
public abstract class MixinBufferBuilder implements BufferConsumer
{
    @Shadow
    private ByteBuffer h;
    @Shadow
    private int k;
    
    @Shadow
    public abstract void shadow$e();
    
    @Shadow
    public abstract void a(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final int p9, final int p10, final float p11, final float p12, final float p13);
    
    @Intrinsic
    public BufferConsumer bufferConsumer$pos(final float x, final float y, final float z) {
        ((edy)this).a((double)x, (double)y, (double)z);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final int red, final int green, final int blue, final int alpha) {
        ((edy)this).a(red, green, blue, alpha);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final float red, final float green, final float blue, final float alpha) {
        ((edy)this).a(red, green, blue, alpha);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$uv(final float u, final float v) {
        ((edy)this).a(u, v);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$packedOverlay(final int packedOverlay) {
        ((edy)this).c(packedOverlay);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$light(final int packedLight) {
        ((edy)this).b(packedLight);
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$normal(final float x, final float y, final float z) {
        ((edy)this).b(x, y, z);
        return this;
    }
    
    @Intrinsic
    public void bufferConsumer$vertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        this.a(x, y, z, red, green, blue, alpha, u, v, packedOverlay, packedLight, normalX, normalY, normalZ);
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$endVertex() {
        this.shadow$e();
        return this;
    }
}
