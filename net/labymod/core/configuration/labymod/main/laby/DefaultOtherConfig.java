// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.other.WindowConfig;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.util.version.SemanticVersion;
import java.util.List;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.CollectionResetWidget;
import java.util.Set;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultAdvancedConfig;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultWindowConfig;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultDiscordConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultOtherConfig extends Config implements OtherConfig
{
    @SpriteSlot(x = 5, y = 7)
    private DefaultDiscordConfig discord;
    @SpriteSlot(x = 4, y = 5, page = 1)
    private final DefaultWindowConfig window;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 4, y = 7)
    private final ConfigProperty<Boolean> anonymousStatistics;
    private final ConfigProperty<Map<String, Boolean>> outOfMemoryWarnings;
    @SpriteSlot(x = 3, y = 2, page = 1)
    private DefaultAdvancedConfig advanced;
    private final ConfigProperty<Set<String>> conversionAskedNamespaces;
    @CollectionResetWidget.CollectionResetSetting
    @SettingSection("serverapi")
    private final ConfigProperty<Map<String, Boolean>> serverSwitchChoices;
    @CollectionResetWidget.CollectionResetSetting
    private final ConfigProperty<List<String>> ignoredAddonRecommendations;
    private final ConfigProperty<Integer> lastWidth;
    private final ConfigProperty<Integer> lastHeight;
    private final ConfigProperty<String> preferredCurrency;
    @Exclude
    private final ConfigProperty<SemanticVersion> lastStartedVersion;
    
    public DefaultOtherConfig() {
        this.discord = new DefaultDiscordConfig();
        this.window = new DefaultWindowConfig();
        this.anonymousStatistics = new ConfigProperty<Boolean>(true);
        this.outOfMemoryWarnings = new ConfigProperty<Map<String, Boolean>>(new HashMap<String, Boolean>());
        this.advanced = new DefaultAdvancedConfig();
        this.conversionAskedNamespaces = new ConfigProperty<Set<String>>(new HashSet<String>());
        this.serverSwitchChoices = new ConfigProperty<Map<String, Boolean>>(new HashMap<String, Boolean>());
        this.ignoredAddonRecommendations = new ConfigProperty<List<String>>(new ArrayList<String>());
        this.lastWidth = new ConfigProperty<Integer>(1280);
        this.lastHeight = new ConfigProperty<Integer>(720);
        this.preferredCurrency = new ConfigProperty<String>("null");
        this.lastStartedVersion = new ConfigProperty<SemanticVersion>(new SemanticVersion("0.0.0"));
    }
    
    @Override
    public DiscordConfig discord() {
        return this.discord;
    }
    
    @Override
    public ConfigProperty<Boolean> anonymousStatistics() {
        return this.anonymousStatistics;
    }
    
    @Override
    public ConfigProperty<Map<String, Boolean>> outOfMemoryWarnings() {
        return this.outOfMemoryWarnings;
    }
    
    @Override
    public AdvancedConfig advanced() {
        return this.advanced;
    }
    
    @Override
    public ConfigProperty<Set<String>> conversionAskedNamespaces() {
        return this.conversionAskedNamespaces;
    }
    
    @Override
    public ConfigProperty<Integer> lastWidth() {
        return this.lastWidth;
    }
    
    @Override
    public ConfigProperty<Integer> lastHeight() {
        return this.lastHeight;
    }
    
    @Override
    public ConfigProperty<Map<String, Boolean>> serverSwitchChoices() {
        return this.serverSwitchChoices;
    }
    
    @Override
    public ConfigProperty<List<String>> ignoredAddonRecommendations() {
        return this.ignoredAddonRecommendations;
    }
    
    @Override
    public ConfigProperty<String> preferredCurrency() {
        return this.preferredCurrency;
    }
    
    @Override
    public WindowConfig window() {
        return this.window;
    }
    
    public ConfigProperty<SemanticVersion> lastStartedVersion() {
        return this.lastStartedVersion;
    }
    
    @Override
    public int getConfigVersion() {
        return 2;
    }
}
