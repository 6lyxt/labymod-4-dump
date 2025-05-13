// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultHitboxConfig extends Config implements HitboxConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f, steps = 0.1f)
    private final ConfigProperty<Float> lineSize;
    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> eyeLineColor;
    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> eyeBoxColor;
    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> boxColor;
    
    public DefaultHitboxConfig() {
        this.enabled = new ConfigProperty<Boolean>(false);
        this.lineSize = new ConfigProperty<Float>(2.5f);
        this.eyeLineColor = new ConfigProperty<Color>(Color.of("#0000AA"));
        this.eyeBoxColor = new ConfigProperty<Color>(Color.of("#AA0000"));
        this.boxColor = new ConfigProperty<Color>(Color.WHITE);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Float> lineSize() {
        return this.lineSize;
    }
    
    @Override
    public ConfigProperty<Color> eyeLineColor() {
        return this.eyeLineColor;
    }
    
    @Override
    public ConfigProperty<Color> eyeBoxColor() {
        return this.eyeBoxColor;
    }
    
    @Override
    public ConfigProperty<Color> boxColor() {
        return this.boxColor;
    }
}
