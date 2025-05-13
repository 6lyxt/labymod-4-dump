// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.accessor;

import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.type.SettingElement;

public interface SettingAccessorFactory
{
    SettingAccessor create(final SettingElement p0, final SettingAccessorContext p1, final Config p2);
    
    boolean isAssignableFrom(final Class<?> p0);
}
