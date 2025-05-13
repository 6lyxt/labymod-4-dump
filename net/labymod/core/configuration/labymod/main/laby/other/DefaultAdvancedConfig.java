// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.other;

import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget;
import net.labymod.api.util.MethodOrder;
import net.labymod.core.client.gui.screen.activity.activities.debug.OpenGLInformationActivity;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.HashMap;
import net.labymod.api.configuration.loader.annotation.Exclude;
import java.util.Map;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultAdvancedConfig extends Config implements AdvancedConfig
{
    private final ConfigProperty<Boolean> devTools;
    @SwitchWidget.SwitchSetting
    @SettingSection("devTools")
    @SettingRequires("devTools")
    private final ConfigProperty<Boolean> enableDebugger;
    @SwitchWidget.SwitchSetting
    @SettingRequires("devTools")
    private final ConfigProperty<Boolean> enableRefreshHotkey;
    @Exclude
    private final ConfigProperty<Map<String, Boolean>> debugWindows;
    
    public DefaultAdvancedConfig() {
        this.devTools = new ConfigProperty<Boolean>(false);
        this.enableDebugger = new ConfigProperty<Boolean>(false);
        this.enableRefreshHotkey = new ConfigProperty<Boolean>(false);
        this.debugWindows = new ConfigProperty<Map<String, Boolean>>(new HashMap<String, Boolean>());
    }
    
    @Override
    public boolean debugger() {
        return this.devTools.get() && this.enableDebugger.get();
    }
    
    @Override
    public boolean refreshHotkey() {
        return this.devTools.get() && this.enableRefreshHotkey.get();
    }
    
    @Override
    public Map<String, Boolean> debugWindows() {
        return this.debugWindows.get();
    }
    
    public ConfigProperty<Boolean> devTools() {
        return this.devTools;
    }
    
    @MethodOrder(before = "enableLssDebug")
    @ActivitySettingWidget.ActivitySetting
    public Activity viewOpenGLInfo() {
        return new OpenGLInformationActivity();
    }
}
