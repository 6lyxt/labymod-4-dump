// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.hover;

import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable(named = true)
public interface HoverBackgroundEffect
{
    float getPadding();
    
    void render(final Stack p0, final float p1, final float p2, final float p3, final float p4, final RenderableComponent p5);
    
    default void render(final Stack stack, final float x, final float y, final float width, final float height) {
        this.render(stack, x, y, width, height, null);
    }
    
    default float getScale() {
        return 1.0f;
    }
}
