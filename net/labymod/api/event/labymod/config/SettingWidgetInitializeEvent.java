// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.List;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true)
public class SettingWidgetInitializeEvent implements Event
{
    private final ParentScreen parentScreen;
    private final Setting holder;
    private final List<Widget> settings;
    
    public SettingWidgetInitializeEvent(final ParentScreen parentScreen, final Setting holder, final List<Widget> settings) {
        this.parentScreen = parentScreen;
        this.holder = holder;
        this.settings = settings;
    }
    
    public ParentScreen parentScreen() {
        return this.parentScreen;
    }
    
    public Setting holder() {
        return this.holder;
    }
    
    public List<Widget> getSettings() {
        return this.settings;
    }
}
