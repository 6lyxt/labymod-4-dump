// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface UserIndicatorConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> showUserIndicatorInPlayerList();
    
    ConfigProperty<Boolean> showLabyModPlayerPercentageInTabList();
    
    ConfigProperty<Boolean> showCountryFlagInPlayerList();
}
