// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.render.font.VisualCharSequence;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface StringFormatter
{
    VisualCharSequence getVisualOrder(final String p0, final Style p1);
    
    void visualFormat(final VisualCharSequence p0, final boolean p1, final GlyphConsumer p2);
}
