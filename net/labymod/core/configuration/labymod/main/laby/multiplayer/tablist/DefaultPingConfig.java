// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.multiplayer.tablist;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultPingConfig extends Config implements PingConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 5, y = 6)
    private final ConfigProperty<Boolean> exact;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 6)
    private final ConfigProperty<Boolean> exactColored;
    
    public DefaultPingConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.exact = new ConfigProperty<Boolean>(false);
        this.exactColored = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> exact() {
        return this.exact;
    }
    
    @Override
    public ConfigProperty<Boolean> exactColored() {
        return this.exactColored;
    }
}
