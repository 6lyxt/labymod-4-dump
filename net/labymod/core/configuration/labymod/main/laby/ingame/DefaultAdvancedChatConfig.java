// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public class DefaultAdvancedChatConfig extends Config implements AdvancedChatConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled;
    @SettingSection("window")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> draggable;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> resizeable;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showMenuButton;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> legacyScrollbar;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fadeInMessages;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> disableCrosshair;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showPlayerHeads;
    @VersionCompatibility("1.17<*")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showChatOnHiddenGui;
    @KeybindWidget.KeyBindSetting
    private final ConfigProperty<Key> chatPeekKey;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> legacyMessageOffset;
    @SettingSection("globalTab")
    @SliderWidget.SliderSetting(min = 100.0f, max = 1000.0f, steps = 10.0f)
    @CustomTranslation("labymod.chattab.general.chatLimit")
    private final ConfigProperty<Integer> globalChatLimit;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.general.combineChatMessages")
    private final ConfigProperty<Boolean> globalCombineChatMessages;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.general.antiChatClear")
    private final ConfigProperty<Boolean> globalAntiChatClear;
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.19<*")
    @CustomTranslation("labymod.chattab.general.chatTrust")
    private final ConfigProperty<Boolean> globalChatTrust;
    @CustomTranslation("labymod.chattab.general.shadow")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> globalShadow;
    @CustomTranslation("labymod.chattab.general.background")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> globalBackground;
    
    public DefaultAdvancedChatConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.draggable = new ConfigProperty<Boolean>(true);
        this.resizeable = new ConfigProperty<Boolean>(true);
        this.showMenuButton = new ConfigProperty<Boolean>(true);
        this.legacyScrollbar = new ConfigProperty<Boolean>(false);
        this.fadeInMessages = new ConfigProperty<Boolean>(true);
        this.disableCrosshair = new ConfigProperty<Boolean>(true);
        this.showPlayerHeads = new ConfigProperty<Boolean>(true);
        this.showChatOnHiddenGui = new ConfigProperty<Boolean>(true);
        this.chatPeekKey = new ConfigProperty<Key>(Key.NONE);
        this.legacyMessageOffset = new ConfigProperty<Boolean>(true);
        this.globalChatLimit = new ConfigProperty<Integer>(100);
        this.globalCombineChatMessages = new ConfigProperty<Boolean>(false);
        this.globalAntiChatClear = new ConfigProperty<Boolean>(false);
        this.globalChatTrust = new ConfigProperty<Boolean>(true);
        this.globalShadow = new ConfigProperty<Boolean>(true);
        this.globalBackground = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> draggable() {
        return this.draggable;
    }
    
    @Override
    public ConfigProperty<Boolean> resizeable() {
        return this.resizeable;
    }
    
    @Override
    public ConfigProperty<Boolean> showMenuButton() {
        return this.showMenuButton;
    }
    
    @Override
    public ConfigProperty<Boolean> legacyScrollbar() {
        return this.legacyScrollbar;
    }
    
    @Override
    public ConfigProperty<Boolean> fadeInMessages() {
        return this.fadeInMessages;
    }
    
    @Override
    public ConfigProperty<Boolean> disableCrosshair() {
        return this.disableCrosshair;
    }
    
    @Override
    public ConfigProperty<Boolean> showPlayerHeads() {
        return this.showPlayerHeads;
    }
    
    @Override
    public ConfigProperty<Boolean> showChatOnHiddenGui() {
        return this.showChatOnHiddenGui;
    }
    
    @Override
    public ConfigProperty<Key> chatPeekKey() {
        return this.chatPeekKey;
    }
    
    @Override
    public ConfigProperty<Boolean> legacyMessageOffset() {
        return this.legacyMessageOffset;
    }
    
    @Override
    public ConfigProperty<Integer> globalChatLimit() {
        return this.globalChatLimit;
    }
    
    @Override
    public ConfigProperty<Boolean> globalCombineChatMessages() {
        return this.globalCombineChatMessages;
    }
    
    @Override
    public ConfigProperty<Boolean> globalAntiChatClear() {
        return this.globalAntiChatClear;
    }
    
    @Override
    public ConfigProperty<Boolean> globalChatTrust() {
        return this.globalChatTrust;
    }
    
    @Override
    public ConfigProperty<Boolean> globalShadow() {
        return this.globalShadow;
    }
    
    @Override
    public ConfigProperty<Boolean> globalBackground() {
        return this.globalBackground;
    }
}
