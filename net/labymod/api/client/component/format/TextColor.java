// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format;

import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.component.ComponentService;

public interface TextColor
{
    default TextColor color(final int value) {
        return ComponentService.textColor(value);
    }
    
    default TextColor color(final int red, final int green, final int blue) {
        return ComponentService.textColor(ColorFormat.ARGB32.pack(red, green, blue));
    }
    
    int getValue();
    
    String serialize();
    
    Color color();
    
    @Deprecated
    default int value() {
        return this.getValue();
    }
}
