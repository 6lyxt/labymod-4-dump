// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultTitleScreenConfig extends Config implements TitleScreenConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 2, y = 3, page = 1)
    private final ConfigProperty<Boolean> socialMediaLinks;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7, y = 6)
    private final ConfigProperty<Boolean> quickPlay;
    
    public DefaultTitleScreenConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.socialMediaLinks = new ConfigProperty<Boolean>(true);
        this.quickPlay = new ConfigProperty<Boolean>(false);
    }
    
    @Override
    public ConfigProperty<Boolean> custom() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> socialMediaLinks() {
        return this.socialMediaLinks;
    }
    
    @Override
    public ConfigProperty<Boolean> quickPlay() {
        return this.quickPlay;
    }
}
