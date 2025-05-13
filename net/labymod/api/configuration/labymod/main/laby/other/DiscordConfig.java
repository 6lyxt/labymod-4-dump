// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.other;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface DiscordConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Boolean> showServerAddress();
    
    ConfigProperty<Boolean> displayAccount();
}
