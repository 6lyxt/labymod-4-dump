// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.other;

import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface WindowConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> cleanWindowTitle();
    
    ConfigProperty<Boolean> borderlessWindow();
    
    ConfigProperty<Boolean> restoreWindowResolution();
    
    MicrosoftWindowConfig microsoftWindow();
}
