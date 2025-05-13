// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.NotificationsConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultNotificationsConfig extends Config implements NotificationsConfig
{
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 2, page = 1)
    private final ConfigProperty<Boolean> enabled;
    @SliderWidget.SliderSetting(min = 3.0f, max = 10.0f)
    @SpriteSlot(x = 2, y = 2, page = 1)
    @SettingRequires("enabled")
    private final ConfigProperty<Integer> maxNotifications;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(y = 3, page = 1)
    private final ConfigProperty<Boolean> hideChatTrustToast;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7, y = 5, page = 1)
    private final ConfigProperty<Boolean> screenshot;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> screenshotSound;
    
    public DefaultNotificationsConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.maxNotifications = new ConfigProperty<Integer>(5);
        this.hideChatTrustToast = new ConfigProperty<Boolean>(true);
        this.screenshot = new ConfigProperty<Boolean>(true);
        this.screenshotSound = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Integer> maxNotifications() {
        return this.maxNotifications;
    }
    
    @Override
    public ConfigProperty<Boolean> hideChatTrustToast() {
        return this.hideChatTrustToast;
    }
    
    @Override
    public ConfigProperty<Boolean> screenshot() {
        return this.screenshot;
    }
    
    @Override
    public ConfigProperty<Boolean> screenshotSound() {
        return this.screenshotSound;
    }
}
