// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud;

import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.configuration.settings.SettingHandler;

public class DefaultHudWidgetGlobalSettingHandler implements SettingHandler
{
    private final HudWidgetRegistry hudWidgetRegistry;
    
    public DefaultHudWidgetGlobalSettingHandler() {
        this.hudWidgetRegistry = Laby.references().hudWidgetRegistry();
    }
    
    @Override
    public void created(final Setting setting) {
    }
    
    @Override
    public void initialized(final Setting setting) {
    }
    
    @Override
    public boolean opensActivity(final Setting setting) {
        return true;
    }
    
    @Override
    public Activity activity(final Setting setting) {
        final AbstractSettingRegistry registry = this.hudWidgetRegistry.globalHudWidgetConfig().asRegistry(setting.getId()).translationId("hudWidget.global");
        return new SettingContentActivity(registry, false);
    }
}
