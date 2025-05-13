// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultMenuBlurConfig extends Config implements MenuBlurConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f)
    private final ConfigProperty<Float> strength;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> pauseMenu;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> inventories;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> emoteWheel;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> sprayWheel;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> interactionWheel;
    
    public DefaultMenuBlurConfig() {
        this.enabled = ConfigProperty.create(false);
        this.strength = ConfigProperty.create(4.0f);
        this.pauseMenu = ConfigProperty.create(true);
        this.inventories = ConfigProperty.create(true);
        this.emoteWheel = ConfigProperty.create(true);
        this.sprayWheel = ConfigProperty.create(true);
        this.interactionWheel = ConfigProperty.create(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Float> strength() {
        return this.strength;
    }
    
    @Override
    public ConfigProperty<Boolean> pauseMenu() {
        return this.pauseMenu;
    }
    
    @Override
    public ConfigProperty<Boolean> inventories() {
        return this.inventories;
    }
    
    @Override
    public ConfigProperty<Boolean> emoteWheel() {
        return this.emoteWheel;
    }
    
    @Override
    public ConfigProperty<Boolean> sprayWheel() {
        return this.sprayWheel;
    }
    
    @Override
    public ConfigProperty<Boolean> interactionWheel() {
        return this.interactionWheel;
    }
}
