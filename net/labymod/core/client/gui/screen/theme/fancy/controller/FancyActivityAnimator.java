// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.controller;

import net.labymod.core.test.cave.BackgroundWorldTestActivity;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.ShopActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.AddonsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.SettingsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;
import net.labymod.core.client.gui.screen.activity.activities.options.OptionsOverlay;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect.DisconnectedOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect.ConnectOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.NavigationActivity;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController;

public class FancyActivityAnimator extends ActivityAnimationController
{
    public FancyActivityAnimator(final Theme theme) {
        super(theme);
    }
    
    @Override
    protected void register() {
        this.register(MainMenuActivity.class, FancyActivityAnimator::mainActivityFunc);
        this.register(NavigationActivity.class, FancyActivityAnimator::navActivityFunc);
        this.register(MultiplayerActivity.class, FancyActivityAnimator::navContentActivityFunc);
        this.register(ConnectOverlay.class, FancyActivityAnimator::connectActivityFunc);
        this.register(DisconnectedOverlay.class, FancyActivityAnimator::connectActivityFunc);
        this.register(SingleplayerOverlay.class, FancyActivityAnimator::navContentActivityFunc);
        this.register(OptionsOverlay.class, FancyActivityAnimator::navContentActivityFunc);
        this.register(LabyConnectActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(LabyModActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(SettingsActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(AddonsActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(WidgetsEditorActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(PlayerActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(ShopActivity.class, FancyActivityAnimator::opacityFunc);
        this.register(ScreenshotBrowserActivity.class, FancyActivityAnimator::opacityFunc);
    }
    
    protected static void mainActivityFunc(final Transformer transformer) {
        if (transformer.hasNoPreviouslyActivity()) {
            return;
        }
        transformer.scale(ActivityAnimationController.customFunc(transformer, 1.5f, 1.0f, 500, ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_CURVE));
        opacityFunc(transformer);
    }
    
    protected static void navActivityFunc(final Transformer transformer) {
        if (isPreviouslyMainMenu(transformer) && DynamicBackgroundController.isEnabled()) {
            transformer.scale(navFunc(transformer));
        }
    }
    
    protected static void navContentActivityFunc(final Transformer transformer) {
        if (isPreviouslyMainMenu(transformer)) {
            transformer.scale(navFunc(transformer) * defaultFunc(transformer, 0.1f, 1.0f));
        }
        opacityFunc(transformer);
    }
    
    protected static void connectActivityFunc(final Transformer transformer) {
        opacityFunc(transformer);
    }
    
    protected static void opacityFunc(final Transformer transformer) {
        transformer.opacity(defaultFunc(transformer));
    }
    
    protected static float navFunc(final Transformer transformer) {
        return defaultFunc(transformer, 1.3f, 1.0f);
    }
    
    protected static float defaultFunc(final Transformer transformer, final float from, final float to) {
        return ActivityAnimationController.customFunc(transformer, from, to, 400, ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_CURVE);
    }
    
    protected static float defaultFunc(final Transformer transformer) {
        return defaultFunc(transformer, 0.0f, 1.0f);
    }
    
    @Override
    protected boolean isEnabled() {
        final FancyThemeConfig themeConfig = Laby.labyAPI().themeService().getThemeConfig(this.theme, FancyThemeConfig.class);
        return themeConfig != null && themeConfig.activityTransitions().get();
    }
    
    private static boolean isPreviouslyMainMenu(final Transformer transformer) {
        return transformer.isPreviously(MainMenuActivity.class) || transformer.isPreviously(BackgroundWorldTestActivity.class);
    }
}
