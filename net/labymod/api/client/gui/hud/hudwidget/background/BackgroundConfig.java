// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.background;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class BackgroundConfig extends Config
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @CustomTranslation("labymod.hudWidget.background.color")
    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> color;
    @CustomTranslation("labymod.hudWidget.background.margin")
    @SliderWidget.SliderSetting(min = 0.0f, max = 10.0f, steps = 0.5f)
    private final ConfigProperty<Float> margin;
    @CustomTranslation("labymod.hudWidget.background.padding")
    @SliderWidget.SliderSetting(min = 0.0f, max = 10.0f, steps = 0.5f)
    private final ConfigProperty<Float> padding;
    @CustomTranslation("labymod.hudWidget.background.roundness")
    @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
    private final ConfigProperty<Integer> roundness;
    
    public BackgroundConfig() {
        this.enabled = new ConfigProperty<Boolean>(false);
        this.color = new ConfigProperty<Color>(Color.of(0, 76));
        this.margin = new ConfigProperty<Float>(1.0f);
        this.padding = new ConfigProperty<Float>(4.0f);
        this.roundness = new ConfigProperty<Integer>(2);
    }
    
    public float getMargin() {
        return this.enabled.get() ? this.margin.get() : 0.0f;
    }
    
    public float getPadding() {
        return this.enabled.get() ? this.padding.get() : 0.0f;
    }
    
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    public ConfigProperty<Color> color() {
        return this.color;
    }
    
    public ConfigProperty<Float> margin() {
        return this.margin;
    }
    
    public ConfigProperty<Float> padding() {
        return this.padding;
    }
    
    public ConfigProperty<Integer> roundness() {
        return this.roundness;
    }
}
