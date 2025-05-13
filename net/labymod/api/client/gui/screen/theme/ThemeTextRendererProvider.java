// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.client.render.font.text.TextRenderer;

public interface ThemeTextRendererProvider
{
    TextRenderer textRenderer();
    
    void load();
    
    void unload();
    
    default void reload() {
        this.unload();
        this.load();
    }
    
    boolean isLoaded();
}
