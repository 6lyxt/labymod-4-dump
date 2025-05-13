// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.switchable;

import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.SwitchableHandler;

public class BooleanSwitchableHandler implements SwitchableHandler
{
    @Override
    public boolean isEnabled(final SettingElement setting, final Object value, final SwitchableInfo info) {
        return value instanceof Boolean && (boolean)value == info.getRequiredValue().equalsIgnoreCase("true");
    }
}
