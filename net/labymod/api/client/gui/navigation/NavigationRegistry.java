// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

@Referenceable
public interface NavigationRegistry extends Registry<NavigationElement<?>>
{
    @Nullable
    NavigationElement<?> getActiveScreenElement();
    
    void setIngameMenuActive();
    
    ScreenInstance createNavigation(final ScreenBaseNavigationElement<?> p0);
}
