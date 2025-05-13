// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultDynamicBackgroundConfig extends Config implements DynamicBackgroundConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 5, y = 2, page = 1)
    private final ConfigProperty<Boolean> enabled;
    @SpriteSlot(x = 5, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> shader;
    @SpriteSlot(x = 6, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> coloredLight;
    @SpriteSlot(x = 7, y = 3, page = 1)
    @SliderWidget.SliderSetting(min = 0.1f, max = 10.0f, steps = 0.1f)
    private final ConfigProperty<Float> brightness;
    @SpriteSlot(x = 0, y = 4, page = 1)
    @SliderWidget.SliderSetting(min = 0.0f, max = 10.0f)
    private final ConfigProperty<Float> blur;
    @SpriteSlot(x = 1, y = 4, page = 1)
    @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f)
    private final ConfigProperty<Float> resolution;
    @SpriteSlot(x = 2, y = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> limitFpsWhenUnfocused;
    @SpriteSlot(x = 3, y = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> animatedTextures;
    @SpriteSlot(x = 5, y = 5, page = 1)
    @SwitchWidget.SwitchSetting
    @Exclude
    private final ConfigProperty<Boolean> snowflakes;
    
    public DefaultDynamicBackgroundConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.shader = new ConfigProperty<Boolean>(true);
        this.coloredLight = new ConfigProperty<Boolean>(true);
        this.brightness = new ConfigProperty<Float>(2.0f);
        this.blur = new ConfigProperty<Float>(0.0f);
        this.resolution = new ConfigProperty<Float>(5.0f);
        this.limitFpsWhenUnfocused = new ConfigProperty<Boolean>(true);
        this.animatedTextures = new ConfigProperty<Boolean>(true);
        this.snowflakes = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> shader() {
        return this.shader;
    }
    
    @Override
    public ConfigProperty<Boolean> coloredLight() {
        return this.coloredLight;
    }
    
    @Override
    public ConfigProperty<Float> brightness() {
        return this.brightness;
    }
    
    @Override
    public ConfigProperty<Float> blur() {
        return this.blur;
    }
    
    @Override
    public ConfigProperty<Float> resolution() {
        return this.resolution;
    }
    
    @Override
    public ConfigProperty<Boolean> limitFpsWhenUnfocused() {
        return this.limitFpsWhenUnfocused;
    }
    
    @Override
    public ConfigProperty<Boolean> animatedTextures() {
        return this.animatedTextures;
    }
    
    @Override
    public ConfigProperty<Boolean> snowflakes() {
        return this.snowflakes;
    }
}
