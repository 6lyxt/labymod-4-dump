// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

import net.labymod.api.Laby;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.adventure.LegacyChatFormatting;

public class StringIterator
{
    private static final LegacyChatFormatting LEGACY_CHAT_FORMATTING;
    private static final char LEGACY_COLOR_CODE_PREFIX = '§';
    private static final int REPLACEMENT_CHAR = 65533;
    
    public static boolean iterate(final String text, final Style style, final GlyphConsumer consumer) {
        for (int textLength = text.length(), index = 0; index < textLength; ++index) {
            final char codePoint = text.charAt(index);
            if (Character.isHighSurrogate(codePoint)) {
                if (index + 1 >= textLength) {
                    if (!consumer.acceptGlyph(index, style, 65533)) {
                        return false;
                    }
                    break;
                }
                else {
                    final char nextCodePoint = text.charAt(index + 1);
                    if (Character.isLowSurrogate(nextCodePoint)) {
                        if (!consumer.acceptGlyph(index, style, Character.toCodePoint(codePoint, nextCodePoint))) {
                            return false;
                        }
                        ++index;
                    }
                    else if (!consumer.acceptGlyph(textLength, style, 65533)) {
                        return false;
                    }
                }
            }
            else if (!feedConsumer(style, consumer, index, codePoint)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean iterateFormatted(final String text, final Style style, final boolean capitalize, final GlyphConsumer consumer) {
        return iterateFormatted(text, 0, style, capitalize, consumer);
    }
    
    public static boolean iterateFormatted(final String text, final int start, final Style style, final boolean capitalize, final GlyphConsumer consumer) {
        return iterateFormatted(text, start, style, style, capitalize, consumer);
    }
    
    public static boolean iterateFormatted(final String text, final int start, final Style style, final Style otherStyle, final boolean capitalize, final GlyphConsumer consumer) {
        final int textLength = text.length();
        Style finalStyle = style;
        for (int index = start; index < textLength; ++index) {
            final char codePoint = text.charAt(index);
            if (codePoint == '§') {
                if (index + 1 >= textLength) {
                    break;
                }
                final char nextCodePoint = text.charAt(index + 1);
                final Style legacyChatFormatting = StringIterator.LEGACY_CHAT_FORMATTING.getStyle(nextCodePoint);
                if (legacyChatFormatting != null) {
                    finalStyle = (legacyChatFormatting.isEmpty() ? otherStyle : applyLegacyFormat(finalStyle, legacyChatFormatting));
                }
                ++index;
            }
            else if (Character.isHighSurrogate(codePoint)) {
                if (index + 1 >= textLength) {
                    if (!consumer.acceptGlyph(index, finalStyle, 65533)) {
                        return false;
                    }
                    break;
                }
                else {
                    final char nextGlyph = text.charAt(index + 1);
                    if (Character.isLowSurrogate(nextGlyph)) {
                        if (!consumer.acceptGlyph(index, finalStyle, shouldBeUpperCase(capitalize, Character.toCodePoint(codePoint, nextGlyph)))) {
                            return false;
                        }
                        ++index;
                    }
                    else if (!consumer.acceptGlyph(index, finalStyle, 65533)) {
                        return false;
                    }
                }
            }
            else if (!feedConsumer(finalStyle, consumer, index, shouldBeUpperCase(capitalize, codePoint))) {
                return false;
            }
        }
        return true;
    }
    
    private static char shouldBeUpperCase(final boolean capitalize, final char ch) {
        return capitalize ? Character.toUpperCase(ch) : ch;
    }
    
    private static int shouldBeUpperCase(final boolean capitalize, final int codePoint) {
        return capitalize ? Character.toUpperCase(codePoint) : codePoint;
    }
    
    public static boolean iterateBackwards(final String text, final Style style, final GlyphConsumer consumer) {
        final int textLength = text.length();
        for (int index = textLength - 1; index >= 0; --index) {
            final char codePoint = text.charAt(index);
            if (Character.isLowSurrogate(codePoint)) {
                if (index - 1 < 0) {
                    if (!consumer.acceptGlyph(0, style, 65533)) {
                        return false;
                    }
                    break;
                }
                else {
                    final char nextCodePoint = text.charAt(index - 1);
                    if (Character.isHighSurrogate(nextCodePoint)) {
                        --index;
                        if (!consumer.acceptGlyph(index, style, Character.toCodePoint(nextCodePoint, codePoint))) {
                            return false;
                        }
                    }
                    else if (!consumer.acceptGlyph(index, style, 65533)) {
                        return false;
                    }
                }
            }
            else if (!feedConsumer(style, consumer, index, codePoint)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean feedConsumer(final Style style, final GlyphConsumer consumer, final int glyphPosition, final char glyph) {
        return Character.isSurrogate(glyph) ? consumer.acceptGlyph(glyphPosition, style, 65533) : consumer.acceptGlyph(glyphPosition, style, glyph);
    }
    
    private static Style applyLegacyFormat(Style source, final Style other) {
        if (other.isEmpty()) {
            return Style.EMPTY;
        }
        final List<TextDecoration> values = TextDecoration.getValues();
        final int size = values.size();
        int count = 0;
        for (final TextDecoration decoration : values) {
            if (!other.hasDecoration(decoration)) {
                ++count;
            }
            else {
                source = source.decorate(decoration);
            }
        }
        if (count == size) {
            for (final TextDecoration decoration : values) {
                source = source.decoration(decoration, false);
            }
        }
        if (other.getColor() != null) {
            source = source.color(other.getColor());
        }
        return source;
    }
    
    static {
        LEGACY_CHAT_FORMATTING = Laby.references().legacyChatFormatting();
    }
}
