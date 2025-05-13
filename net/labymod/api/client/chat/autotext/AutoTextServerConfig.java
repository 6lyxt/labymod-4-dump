// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.autotext;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.Config;

public class AutoTextServerConfig extends Config
{
    @SwitchWidget.SwitchSetting
    private ConfigProperty<Boolean> enabled;
    @TextFieldWidget.TextFieldSetting
    @SettingRequires("enabled")
    private ConfigProperty<String> address;
    
    public AutoTextServerConfig() {
        this.enabled = new ConfigProperty<Boolean>(false);
        this.address = new ConfigProperty<String>("");
    }
    
    public AutoTextServerConfig(final boolean enabled, final String address) {
        this.enabled = new ConfigProperty<Boolean>(false);
        this.address = new ConfigProperty<String>("");
        this.enabled.set(enabled);
        this.address.set(address);
    }
    
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    public ConfigProperty<String> address() {
        return this.address;
    }
    
    public AutoTextServerConfig copy() {
        return new AutoTextServerConfig(this.enabled.get(), this.address.get());
    }
}
