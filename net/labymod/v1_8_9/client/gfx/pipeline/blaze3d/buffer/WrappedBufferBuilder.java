// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx.pipeline.blaze3d.buffer;

import java.util.List;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.v1_8_9.client.renderer.WorldRendererAccessor;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public class WrappedBufferBuilder implements BufferConsumer
{
    private final bfd worldRenderer;
    private bmu currentFormat;
    private bmv currentElement;
    private int currentElementIndex;
    
    public WrappedBufferBuilder(final int capacity) {
        this(new bfd(capacity));
    }
    
    public WrappedBufferBuilder(final bfd worldRenderer) {
        this.worldRenderer = worldRenderer;
    }
    
    public void begin(final DrawingMode mode, final bmu vertexFormat) {
        this.worldRenderer.a(mode.getId(), vertexFormat);
        this.currentFormat = vertexFormat;
        this.currentElementIndex = 0;
        this.currentElement = this.currentFormat.c(this.currentElementIndex);
    }
    
    public boolean building() {
        return ((WorldRendererAccessor)this.worldRenderer).building();
    }
    
    @Override
    public BufferConsumer pos(final float x, final float y, final float z) {
        if (this.currentElement != bms.m) {
            return this;
        }
        this.worldRenderer.b((double)x, (double)y, (double)z);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer color(final int argb) {
        if (this.currentElement != bms.n) {
            return this;
        }
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        this.worldRenderer.a(colorFormat.normalizedRed(argb), colorFormat.normalizedGreen(argb), colorFormat.normalizedBlue(argb), colorFormat.normalizedAlpha(argb));
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer color(final float red, final float green, final float blue, final float alpha) {
        if (this.currentElement != bms.n) {
            return this;
        }
        this.worldRenderer.a(red, green, blue, alpha);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer color(final int red, final int green, final int blue, final int alpha) {
        if (this.currentElement != bms.n) {
            bfl.c(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
            return this;
        }
        this.worldRenderer.b(red, green, blue, alpha);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer packedColor(final int color) {
        return super.packedColor(color);
    }
    
    @Override
    public BufferConsumer uv(final float u, final float v) {
        if (this.currentElement != bms.o) {
            return this;
        }
        this.worldRenderer.a((double)u, (double)v);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer normal(final float x, final float y, final float z) {
        if (this.currentElement != bms.q) {
            return this;
        }
        this.worldRenderer.c(x, y, z);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer packedOverlay(final int packedOverlay) {
        return this;
    }
    
    @Override
    public BufferConsumer light(final int packedLight) {
        if (this.currentElement != bms.o) {
            return this;
        }
        this.worldRenderer.a(packedLight & 0xFF, packedLight >> 16 & 0xFF);
        this.nextElement();
        return this;
    }
    
    private void nextElement() {
        final List<bmv> elements = this.currentFormat.h();
        this.currentElementIndex = (this.currentElementIndex + 1) % elements.size();
        this.currentElement = elements.get(this.currentElementIndex);
        if (this.currentElement.b() == bmv.b.g) {
            this.nextElement();
        }
    }
    
    @Override
    public BufferConsumer endVertex() {
        this.worldRenderer.d();
        return this;
    }
    
    public bfd getWorldRenderer() {
        return this.worldRenderer;
    }
}
