// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import net.labymod.api.configuration.settings.type.SettingElement;

public interface SwitchableHandler
{
    boolean isEnabled(final SettingElement p0, final Object p1, final SwitchableInfo p2);
}
