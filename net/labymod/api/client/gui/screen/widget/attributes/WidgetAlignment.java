// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.client.gui.HorizontalAlignment;

public enum WidgetAlignment
{
    LEFT, 
    TOP, 
    CENTER, 
    RIGHT, 
    BOTTOM;
    
    public static WidgetAlignment of(final HorizontalAlignment alignment) {
        if (alignment == HorizontalAlignment.LEFT) {
            return WidgetAlignment.LEFT;
        }
        if (alignment == HorizontalAlignment.RIGHT) {
            return WidgetAlignment.RIGHT;
        }
        return WidgetAlignment.CENTER;
    }
}
