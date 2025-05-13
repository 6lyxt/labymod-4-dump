// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;

public enum VerticalAlignment
{
    TOP, 
    CENTER, 
    BOTTOM;
    
    private static final VerticalAlignment[] VALUES;
    
    public VerticalAlignment opposite() {
        switch (this.ordinal()) {
            case 0: {
                return VerticalAlignment.TOP;
            }
            case 2: {
                return VerticalAlignment.BOTTOM;
            }
            case 1: {
                return VerticalAlignment.CENTER;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
            }
        }
    }
    
    public static VerticalAlignment of(final WidgetAlignment alignment) {
        return (alignment == WidgetAlignment.BOTTOM) ? VerticalAlignment.BOTTOM : ((alignment == WidgetAlignment.CENTER) ? VerticalAlignment.CENTER : VerticalAlignment.TOP);
    }
    
    @Nullable
    public static VerticalAlignment of(final String value) {
        if (value == null) {
            return null;
        }
        for (final VerticalAlignment alignment : VerticalAlignment.VALUES) {
            if (value.equals(alignment.name())) {
                return alignment;
            }
        }
        return null;
    }
    
    static {
        VALUES = values();
    }
}
