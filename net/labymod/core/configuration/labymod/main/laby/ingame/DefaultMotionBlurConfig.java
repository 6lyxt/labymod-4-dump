// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultMotionBlurConfig extends Config implements MotionBlurConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<MotionBlurType> motionBlurType;
    @SettingRequires(value = "motionBlurType", required = "LABYMOD")
    @SliderWidget.SliderSetting(min = 4.0f, max = 32.0f)
    private final ConfigProperty<Integer> blurQuality;
    @SliderWidget.SliderSetting(min = 0.0f, max = 100.0f)
    private final ConfigProperty<Float> blurStrength;
    
    public DefaultMotionBlurConfig() {
        this.enabled = ConfigProperty.create(false);
        this.motionBlurType = ConfigProperty.createEnum(MotionBlurType.LABYMOD);
        this.blurQuality = ConfigProperty.create(12);
        this.blurStrength = ConfigProperty.create(20.0f);
    }
    
    @Override
    public ConfigProperty<MotionBlurType> motionBlurType() {
        return this.motionBlurType;
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Integer> blurQuality() {
        return this.blurQuality;
    }
    
    @Override
    public ConfigProperty<Float> blurStrength() {
        return this.blurStrength;
    }
}
