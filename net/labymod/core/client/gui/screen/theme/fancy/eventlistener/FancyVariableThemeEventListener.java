// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.eventlistener;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.Color;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.api.client.gui.screen.theme.ThemeEventListener;

public class FancyVariableThemeEventListener implements ThemeEventListener
{
    private final FancyTheme theme;
    private final FancyThemeConfig themeConfig;
    
    public FancyVariableThemeEventListener(final FancyTheme theme) {
        this.theme = theme;
        this.themeConfig = Laby.labyAPI().themeService().getThemeConfig(theme, FancyThemeConfig.class);
    }
    
    @Subscribe
    public void onScreenOpen(final ScreenOpenEvent event) {
        this.updateThemeVariables();
    }
    
    @Subscribe
    public void onActivityOpen(final ActivityOpenEvent event) {
        this.updateThemeVariables();
    }
    
    @Subscribe
    public void onScreenDisplay(final ScreenDisplayEvent event) {
        this.updateThemeVariables();
    }
    
    public void updateThemeVariables() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final Window window = minecraft.minecraftWindow();
        final boolean ingame = minecraft.isIngame();
        window.setVariable("--background-color", this.getBackgroundColor(ingame));
        window.setVariable("--background-color-border", this.getBackgroundBorderColor(ingame));
        window.setVariable("--border-radius", 15);
        window.setVariable("--border-thickness", 4);
        final int primaryButtonColor = this.themeConfig.buttonColor().get().get();
        window.setVariable("--button-color", primaryButtonColor);
        window.setVariable("--button-color-border", modify(primaryButtonColor, -10, 1.33f));
        window.setVariable("--button-color-hover", modify(primaryButtonColor, -10, 1.66f));
        window.setVariable("--button-color-disabled", modify(primaryButtonColor, -20, 0.33f));
        window.setVariable("--button-color-active", modify(primaryButtonColor, 22, 1.66f));
        window.setVariable("--button-color-text", textColorForBackground(primaryButtonColor));
        final int accentButtonColor = this.themeConfig.accentColor().get().get();
        window.setVariable("--accent-button-color", accentButtonColor);
        window.setVariable("--accent-button-color-border", modify(accentButtonColor, 10, 1.33f));
        window.setVariable("--accent-button-color-hover", modify(accentButtonColor, -10, 1.66f));
        window.setVariable("--accent-button-color-disabled", modify(accentButtonColor, -20, 0.33f));
        window.setVariable("--accent-button-color-active", modify(accentButtonColor, 22, 1.66f));
        window.setVariable("--accent-button-color-text", textColorForBackground(accentButtonColor));
        window.setVariable("--accent-navigation-color", ColorFormat.ARGB32.mul(accentButtonColor, 3.5833f, 1.48f, 1.3125f, 1.0f));
    }
    
    public int getBackgroundColor(final boolean ingame) {
        return ColorFormat.ARGB32.pack(13, 14, 15, 150);
    }
    
    public int getBackgroundBorderColor(final boolean ingame) {
        return ColorFormat.ARGB32.pack(13, 14, 15, 170);
    }
    
    private static int modify(final int color, final int rgbDelta, final float alphaMultiplier) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        return colorFormat.mul(colorFormat.add(color, rgbDelta, rgbDelta, rgbDelta, 0), 1.0f, 1.0f, 1.0f, alphaMultiplier);
    }
    
    private static int textColorForBackground(final int backgroundColor) {
        final int red = backgroundColor >> 16 & 0xFF;
        final int green = backgroundColor >> 8 & 0xFF;
        final int blue = backgroundColor & 0xFF;
        final int alpha = backgroundColor >> 24 & 0xFF;
        final int brightness = (red * 299 + green * 587 + blue * 114) / 1000 * alpha / 255;
        return (brightness > 125) ? -16777216 : -921103;
    }
}
