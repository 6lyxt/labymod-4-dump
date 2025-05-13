// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import javax.inject.Singleton;

@Singleton
public class SettingListener
{
    @Subscribe
    public void onSettingUpdate(final SettingUpdateEvent event) {
        final SettingElement setting = event.setting();
        if (setting == null) {
            return;
        }
        if (setting.getPath().equals("settings.appearance.darkLoadingScreen")) {
            if (!(event.getValue() instanceof Boolean)) {
                return;
            }
            final boolean value = event.getValue();
            Laby.labyAPI().minecraft().options().setEyeProtectionActive(value);
        }
    }
}
