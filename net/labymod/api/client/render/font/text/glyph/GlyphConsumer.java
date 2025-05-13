// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text.glyph;

import net.labymod.api.client.component.format.Style;

@FunctionalInterface
public interface GlyphConsumer
{
    boolean acceptGlyph(final int p0, final Style p1, final int p2);
}
