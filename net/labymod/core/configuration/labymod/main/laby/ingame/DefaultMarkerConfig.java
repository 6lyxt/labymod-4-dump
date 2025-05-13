// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultMarkerConfig extends Config implements MarkerConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SliderWidget.SliderSetting(min = 2.0f, max = 10.0f)
    private final ConfigProperty<Integer> duration;
    
    public DefaultMarkerConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.duration = new ConfigProperty<Integer>(10);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Integer> duration() {
        return this.duration;
    }
}
