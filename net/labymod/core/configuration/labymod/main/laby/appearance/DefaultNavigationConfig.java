// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultNavigationConfig extends Config implements NavigationConfig
{
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7)
    private final ConfigProperty<Boolean> showSingleplayer;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(y = 1)
    private final ConfigProperty<Boolean> showOptions;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7, y = 4, page = 1)
    private final ConfigProperty<Boolean> rememberLastTab;
    
    public DefaultNavigationConfig() {
        this.showSingleplayer = new ConfigProperty<Boolean>(false);
        this.showOptions = new ConfigProperty<Boolean>(true);
        this.rememberLastTab = new ConfigProperty<Boolean>(false);
    }
    
    @Override
    public ConfigProperty<Boolean> showSingleplayer() {
        return this.showSingleplayer;
    }
    
    @Override
    public ConfigProperty<Boolean> showOptions() {
        return this.showOptions;
    }
    
    @Override
    public ConfigProperty<Boolean> rememberLastTab() {
        return this.rememberLastTab;
    }
}
