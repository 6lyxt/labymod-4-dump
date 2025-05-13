// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface HotbarRenderer
{
    HotbarRenderer mode(final RenderMode p0);
    
    HotbarRenderer pos(final float p0, final float p1);
    
    HotbarRenderer layout(final Layout p0);
    
    void render(final Stack p0);
    
    float getWidth();
    
    float getHeight();
    
    public enum Layout
    {
        VERTICAL, 
        HORIZONTAL;
    }
}
