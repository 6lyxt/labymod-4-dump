// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;

public class TextHudWidgetConfig extends BackgroundHudWidget.BackgroundHudWidgetConfig
{
    @CustomTranslation("labymod.hudWidget.text.customLabel")
    @TextFieldWidget.TextFieldSetting(maxLength = 32)
    private final ConfigProperty<String> customLabel;
    @SettingOrder(10)
    @CustomTranslation("labymod.hudWidget.text.labelColor")
    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "useGlobal", invert = true)
    private final ConfigProperty<Color> labelColor;
    @SettingOrder(10)
    @CustomTranslation("labymod.hudWidget.text.bracketColor")
    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "useGlobal", invert = true)
    private final ConfigProperty<Color> bracketColor;
    @SettingOrder(10)
    @CustomTranslation("labymod.hudWidget.text.valueColor")
    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "useGlobal", invert = true)
    private final ConfigProperty<Color> valueColor;
    @SettingOrder(10)
    @CustomTranslation("labymod.hudWidget.text.lineHeight")
    @SliderWidget.SliderSetting(min = 50.0f, max = 150.0f, steps = 10.0f)
    @SettingRequires(value = "useGlobal", invert = true)
    private final ConfigProperty<Integer> lineHeight;
    @SettingOrder(10)
    @CustomTranslation("labymod.hudWidget.text.formatting")
    @DropdownWidget.DropdownSetting
    @SettingRequires(value = "useGlobal", invert = true)
    private final ConfigProperty<Formatting> formatting;
    
    public TextHudWidgetConfig() {
        this.customLabel = new ConfigProperty<String>("");
        this.labelColor = new ConfigProperty<Color>(Color.of("#FFAA00"));
        this.bracketColor = new ConfigProperty<Color>(Color.of("#AAAAAA"));
        this.valueColor = new ConfigProperty<Color>(Color.WHITE);
        this.lineHeight = new ConfigProperty<Integer>(100);
        this.formatting = ConfigProperty.createEnum(Formatting.SQUARE_BRACKETS);
    }
    
    public ConfigProperty<String> customLabel() {
        return this.customLabel;
    }
    
    public ConfigProperty<Color> valueColor() {
        return this.property(GlobalHudWidgetConfig::valueColor, this.valueColor);
    }
    
    public ConfigProperty<Color> bracketColor() {
        return this.property(GlobalHudWidgetConfig::bracketColor, this.bracketColor);
    }
    
    public ConfigProperty<Color> labelColor() {
        return this.property(GlobalHudWidgetConfig::labelColor, this.labelColor);
    }
    
    public ConfigProperty<Integer> lineHeight() {
        return this.property(GlobalHudWidgetConfig::lineHeight, this.lineHeight);
    }
    
    public ConfigProperty<Formatting> formatting() {
        return this.property(GlobalHudWidgetConfig::formatting, this.formatting);
    }
    
    @Deprecated
    public ConfigProperty<Color> primaryColor() {
        return this.valueColor();
    }
    
    @Deprecated
    public ConfigProperty<Color> secondaryColor() {
        return this.bracketColor();
    }
    
    @Deprecated
    public ConfigProperty<Color> accentColor() {
        return this.labelColor();
    }
}
