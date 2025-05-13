// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class SettingResetEvent implements Event
{
    private final Phase phase;
    private final SettingElement setting;
    
    public SettingResetEvent(final Phase phase, final SettingElement setting) {
        this.phase = phase;
        this.setting = setting;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public SettingElement setting() {
        return this.setting;
    }
}
