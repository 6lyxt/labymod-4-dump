// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.RendererBuilder;

public class DefaultRendererBuilder<T extends RendererBuilder<T>> implements RendererBuilder<T>
{
    protected float x;
    protected float y;
    protected int color;
    
    protected DefaultRendererBuilder() {
        this.resetBuilder();
    }
    
    @Override
    public T pos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return (T)this;
    }
    
    @Override
    public T color(final int color) {
        this.color = color;
        return (T)this;
    }
    
    @Override
    public void validateBuilder() {
    }
    
    @Override
    public void resetBuilder() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.color = -1;
    }
}
