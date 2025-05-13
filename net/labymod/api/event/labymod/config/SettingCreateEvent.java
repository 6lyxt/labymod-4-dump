// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.event.Event;

public class SettingCreateEvent implements Event
{
    private final Setting setting;
    
    public SettingCreateEvent(final Setting setting) {
        this.setting = setting;
    }
    
    public Setting setting() {
        return this.setting;
    }
}
