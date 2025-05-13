// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy;

import net.labymod.api.property.Property;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.gui.screen.theme.fancy.eventlistener.FancyVariableThemeEventListener;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;

public class FancyThemeConfig extends ThemeConfig
{
    public static final ThemeUpdateEvent.Reason FONT_UPDATE_REASON;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fancyFont;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> ingameFancyFont;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fancySounds;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> activityTransitions;
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<HighQuality> blurQuality;
    @IntroducedIn("4.1.0")
    @ColorPickerWidget.ColorPickerSetting(alpha = true)
    private final ConfigProperty<Color> buttonColor;
    @IntroducedIn("4.1.0")
    @ColorPickerWidget.ColorPickerSetting(alpha = true)
    private final ConfigProperty<Color> accentColor;
    private transient boolean hasActiveShaderPack;
    
    public FancyThemeConfig() {
        this.fancyFont = new ConfigProperty<Object>(true).addChangeListener((type, oldValue, newValue) -> this.updateFonts());
        this.ingameFancyFont = new ConfigProperty<Object>(true).addChangeListener((type, oldValue, newValue) -> {
            this.updateFonts();
            Laby.references().textRendererProvider().forceMinecraftRenderer(false);
            return;
        });
        this.fancySounds = new ConfigProperty<Boolean>(true);
        this.activityTransitions = new ConfigProperty<Boolean>(true);
        this.blurQuality = ConfigProperty.createEnum(HighQuality.HIGH);
        this.buttonColor = ConfigProperty.create(Color.of(15001321, 30)).addChangeListener((type, oldValue, newValue) -> this.updateThemeVariables());
        this.accentColor = ConfigProperty.create(Color.of(813232, 255)).addChangeListener((type, oldValue, newValue) -> this.updateThemeVariables());
    }
    
    public ConfigProperty<Boolean> fancyFont() {
        return this.fancyFont;
    }
    
    public ConfigProperty<Boolean> ingameFancyFont() {
        return this.ingameFancyFont;
    }
    
    public boolean isIngameFancyFont() {
        final boolean hasActiveShaderPack = Laby.references().shaderPipeline().hasActiveShaderPack();
        if (this.hasActiveShaderPack != hasActiveShaderPack) {
            this.hasActiveShaderPack = hasActiveShaderPack;
            this.updateFonts();
        }
        return !hasActiveShaderPack && (!this.fancyFont().get() || this.ingameFancyFont().get());
    }
    
    public ConfigProperty<Boolean> fancySounds() {
        return this.fancySounds;
    }
    
    public ConfigProperty<Boolean> activityTransitions() {
        return this.activityTransitions;
    }
    
    public ConfigProperty<HighQuality> blurQuality() {
        return this.blurQuality;
    }
    
    public ConfigProperty<Color> buttonColor() {
        return this.buttonColor;
    }
    
    public ConfigProperty<Color> accentColor() {
        return this.accentColor;
    }
    
    private void updateThemeVariables() {
        final FancyTheme theme = (FancyTheme)Laby.labyAPI().themeService().getThemeByName("fancy");
        if (theme != null) {
            final FancyVariableThemeEventListener listener = theme.getVariableListener();
            if (listener != null) {
                listener.updateThemeVariables();
            }
        }
    }
    
    private void updateFonts() {
        final DefaultThemeService themeService = (DefaultThemeService)Laby.labyAPI().themeService();
        if (themeService.isInitialized()) {
            Laby.labyAPI().minecraft().executeNextTick(() -> {
                Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
                Laby.references().textRendererProvider().forceMinecraftRenderer(false);
                Laby.fireEvent(new ThemeUpdateEvent(themeService.currentTheme(), FancyThemeConfig.FONT_UPDATE_REASON));
                themeService.reloadActivities();
            });
        }
    }
    
    static {
        FONT_UPDATE_REASON = ThemeUpdateEvent.Reason.of("FONT_UPDATE", true);
    }
}
