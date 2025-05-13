// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import javax.inject.Inject;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;

@Singleton
@Implements(ThemeRendererParser.class)
public class DefaultThemeRendererParser implements ThemeRendererParser
{
    private final ThemeService themeService;
    
    @Inject
    public DefaultThemeRendererParser(final ThemeService themeService) {
        this.themeService = themeService;
    }
    
    @Override
    public <T extends Widget> ThemeRenderer<T> parse(final String value) {
        String name = value;
        Theme theme = this.themeService.currentTheme();
        if (value.contains(":")) {
            final String[] entries = value.split(":", 2);
            name = entries[1];
            theme = this.themeService.getThemeByName(entries[0]);
        }
        return theme.getWidgetRendererByName(name);
    }
}
