// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.UserIndicatorConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultUserIndicatorConfig extends Config implements UserIndicatorConfig
{
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 1, page = 1)
    private final ConfigProperty<Boolean> showUserIndicatorInPlayerList;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7, y = 1, page = 1)
    private final ConfigProperty<Boolean> showLabyModPlayerPercentageInTabList;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(y = 2, page = 1)
    private final ConfigProperty<Boolean> showCountryFlagInPlayerList;
    
    public DefaultUserIndicatorConfig() {
        this.showUserIndicatorInPlayerList = new ConfigProperty<Boolean>(true);
        this.showLabyModPlayerPercentageInTabList = new ConfigProperty<Boolean>(false);
        this.showCountryFlagInPlayerList = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> showUserIndicatorInPlayerList() {
        return this.showUserIndicatorInPlayerList;
    }
    
    @Override
    public ConfigProperty<Boolean> showLabyModPlayerPercentageInTabList() {
        return this.showLabyModPlayerPercentageInTabList;
    }
    
    @Override
    public ConfigProperty<Boolean> showCountryFlagInPlayerList() {
        return this.showCountryFlagInPlayerList;
    }
}
