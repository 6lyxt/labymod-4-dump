// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import org.jetbrains.annotations.Nullable;

public interface MinecraftWidgetBounds
{
    @Nullable
    default MinecraftWidgetBounds self(final Object source) {
        if (source instanceof final MinecraftWidgetBounds widgetBounds) {
            return widgetBounds;
        }
        return null;
    }
    
    int getBoundsX();
    
    void setBoundsX(final int p0);
    
    int getBoundsY();
    
    void setBoundsY(final int p0);
    
    int getBoundsWidth();
    
    void setBoundsWidth(final int p0);
    
    int getBoundsHeight();
    
    void setBoundsHeight(final int p0);
}
