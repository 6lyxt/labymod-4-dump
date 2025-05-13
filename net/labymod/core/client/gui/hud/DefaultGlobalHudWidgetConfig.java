// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud;

import java.util.HashMap;
import com.google.gson.JsonObject;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.annotation.ConfigPath;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.configuration.loader.Config;

@ConfigName("$PROFILE")
@ConfigPath("hud")
public class DefaultGlobalHudWidgetConfig extends Config implements GlobalHudWidgetConfig
{
    @CustomTranslation("labymod.hudWidget.global.scale")
    @SliderWidget.SliderSetting(min = 0.5f, max = 1.5f, steps = 0.1f)
    @Exclude
    private final ConfigProperty<Float> scale;
    @SettingSection("color")
    @CustomTranslation("labymod.hudWidget.text.labelColor")
    @ColorPickerWidget.ColorPickerSetting
    private final ConfigProperty<Color> labelColor;
    @CustomTranslation("labymod.hudWidget.text.bracketColor")
    @ColorPickerWidget.ColorPickerSetting
    private final ConfigProperty<Color> bracketColor;
    @CustomTranslation("labymod.hudWidget.text.valueColor")
    @ColorPickerWidget.ColorPickerSetting
    private final ConfigProperty<Color> valueColor;
    @CustomTranslation("labymod.hudWidget.text.lineHeight")
    @SliderWidget.SliderSetting(min = 50.0f, max = 150.0f, steps = 10.0f)
    private final ConfigProperty<Integer> lineHeight;
    @SettingSection("miscellaneous")
    @CustomTranslation("labymod.hudWidget.text.formatting")
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<Formatting> formatting;
    @CustomTranslation("labymod.hudWidget.background")
    private BackgroundConfig background;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> smoothMovement;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> itemGravity;
    @Exclude
    private Map<String, JsonObject> configs;
    
    public DefaultGlobalHudWidgetConfig() {
        this.scale = new ConfigProperty<Float>(1.0f);
        this.labelColor = new ConfigProperty<Color>(Color.of("#FFAA00"));
        this.bracketColor = new ConfigProperty<Color>(Color.of("#AAAAAA"));
        this.valueColor = new ConfigProperty<Color>(Color.WHITE);
        this.lineHeight = new ConfigProperty<Integer>(100);
        this.formatting = ConfigProperty.createEnum(Formatting.SQUARE_BRACKETS);
        this.background = new BackgroundConfig();
        this.smoothMovement = new ConfigProperty<Boolean>(true);
        this.itemGravity = new ConfigProperty<Boolean>(true);
        this.configs = new HashMap<String, JsonObject>();
    }
    
    @Override
    public ConfigProperty<Float> scale() {
        return this.scale;
    }
    
    @Override
    public ConfigProperty<Color> valueColor() {
        return this.valueColor;
    }
    
    @Override
    public ConfigProperty<Color> bracketColor() {
        return this.bracketColor;
    }
    
    @Override
    public ConfigProperty<Color> labelColor() {
        return this.labelColor;
    }
    
    @Override
    public ConfigProperty<Integer> lineHeight() {
        return this.lineHeight;
    }
    
    @Override
    public ConfigProperty<Formatting> formatting() {
        return this.formatting;
    }
    
    @Override
    public BackgroundConfig background() {
        return this.background;
    }
    
    @Override
    public ConfigProperty<Boolean> smoothMovement() {
        return this.smoothMovement;
    }
    
    @Override
    public ConfigProperty<Boolean> itemGravity() {
        return this.itemGravity;
    }
    
    public Map<String, JsonObject> getConfigs() {
        return this.configs;
    }
    
    public void setConfigs(final Map<String, JsonObject> configs) {
        this.configs = configs;
    }
}
