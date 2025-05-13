// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.other;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultDiscordConfig extends Config implements DiscordConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 7)
    private final ConfigProperty<Boolean> enabled;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7, y = 7)
    private final ConfigProperty<Boolean> showServerAddress;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 1, page = 1)
    private final ConfigProperty<Boolean> displayAccount;
    
    public DefaultDiscordConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.showServerAddress = new ConfigProperty<Boolean>(true);
        this.displayAccount = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> showServerAddress() {
        return this.showServerAddress;
    }
    
    @Override
    public ConfigProperty<Boolean> displayAccount() {
        return this.displayAccount;
    }
}
