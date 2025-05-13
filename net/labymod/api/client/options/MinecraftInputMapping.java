// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.options;

import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.Key;

public interface MinecraftInputMapping
{
    default boolean isHiddenOrReplaced(final MinecraftInputMapping mapping) {
        return mapping.isHidden() || mapping.hasReplacement();
    }
    
    boolean isDown();
    
    String getName();
    
    String getCategory();
    
    @NotNull
    Key key();
    
    boolean isMouse();
    
    void unpress();
    
    void press();
    
    int getKeyCode();
    
    boolean isActuallyDown();
    
    boolean isHidden();
    
    Widget getReplacement();
    
    boolean hasReplacement();
    
    default void addCategory(final String category) {
    }
}
