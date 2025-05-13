// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.batch;

import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;
import javax.inject.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.TriangleRenderContext;

@Singleton
@Implements(TriangleRenderContext.class)
public class DefaultTriangleRenderContext extends DefaultRenderContext<TriangleRenderContext> implements TriangleRenderContext
{
    private BufferBuilder builder;
    private Stack stack;
    
    @Inject
    public DefaultTriangleRenderContext() {
    }
    
    @Override
    public TriangleRenderContext begin(final Stack stack, final boolean filled, final Function<RenderPhase, BufferBuilder> builderFunction) {
        this.stack = stack;
        final RenderPhase phase = RenderPhases.triangle(filled);
        (this.builder = builderFunction.apply(phase)).begin(phase);
        return this;
    }
    
    @Override
    public TriangleRenderContext begin(final Stack stack, final boolean filled, final float lineWidth, final Function<RenderPhase, BufferBuilder> builderFunction) {
        final RenderPhase phase = RenderPhases.triangle(filled, lineWidth);
        this.stack = stack;
        (this.builder = builderFunction.apply(phase)).begin(phase);
        return this;
    }
    
    @Override
    public TriangleRenderContext render(final float x, final float y, final float x1, final float y1, final float x2, final float y2, final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float alpha = colorFormat.normalizedAlpha(color);
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        this.builder.vertex(this.stack, x, y, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x1, y1, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x2, y2, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x, y, this.zOffset).color(red, green, blue, alpha).next();
        return this;
    }
    
    @Override
    public TriangleRenderContext render(final float x, final float y, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float alpha = colorFormat.normalizedAlpha(color);
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        this.builder.vertex(this.stack, x, y, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x1, y1, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x2, y2, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x3, y3, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x, y, this.zOffset).color(red, green, blue, alpha).next();
        return this;
    }
    
    @Override
    public TriangleRenderContext renderLine(final float x, final float y, final float x1, final float y1, final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float alpha = colorFormat.normalizedAlpha(color);
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        this.builder.vertex(this.stack, x, y, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x1, y1, this.zOffset).color(red, green, blue, alpha).next();
        return this;
    }
    
    @Override
    public RenderContext<TriangleRenderContext> uploadToBuffer() {
        this.builder.uploadToBuffer();
        return this;
    }
}
