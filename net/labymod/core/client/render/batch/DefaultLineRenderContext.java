// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.batch;

import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;
import javax.inject.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.LineRenderContext;

@Singleton
@Implements(LineRenderContext.class)
public class DefaultLineRenderContext extends DefaultRenderContext<LineRenderContext> implements LineRenderContext
{
    private BufferBuilder builder;
    private Stack stack;
    
    @Inject
    public DefaultLineRenderContext() {
    }
    
    @Override
    public LineRenderContext begin(final Stack stack, final Function<RenderPhase, BufferBuilder> builderFunction) {
        final RenderPhase line = RenderPhases.line();
        (this.builder = builderFunction.apply(line)).begin(line);
        this.stack = stack;
        return this;
    }
    
    @Override
    public LineRenderContext renderGradient(final float x, final float y, final float x1, final float y1, final int color, final int color1) {
        this.builder.vertex(this.stack, x, y, this.zOffset).color(color).next();
        this.builder.vertex(this.stack, x1, y1, this.zOffset).color(color1).next();
        return this;
    }
    
    @Override
    public LineRenderContext render(final float x, final float y, final float x1, final float y1, final float red, final float green, final float blue, final float alpha) {
        this.builder.vertex(this.stack, x, y, this.zOffset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, x1, y1, this.zOffset).color(red, green, blue, alpha).next();
        return this;
    }
    
    @Override
    public RenderContext<LineRenderContext> uploadToBuffer() {
        this.builder.uploadToBuffer();
        return this;
    }
}
