// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text.glyph;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public interface Glyph
{
    void render(final BufferConsumer p0, final Stack p1, final float p2, final float p3, final float p4, final GlyphStyle p5);
    
    float advance();
    
    float width();
    
    float height();
    
    void applyMetadata(final Int2ObjectMap<Object> p0);
}
