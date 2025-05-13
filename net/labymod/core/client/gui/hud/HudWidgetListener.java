// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud;

import net.labymod.core.event.addon.lifecycle.AddonStateChangeEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.core.client.render.font.text.TextUtil;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;

public class HudWidgetListener
{
    private final DefaultHudWidgetRegistry hudWidgetRegistry;
    
    public HudWidgetListener(final DefaultHudWidgetRegistry hudWidgetRegistry) {
        this.hudWidgetRegistry = hudWidgetRegistry;
    }
    
    @Subscribe
    public void onSettingUpdate(final SettingUpdateEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final SettingAccessor accessor = event.setting().getAccessor();
        if (accessor == null || accessor.config() == null) {
            return;
        }
        final Config config = accessor.config();
        final boolean isGlobalConfig = config instanceof GlobalHudWidgetConfig;
        if (!isGlobalConfig && !(config instanceof HudWidgetConfig)) {
            return;
        }
        TextUtil.pushAndApplyAttributes();
        for (final HudWidget<?> hudWidget : this.hudWidgetRegistry.values()) {
            if (isGlobalConfig || config.equals(hudWidget.getConfig())) {
                if (!hudWidget.isEnabled()) {
                    continue;
                }
                hudWidget.reloadConfig();
            }
        }
        TextUtil.popRenderAttributes();
    }
    
    @Subscribe
    public void onResourceReload(final ResourceReloadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        TextUtil.pushAndApplyAttributes();
        for (final HudWidget<?> widget : this.hudWidgetRegistry.values()) {
            widget.onUpdate();
        }
        TextUtil.popRenderAttributes();
    }
    
    @Subscribe
    public void onThemeUpdate(final ThemeUpdateEvent event) {
        if (event.reason().equals(FancyThemeConfig.FONT_UPDATE_REASON)) {
            this.hudWidgetRegistry.updateHudWidgets("theme_update");
        }
    }
    
    @Subscribe
    public void onThemeChange(final ThemeChangeEvent event) {
        if (event.phase() == Phase.POST) {
            this.hudWidgetRegistry.updateHudWidgets("theme_change");
        }
    }
    
    @Subscribe
    public void onAddonStateChange(final AddonStateChangeEvent event) {
        this.hudWidgetRegistry.updateHudWidgets("addon_state_change");
    }
}
