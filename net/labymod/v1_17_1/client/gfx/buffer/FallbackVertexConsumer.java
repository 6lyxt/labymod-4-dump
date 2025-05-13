// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.gfx.buffer;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public class FallbackVertexConsumer implements BufferConsumer
{
    private final dqp consumer;
    
    public FallbackVertexConsumer(final dqp consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public BufferConsumer pos(final float x, final float y, final float z) {
        this.consumer.a((double)x, (double)y, (double)z);
        return this;
    }
    
    @Override
    public BufferConsumer color(final int argb) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        this.consumer.a(colorFormat.normalizedRed(argb), colorFormat.normalizedGreen(argb), colorFormat.normalizedBlue(argb), colorFormat.normalizedAlpha(argb));
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
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        this.consumer.a(colorFormat.normalizedRed(color), colorFormat.normalizedGreen(color), colorFormat.normalizedBlue(color), colorFormat.normalizedAlpha(color));
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
        this.consumer.a(packedLight);
        return this;
    }
    
    public void a(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        this.consumer.a(x, y, z, red, green, blue, alpha, u, v, packedOverlay, packedLight, normalX, normalY, normalZ);
    }
    
    @Override
    public BufferConsumer endVertex() {
        this.consumer.c();
        return this;
    }
}
