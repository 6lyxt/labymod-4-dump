// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.buffer;

import java.util.List;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.v1_12_2.client.renderer.WorldRendererAccessor;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public class WrappedBufferBuilder implements BufferConsumer
{
    private final buk bufferBuilder;
    private cea currentFormat;
    private ceb currentElement;
    private int currentElementIndex;
    
    public WrappedBufferBuilder(final int capacity) {
        this(new buk(capacity));
    }
    
    public WrappedBufferBuilder(final buk bufferBuilder) {
        this.bufferBuilder = bufferBuilder;
    }
    
    public void begin(final DrawingMode mode, final cea vertexFormat) {
        this.bufferBuilder.a(mode.getId(), vertexFormat);
        this.currentFormat = vertexFormat;
        this.currentElementIndex = 0;
        this.currentElement = this.currentFormat.c(this.currentElementIndex);
    }
    
    public boolean building() {
        return ((WorldRendererAccessor)this.bufferBuilder).building();
    }
    
    @Override
    public BufferConsumer pos(final float x, final float y, final float z) {
        if (this.currentElement != cdy.m) {
            return this;
        }
        this.bufferBuilder.b((double)x, (double)y, (double)z);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer color(final int argb) {
        if (this.currentElement != cdy.n) {
            return this;
        }
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        this.bufferBuilder.a(colorFormat.normalizedRed(argb), colorFormat.normalizedGreen(argb), colorFormat.normalizedBlue(argb), colorFormat.normalizedAlpha(argb));
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer color(final float red, final float green, final float blue, final float alpha) {
        if (this.currentElement != cdy.n) {
            return this;
        }
        this.bufferBuilder.a(red, green, blue, alpha);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer color(final int red, final int green, final int blue, final int alpha) {
        if (this.currentElement != cdy.n) {
            bus.c(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
            return this;
        }
        this.bufferBuilder.b(red, green, blue, alpha);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer packedColor(final int color) {
        return super.packedColor(color);
    }
    
    @Override
    public BufferConsumer uv(final float u, final float v) {
        if (this.currentElement != cdy.o) {
            return this;
        }
        this.bufferBuilder.a((double)u, (double)v);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer normal(final float x, final float y, final float z) {
        if (this.currentElement != cdy.q) {
            return this;
        }
        this.bufferBuilder.c(x, y, z);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer packedOverlay(final int packedOverlay) {
        return this;
    }
    
    @Override
    public BufferConsumer light(final int packedLight) {
        if (this.currentElement != cdy.o) {
            return this;
        }
        this.bufferBuilder.a(packedLight & 0xFF, packedLight >> 16 & 0xFF);
        this.nextElement();
        return this;
    }
    
    private void nextElement() {
        final List<ceb> elements = this.currentFormat.h();
        this.currentElementIndex = (this.currentElementIndex + 1) % elements.size();
        this.currentElement = elements.get(this.currentElementIndex);
        if (this.currentElement.b() == ceb.b.g) {
            this.nextElement();
        }
    }
    
    @Override
    public BufferConsumer endVertex() {
        this.bufferBuilder.d();
        return this;
    }
    
    public buk getBufferBuilder() {
        return this.bufferBuilder;
    }
}
