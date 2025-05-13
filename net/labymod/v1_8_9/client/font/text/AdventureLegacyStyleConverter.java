// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.font.text;

import net.labymod.api.Laby;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.v1_8_9.client.component.VersionedTextColor;
import java.util.Iterator;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;

public class AdventureLegacyStyleConverter
{
    private static final char LEGACY_COLOR_CODE = '§';
    
    public static String getColorCodes(final Style style) {
        if (style == null || style.isEmpty()) {
            return "§r";
        }
        final StringBuilder builder = new StringBuilder();
        if (style.getColor() != null) {
            builder.append('§').append(getTextColor(style.getColor()));
        }
        for (final TextDecoration decoration : TextDecoration.getValues()) {
            if (!style.hasDecoration(decoration)) {
                continue;
            }
            builder.append('§').append(getDecorationCharacter(decoration));
        }
        return builder.toString();
    }
    
    public static String getDecorationCodes(final Style style) {
        if (style == null || style.isEmpty()) {
            return "§r";
        }
        final StringBuilder builder = new StringBuilder();
        if (style.getColor() != null && style.getColor() instanceof VersionedTextColor && ((VersionedTextColor)style.getColor()).isNamed()) {
            builder.append('§').append(getTextColor(style.getColor()));
        }
        for (final TextDecoration decoration : TextDecoration.getValues()) {
            if (!style.hasDecoration(decoration)) {
                continue;
            }
            builder.append('§').append(getDecorationCharacter(decoration));
        }
        return builder.toString();
    }
    
    private static char getTextColor(final TextColor color) {
        return Laby.references().legacyChatFormatting().getColorCode(color);
    }
    
    private static char getDecorationCharacter(final TextDecoration decoration) {
        switch (decoration) {
            case OBFUSCATED: {
                return 'k';
            }
            case BOLD: {
                return 'l';
            }
            case STRIKETHROUGH: {
                return 'm';
            }
            case UNDERLINED: {
                return 'n';
            }
            case ITALIC: {
                return 'o';
            }
            default: {
                return 'r';
            }
        }
    }
}
