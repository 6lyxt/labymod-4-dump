// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader;

import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.settings.Setting;
import java.util.List;

public interface ConfigAccessor
{
    List<Setting> toSettings();
    
    List<Setting> toSettings(final Setting p0);
    
    RootSettingRegistry asRegistry(final String p0);
}
