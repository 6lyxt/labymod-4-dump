// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat.category;

import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.chat.filter.ChatFilter;
import java.util.List;
import net.labymod.api.configuration.settings.annotation.SettingRequiresExclude;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.loader.Config;

@CustomTranslation("labymod.chattab.general")
@SettingRequires(value = "global", invert = true)
public class GeneralChatTabConfig extends Config
{
    private static final AdvancedChatConfig CHAT_SETTING;
    @TextFieldWidget.TextFieldSetting
    @CustomTranslation("labymod.chattab.general.identifier")
    @SettingRequiresExclude
    private final ConfigProperty<String> name;
    @SettingRequiresExclude
    @CustomTranslation("labymod.chattab.")
    private final ConfigProperty<List<ChatFilter>> filters;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> global;
    @SliderWidget.SliderSetting(min = 100.0f, max = 1000.0f, steps = 10.0f)
    private final ConfigProperty<Integer> chatLimit;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> combineChatMessages;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> antiChatClear;
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.19<*")
    private final ConfigProperty<Boolean> chatTrust;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> shadow;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> background;
    
    public GeneralChatTabConfig(final String name) {
        this.name = new ConfigProperty<String>("");
        this.filters = new ConfigProperty<List<ChatFilter>>(new ArrayList<ChatFilter>());
        this.global = new ConfigProperty<Boolean>(true);
        this.chatLimit = new ConfigProperty<Integer>(GeneralChatTabConfig.CHAT_SETTING.globalChatLimit().get());
        this.combineChatMessages = new ConfigProperty<Boolean>(GeneralChatTabConfig.CHAT_SETTING.globalCombineChatMessages().get());
        this.antiChatClear = new ConfigProperty<Boolean>(GeneralChatTabConfig.CHAT_SETTING.globalAntiChatClear().get());
        this.chatTrust = new ConfigProperty<Boolean>(true);
        this.shadow = new ConfigProperty<Boolean>(true);
        this.background = new ConfigProperty<Boolean>(true);
        this.name.set(name);
    }
    
    public GeneralChatTabConfig() {
        this.name = new ConfigProperty<String>("");
        this.filters = new ConfigProperty<List<ChatFilter>>(new ArrayList<ChatFilter>());
        this.global = new ConfigProperty<Boolean>(true);
        this.chatLimit = new ConfigProperty<Integer>(GeneralChatTabConfig.CHAT_SETTING.globalChatLimit().get());
        this.combineChatMessages = new ConfigProperty<Boolean>(GeneralChatTabConfig.CHAT_SETTING.globalCombineChatMessages().get());
        this.antiChatClear = new ConfigProperty<Boolean>(GeneralChatTabConfig.CHAT_SETTING.globalAntiChatClear().get());
        this.chatTrust = new ConfigProperty<Boolean>(true);
        this.shadow = new ConfigProperty<Boolean>(true);
        this.background = new ConfigProperty<Boolean>(true);
    }
    
    public ConfigProperty<String> name() {
        return this.name;
    }
    
    public ConfigProperty<Boolean> global() {
        return this.global;
    }
    
    public ConfigProperty<Integer> chatLimit() {
        return this.global.get() ? GeneralChatTabConfig.CHAT_SETTING.globalChatLimit() : this.chatLimit;
    }
    
    public ConfigProperty<Boolean> combineChatMessages() {
        return this.global.get() ? GeneralChatTabConfig.CHAT_SETTING.globalCombineChatMessages() : this.combineChatMessages;
    }
    
    public ConfigProperty<Boolean> antiChatClear() {
        return this.global.get() ? GeneralChatTabConfig.CHAT_SETTING.globalAntiChatClear() : this.antiChatClear;
    }
    
    public ConfigProperty<Boolean> chatTrust() {
        return this.global.get() ? GeneralChatTabConfig.CHAT_SETTING.globalChatTrust() : this.chatTrust;
    }
    
    public ConfigProperty<Boolean> shadow() {
        return this.global.get() ? GeneralChatTabConfig.CHAT_SETTING.globalShadow() : this.shadow;
    }
    
    public ConfigProperty<Boolean> background() {
        return this.global.get() ? GeneralChatTabConfig.CHAT_SETTING.globalBackground() : this.background;
    }
    
    public ConfigProperty<List<ChatFilter>> filters() {
        return this.filters;
    }
    
    static {
        CHAT_SETTING = Laby.labyAPI().config().ingame().advancedChat();
    }
}
