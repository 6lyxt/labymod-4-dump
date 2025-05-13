// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.core.configuration.labymod.main.laby.appearance.DefaultTitleScreenConfig;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultMenuBlurConfig;
import net.labymod.core.configuration.labymod.main.laby.appearance.DefaultNavigationConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.core.configuration.labymod.main.laby.appearance.DefaultDynamicBackgroundConfig;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.AdvancedSelectionWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultAppearanceConfig extends Config implements AppearanceConfig
{
    @AdvancedSelectionWidget.AdvancedSelectionSetting
    @SpriteSlot
    private final ConfigProperty<String> theme;
    @SpriteSlot(x = 6, y = 2, page = 1)
    private DefaultDynamicBackgroundConfig dynamicBackground;
    @SettingSection("loadingScreen")
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.13<*")
    @SpriteSlot(x = 1)
    private final ConfigProperty<Boolean> darkLoadingScreen;
    @SettingRequires(value = "dynamicBackground", invert = true)
    @DropdownWidget.DropdownSetting
    @VersionCompatibility("1.13<*")
    @SpriteSlot(x = 2)
    private final ConfigProperty<FadeOutAnimationType> fadeOutAnimation;
    @SettingSection("menus")
    @SpriteSlot(x = 6)
    private DefaultNavigationConfig navigation;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 3)
    private final ConfigProperty<Boolean> hideMenuBackground;
    @IntroducedIn("4.1.0")
    @SpriteSlot(x = 3, y = 5, page = 1)
    private final DefaultMenuBlurConfig menuBlur;
    @SpriteSlot(x = 3, y = 3, page = 1)
    private DefaultTitleScreenConfig customTitleScreen;
    @SpriteSlot(x = 4, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fixedTooltips;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 3, y = 7)
    private final ConfigProperty<Boolean> replaceSkinCustomization;
    @VersionCompatibility("1.13<*")
    @SpriteSlot(x = 4)
    private final ConfigProperty<Boolean> cleanWindowTitle;
    
    public DefaultAppearanceConfig() {
        this.theme = new ConfigProperty<String>("fancy");
        this.dynamicBackground = new DefaultDynamicBackgroundConfig();
        this.darkLoadingScreen = new ConfigProperty<Boolean>(false);
        this.fadeOutAnimation = new ConfigProperty<FadeOutAnimationType>(FadeOutAnimationType.FADING);
        this.navigation = new DefaultNavigationConfig();
        this.hideMenuBackground = new ConfigProperty<Boolean>(false);
        this.menuBlur = new DefaultMenuBlurConfig();
        this.customTitleScreen = new DefaultTitleScreenConfig();
        this.fixedTooltips = new ConfigProperty<Boolean>(false);
        this.replaceSkinCustomization = ConfigProperty.create(true);
        this.cleanWindowTitle = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<String> theme() {
        return this.theme;
    }
    
    @Override
    public DynamicBackgroundConfig dynamicBackground() {
        return this.dynamicBackground;
    }
    
    @Override
    public ConfigProperty<Boolean> darkLoadingScreen() {
        return this.darkLoadingScreen;
    }
    
    @Override
    public ConfigProperty<FadeOutAnimationType> fadeOutAnimation() {
        return this.fadeOutAnimation;
    }
    
    @Override
    public ConfigProperty<Boolean> hideMenuBackground() {
        return this.hideMenuBackground;
    }
    
    @Override
    public ConfigProperty<Boolean> cleanWindowTitle() {
        return this.cleanWindowTitle;
    }
    
    @Override
    public ConfigProperty<Boolean> fixedTooltips() {
        return this.fixedTooltips;
    }
    
    @Override
    public ConfigProperty<HighQuality> blurQuality() {
        return null;
    }
    
    @Override
    public NavigationConfig navigation() {
        return this.navigation;
    }
    
    @Override
    public TitleScreenConfig titleScreen() {
        return this.customTitleScreen;
    }
    
    @Override
    public MenuBlurConfig menuBlur() {
        return this.menuBlur;
    }
    
    @Override
    public ConfigProperty<Boolean> replaceSkinCustomization() {
        return this.replaceSkinCustomization;
    }
    
    @Override
    public int getConfigVersion() {
        return 2;
    }
}
