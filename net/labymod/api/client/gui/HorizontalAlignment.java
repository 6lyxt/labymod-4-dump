// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;

public enum HorizontalAlignment
{
    LEFT, 
    CENTER, 
    RIGHT, 
    NONE;
    
    public static HorizontalAlignment[] VALUES;
    
    public HorizontalAlignment opposite() {
        if (this == HorizontalAlignment.LEFT) {
            return HorizontalAlignment.RIGHT;
        }
        if (this == HorizontalAlignment.RIGHT) {
            return HorizontalAlignment.LEFT;
        }
        return HorizontalAlignment.CENTER;
    }
    
    public static HorizontalAlignment of(final WidgetAlignment alignment) {
        if (alignment == WidgetAlignment.CENTER) {
            return HorizontalAlignment.CENTER;
        }
        if (alignment == WidgetAlignment.RIGHT) {
            return HorizontalAlignment.RIGHT;
        }
        return HorizontalAlignment.LEFT;
    }
    
    @Nullable
    public static HorizontalAlignment of(final String value) {
        if (value == null) {
            return null;
        }
        for (final HorizontalAlignment alignment : HorizontalAlignment.VALUES) {
            if (value.equals(alignment.name())) {
                return alignment;
            }
        }
        return null;
    }
    
    static {
        HorizontalAlignment.VALUES = values();
    }
}
