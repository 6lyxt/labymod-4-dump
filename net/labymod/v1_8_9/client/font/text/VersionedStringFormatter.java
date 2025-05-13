// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.font.text;

import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.render.font.text.StringIterator;
import net.labymod.api.client.render.font.VisualCharSequence;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.font.text.StringFormatter;

@Singleton
@Implements(StringFormatter.class)
public class VersionedStringFormatter implements StringFormatter
{
    @Override
    public VisualCharSequence getVisualOrder(final String text, final Style style) {
        return new VisualCharSequence(consumer -> StringIterator.iterateFormatted(text, style, false, consumer));
    }
    
    @Override
    public void visualFormat(final VisualCharSequence text, final boolean capitalize, final GlyphConsumer consumer) {
        text.accept((position, style, codePoint) -> consumer.acceptGlyph(position, style, capitalize ? Character.toUpperCase(codePoint) : codePoint));
    }
}
