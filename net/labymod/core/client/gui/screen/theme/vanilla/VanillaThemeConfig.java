// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla;

import net.labymod.api.property.Property;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;

public class VanillaThemeConfig extends ThemeConfig implements VanillaThemeConfigAccessor
{
    @VersionCompatibility("24w09a<*")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> freshUI;
    
    public VanillaThemeConfig() {
        this.freshUI = new ConfigProperty<Object>(true).addChangeListener((type, oldValue, newValue) -> {
            final DefaultThemeService themeService = (DefaultThemeService)Laby.labyAPI().themeService();
            themeService.reloadActivities();
        });
    }
    
    @Override
    public ConfigProperty<Boolean> freshUI() {
        return this.freshUI;
    }
}
