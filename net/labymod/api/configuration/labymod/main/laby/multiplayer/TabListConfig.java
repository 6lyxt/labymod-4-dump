// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface TabListConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Color> backgroundColor();
    
    ConfigProperty<Color> foregroundColor();
    
    ConfigProperty<Boolean> serverBanner();
    
    ConfigProperty<Boolean> labyModPercentage();
    
    ConfigProperty<Boolean> labyModBadge();
    
    ConfigProperty<Boolean> showHeads();
    
    PingConfig ping();
}
