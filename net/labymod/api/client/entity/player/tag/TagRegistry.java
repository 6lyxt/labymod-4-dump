// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag;

import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TagRegistry
{
    void render(final Stack p0, final Entity p1, final float p2);
    
    void render(final Stack p0, final Entity p1, final float p2, final TagType p3);
    
    void register(final String p0, final PositionType p1, final TagRenderer p2);
    
    void registerAfter(final String p0, final String p1, final PositionType p2, final TagRenderer p3);
    
    void registerBefore(final String p0, final String p1, final PositionType p2, final TagRenderer p3);
    
    void unregister(final String p0);
}
