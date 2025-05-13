// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.hover;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface HoverBackgroundEffectRenderer
{
    HoverBackgroundEffectRenderer hoverEffect(final HoverBackgroundEffect p0);
    
    HoverBackgroundEffectRenderer x(final float p0);
    
    HoverBackgroundEffectRenderer y(final float p0);
    
    HoverBackgroundEffectRenderer width(final float p0);
    
    HoverBackgroundEffectRenderer height(final float p0);
    
    HoverBackgroundEffectRenderer pos(final float p0, final float p1, final float p2, final float p3);
    
    HoverBackgroundEffectRenderer component(final RenderableComponent p0);
    
    void render(final Stack p0);
}
