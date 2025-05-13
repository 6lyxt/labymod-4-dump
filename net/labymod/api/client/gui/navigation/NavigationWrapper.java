// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation;

import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import org.jetbrains.annotations.NotNull;

public interface NavigationWrapper
{
    void reload();
    
    void updateElement(final NavigationElement<?> p0);
    
    @NotNull
    Object mostInnerScreen();
    
    void displayScreen(final ScreenBaseNavigationElement<?> p0);
    
    default boolean isActive(final ScreenBaseNavigationElement<?> element) {
        final Class<?> clazz = element.getScreen().mostInnerScreen().getClass();
        return this.mostInnerScreen().getClass().equals(clazz);
    }
}
