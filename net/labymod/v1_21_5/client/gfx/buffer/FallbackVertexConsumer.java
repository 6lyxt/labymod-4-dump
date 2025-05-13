// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.buffer;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public class FallbackVertexConsumer implements BufferConsumer
{
    private final flg consumer;
    
    public FallbackVertexConsumer(final flg consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public BufferConsumer pos(final float x, final float y, final float z) {
        this.consumer.a(x, y, z);
        return this;
    }
    
    @Override
    public BufferConsumer color(final int argb) {
        this.consumer.a(argb);
        return this;
    }
    
    @Override
    public BufferConsumer color(final float red, final float green, final float blue, final float alpha) {
        this.consumer.a(red, green, blue, alpha);
        return this;
    }
    
    @Override
    public BufferConsumer color(final int red, final int green, final int blue, final int alpha) {
        this.consumer.a(red, green, blue, alpha);
        return this;
    }
    
    @Override
    public BufferConsumer packedColor(final int color) {
        this.consumer.a(color);
        return this;
    }
    
    @Override
    public BufferConsumer uv(final float u, final float v) {
        this.consumer.a(u, v);
        return this;
    }
    
    @Override
    public BufferConsumer normal(final float x, final float y, final float z) {
        this.consumer.b(x, y, z);
        return this;
    }
    
    @Override
    public BufferConsumer packedOverlay(final int packedOverlay) {
        this.consumer.b(packedOverlay);
        return this;
    }
    
    @Override
    public BufferConsumer light(final int packedLight) {
        this.consumer.c(packedLight);
        return this;
    }
    
    @Override
    public void vertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        final ColorFormat format = ColorFormat.ARGB32;
        final int argb = format.pack(red, green, blue, alpha);
        this.consumer.a(x, y, z, argb, u, v, packedOverlay, packedLight, normalX, normalY, normalZ);
    }
    
    @Override
    public BufferConsumer endVertex() {
        return this;
    }
}
