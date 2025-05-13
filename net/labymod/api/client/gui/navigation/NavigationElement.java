// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation;

import net.labymod.api.client.gui.screen.widget.Widget;

public interface NavigationElement<T extends Widget>
{
    String getWidgetId();
    
    default boolean isVisible() {
        return true;
    }
    
    T createWidget(final NavigationWrapper p0);
    
    @Deprecated
    default boolean shouldPlaySound() {
        return true;
    }
}
