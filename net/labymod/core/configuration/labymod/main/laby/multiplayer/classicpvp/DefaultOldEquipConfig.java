// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.multiplayer.classicpvp;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultOldEquipConfig extends Config implements OldEquipConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 2)
    private final ConfigProperty<Boolean> enabled;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> hideIndicator;
    
    public DefaultOldEquipConfig() {
        this.enabled = new ConfigProperty<Boolean>(false);
        this.hideIndicator = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> hideIndicator() {
        return this.hideIndicator;
    }
}
