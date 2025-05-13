// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.component;

import java.util.Iterator;
import java.util.Locale;
import java.util.HashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum VersionedNamedTextColors
{
    BLACK(a.a, 0), 
    DARK_BLUE(a.b, 170), 
    DARK_GREEN(a.c, 43520), 
    DARK_AQUA(a.d, 43690), 
    DARK_RED(a.e, 11141120), 
    DARK_PURPLE(a.f, 11141290), 
    GOLD(a.g, 16755200), 
    GRAY(a.h, 11184810), 
    DARK_GRAY(a.i, 5592405), 
    BLUE(a.j, 5592575), 
    GREEN(a.k, 5635925), 
    AQUA(a.l, 5636095), 
    RED(a.m, 16733525), 
    LIGHT_PURPLE(a.n, 16733695), 
    YELLOW(a.o, 16777045), 
    WHITE(a.p, 16777215);
    
    private static final Set<VersionedTextColor> VALUES;
    private static final Map<String, VersionedTextColor> BY_NAME;
    private static final Map<Integer, VersionedTextColor> BY_VALUE;
    private static final Map<String, VersionedTextColor> BY_HEX;
    private static final Map<a, VersionedTextColor> BY_FORMATTING;
    private final VersionedTextColor textColor;
    
    private VersionedNamedTextColors(final a formatting, final int color) {
        this.textColor = new VersionedTextColor(formatting, color);
    }
    
    public VersionedTextColor textColor() {
        return this.textColor;
    }
    
    public static VersionedTextColor byName(final String name) {
        return VersionedNamedTextColors.BY_NAME.get(name);
    }
    
    public static VersionedTextColor byHex(final String hex) {
        return VersionedNamedTextColors.BY_HEX.get(hex);
    }
    
    public static VersionedTextColor byValue(final int value) {
        return VersionedNamedTextColors.BY_VALUE.get(value);
    }
    
    public static VersionedTextColor byFormatting(final a formatting) {
        if (formatting == null) {
            return null;
        }
        return VersionedNamedTextColors.BY_FORMATTING.get(formatting);
    }
    
    static {
        final Set<VersionedTextColor> values = new HashSet<VersionedTextColor>();
        for (final VersionedNamedTextColors value : values()) {
            values.add(value.textColor);
        }
        VALUES = Collections.unmodifiableSet((Set<? extends VersionedTextColor>)values);
        final Map<String, VersionedTextColor> byName = new HashMap<String, VersionedTextColor>(VersionedNamedTextColors.VALUES.size());
        final Map<Integer, VersionedTextColor> byValue = new HashMap<Integer, VersionedTextColor>(VersionedNamedTextColors.VALUES.size());
        final Map<String, VersionedTextColor> byHex = new HashMap<String, VersionedTextColor>(VersionedNamedTextColors.VALUES.size());
        final Map<a, VersionedTextColor> byFormatting = new HashMap<a, VersionedTextColor>(VersionedNamedTextColors.VALUES.size());
        for (final VersionedTextColor color : VersionedNamedTextColors.VALUES) {
            byName.put(color.getFormatting().y.toLowerCase(Locale.US), color);
            byValue.put(color.color().get(), color);
            byHex.put(color.getHex(), color);
            byFormatting.put(color.getFormatting(), color);
        }
        BY_NAME = Collections.unmodifiableMap((Map<? extends String, ? extends VersionedTextColor>)byName);
        BY_VALUE = Collections.unmodifiableMap((Map<? extends Integer, ? extends VersionedTextColor>)byValue);
        BY_HEX = Collections.unmodifiableMap((Map<? extends String, ? extends VersionedTextColor>)byHex);
        BY_FORMATTING = Collections.unmodifiableMap((Map<? extends a, ? extends VersionedTextColor>)byFormatting);
    }
}
