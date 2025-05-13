// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.tablist.DefaultPingConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultTabListConfig extends Config implements TabListConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> backgroundColor;
    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> foregroundColor;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> serverBanner;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> labyModPercentage;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> labyModBadge;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showHeads;
    private DefaultPingConfig ping;
    
    public DefaultTabListConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.backgroundColor = new ConfigProperty<Color>(Color.of(Integer.MIN_VALUE));
        this.foregroundColor = new ConfigProperty<Color>(Color.of(553648127));
        this.serverBanner = new ConfigProperty<Boolean>(true);
        this.labyModPercentage = new ConfigProperty<Boolean>(false);
        this.labyModBadge = new ConfigProperty<Boolean>(true);
        this.showHeads = new ConfigProperty<Boolean>(true);
        this.ping = new DefaultPingConfig();
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Color> backgroundColor() {
        return this.backgroundColor;
    }
    
    @Override
    public ConfigProperty<Color> foregroundColor() {
        return this.foregroundColor;
    }
    
    @Override
    public ConfigProperty<Boolean> serverBanner() {
        return this.serverBanner;
    }
    
    @Override
    public ConfigProperty<Boolean> labyModPercentage() {
        return this.labyModPercentage;
    }
    
    @Override
    public ConfigProperty<Boolean> labyModBadge() {
        return this.labyModBadge;
    }
    
    @Override
    public ConfigProperty<Boolean> showHeads() {
        return this.showHeads;
    }
    
    @Override
    public PingConfig ping() {
        return this.ping;
    }
}
