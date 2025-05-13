// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.ingame.SprayConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultSprayConfig extends Config implements SprayConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> orderSprayClockwise;
    
    public DefaultSprayConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.orderSprayClockwise = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> orderSprayClockwise() {
        return this.orderSprayClockwise;
    }
}
