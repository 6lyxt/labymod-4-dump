// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.batch;

import javax.inject.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.batch.LineRenderContext;
import net.labymod.api.client.render.batch.TriangleRenderContext;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.RenderContexts;

@Singleton
@Implements(RenderContexts.class)
public class DefaultRenderContexts implements RenderContexts
{
    private final RectangleRenderContext rectangleRenderContext;
    private final ResourceRenderContext resourceRenderContext;
    private final TriangleRenderContext triangleRenderContext;
    private final LineRenderContext lineRenderContext;
    private Stack currentStack;
    
    @Inject
    public DefaultRenderContexts(final ResourceRenderContext resourceRenderContext, final LineRenderContext lineRenderContext, final RectangleRenderContext rectangleRenderContext, final TriangleRenderContext triangleRenderContext) {
        this.lineRenderContext = lineRenderContext;
        this.rectangleRenderContext = rectangleRenderContext;
        this.resourceRenderContext = resourceRenderContext;
        this.triangleRenderContext = triangleRenderContext;
    }
    
    @Override
    public RectangleRenderContext rectangleRenderContext() {
        return this.rectangleRenderContext;
    }
    
    @Override
    public ResourceRenderContext resourceRenderContext() {
        return this.resourceRenderContext;
    }
    
    @Override
    public TriangleRenderContext triangleRenderContext() {
        return this.triangleRenderContext;
    }
    
    @Override
    public LineRenderContext lineRenderContext() {
        return this.lineRenderContext;
    }
    
    @Override
    public Stack currentStack() {
        return this.currentStack;
    }
    
    public void setCurrentStack(final Stack currentStack) {
        this.currentStack = currentStack;
    }
}
