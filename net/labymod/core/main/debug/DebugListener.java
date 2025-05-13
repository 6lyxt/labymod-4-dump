// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.client.lifecycle.GameFpsLimitEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.event.client.input.KeyEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.api.LabyAPI;
import javax.inject.Singleton;

@Singleton
public final class DebugListener
{
    private final LabyAPI labyAPI;
    private final DefaultThemeService defaultThemeService;
    private boolean framerateLimit;
    
    @Inject
    public DebugListener() {
        this.labyAPI = Laby.labyAPI();
        this.defaultThemeService = (DefaultThemeService)this.labyAPI.themeService();
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        if (event.state() != KeyEvent.State.PRESS) {
            return;
        }
        final Key key = event.key();
        final boolean controlPressed = KeyHandler.isLeftControlDown();
        final boolean developmentEnvironment = this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment();
        final AdvancedConfig advanced = this.labyAPI.config().other().advanced();
        if (controlPressed && key == Key.L && advanced.refreshHotkey()) {
            this.labyAPI.refresh();
            return;
        }
        if (this.labyAPI.minecraft().isMouseLocked()) {
            return;
        }
        if (controlPressed) {
            DebugRegistry.toggleStates(key);
        }
        if (!developmentEnvironment) {
            return;
        }
        if (controlPressed && key == Key.T) {
            this.switchTheme();
            return;
        }
        if (controlPressed && key == Key.R) {
            this.defaultThemeService.reload(true);
            return;
        }
        if (controlPressed && key == Key.L) {
            this.framerateLimit = !this.framerateLimit;
        }
    }
    
    @Subscribe
    public void handleFramerateLimit(final GameFpsLimitEvent event) {
        if (this.framerateLimit) {
            event.setFramerateLimit(1);
        }
    }
    
    private void switchTheme() {
        final Theme currentTheme = this.defaultThemeService.currentTheme();
        final String name = currentTheme.getId().equals("vanilla") ? "fancy" : "vanilla";
        this.defaultThemeService.reload(name, true);
    }
}
