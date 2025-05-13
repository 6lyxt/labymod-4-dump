// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface NavigationConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> showSingleplayer();
    
    ConfigProperty<Boolean> showOptions();
    
    ConfigProperty<Boolean> rememberLastTab();
}
