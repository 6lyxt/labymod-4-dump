// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true)
public class SettingInitializeEvent implements Event
{
    private final Setting setting;
    
    public SettingInitializeEvent(final Setting setting) {
        this.setting = setting;
    }
    
    public Setting setting() {
        return this.setting;
    }
}
