// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultServerListConfig extends Config implements ServerListConfig
{
    @IntroducedIn("4.2.0")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> draggableEntries;
    @SettingSection("liveServerList")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(y = 7)
    private final ConfigProperty<Boolean> liveServerList;
    @SliderWidget.SliderSetting(min = 3.0f, max = 30.0f)
    @SettingRequires("liveServerList")
    @SpriteSlot(x = 1, y = 7)
    private final ConfigProperty<Integer> cooldown;
    @SettingSection("richServerList")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> richServerList;
    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> niceNames;
    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> highQualityTextures;
    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> quickJoinButtons;
    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> friendsInServerList;
    
    public DefaultServerListConfig() {
        this.draggableEntries = new ConfigProperty<Boolean>(true);
        this.liveServerList = new ConfigProperty<Boolean>(true);
        this.cooldown = new ConfigProperty<Integer>(10);
        this.richServerList = new ConfigProperty<Boolean>(true);
        this.niceNames = new ConfigProperty<Boolean>(true);
        this.highQualityTextures = new ConfigProperty<Boolean>(true);
        this.quickJoinButtons = new ConfigProperty<Boolean>(true);
        this.friendsInServerList = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> draggableEntries() {
        return this.draggableEntries;
    }
    
    @Override
    public ConfigProperty<Boolean> liveServerList() {
        return this.liveServerList;
    }
    
    @Override
    public ConfigProperty<Integer> cooldown() {
        return this.cooldown;
    }
    
    @Override
    public ConfigProperty<Boolean> richServerList() {
        return this.richServerList;
    }
    
    @Override
    public ConfigProperty<Boolean> niceNames() {
        return this.niceNames;
    }
    
    @Override
    public ConfigProperty<Boolean> highQualityTextures() {
        return this.highQualityTextures;
    }
    
    @Override
    public ConfigProperty<Boolean> quickJoinButtons() {
        return this.quickJoinButtons;
    }
    
    @Override
    public ConfigProperty<Boolean> friendsInServerList() {
        return this.friendsInServerList;
    }
}
