// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Phase;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@Deprecated
@LabyEvent(background = true)
public class SettingUpdateEvent implements Event
{
    private final Phase phase;
    private final SettingElement setting;
    private Object value;
    
    public SettingUpdateEvent(final Phase phase, final SettingElement setting, final Object value) {
        this.phase = phase;
        this.setting = setting;
        this.value = value;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public SettingElement setting() {
        return this.setting;
    }
    
    public <T> T getValue() {
        return (T)this.value;
    }
    
    public <T> void setValue(final T value) {
        this.value = value;
    }
}
