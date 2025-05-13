// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.hover;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffectRenderer;

@Singleton
@Implements(HoverBackgroundEffectRenderer.class)
public class DefaultHoverBackgroundEffectRenderer implements HoverBackgroundEffectRenderer
{
    private HoverBackgroundEffect hoverBackgroundEffect;
    private RenderableComponent renderableComponent;
    private float x;
    private float y;
    private float width;
    private float height;
    
    @Override
    public HoverBackgroundEffectRenderer hoverEffect(final HoverBackgroundEffect hoverBackgroundEffect) {
        this.hoverBackgroundEffect = hoverBackgroundEffect;
        return this;
    }
    
    @Override
    public HoverBackgroundEffectRenderer x(final float x) {
        this.x = x;
        return this;
    }
    
    @Override
    public HoverBackgroundEffectRenderer y(final float y) {
        this.y = y;
        return this;
    }
    
    @Override
    public HoverBackgroundEffectRenderer width(final float width) {
        this.width = width;
        return this;
    }
    
    @Override
    public HoverBackgroundEffectRenderer height(final float height) {
        this.height = height;
        return this;
    }
    
    @Override
    public HoverBackgroundEffectRenderer pos(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }
    
    @Override
    public HoverBackgroundEffectRenderer component(final RenderableComponent renderableComponent) {
        this.renderableComponent = renderableComponent;
        return this;
    }
    
    @Override
    public void render(final Stack stack) {
        if (this.hoverBackgroundEffect == null) {
            this.reset();
            return;
        }
        if ((this.width == 0.0f || this.height == 0.0f) && this.renderableComponent == null) {
            this.reset();
            return;
        }
        if (this.width == 0.0f) {
            this.width = this.renderableComponent.getWidth();
        }
        if (this.height == 0.0f) {
            this.height = this.renderableComponent.getHeight();
        }
        this.hoverBackgroundEffect.render(stack, this.x, this.y, this.width, this.height, this.renderableComponent);
        this.reset();
    }
    
    private void reset() {
        this.hoverBackgroundEffect = null;
        this.renderableComponent = null;
        this.x = 0.0f;
        this.y = 0.0f;
        this.width = 0.0f;
        this.height = 0.0f;
    }
}
