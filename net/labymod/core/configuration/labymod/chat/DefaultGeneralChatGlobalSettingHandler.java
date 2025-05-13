// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.chat;

import java.util.Iterator;
import java.util.List;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatSettingActivity;
import java.util.Locale;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.configuration.settings.Setting;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatGlobalSettingHandler;

@Singleton
@Implements(GeneralChatGlobalSettingHandler.class)
public class DefaultGeneralChatGlobalSettingHandler implements GeneralChatGlobalSettingHandler
{
    private final LabyAPI labyAPI;
    
    @Inject
    public DefaultGeneralChatGlobalSettingHandler(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
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
        final RootSettingRegistry settingRegistry = RootSettingRegistry.labymod(setting.getId()).translationId("settings.ingame.advancedChat");
        final List<Setting> chatSettings = ((Config)this.labyAPI.config().ingame().advancedChat()).toSettings();
        for (final Setting chatSetting : chatSettings) {
            if (chatSetting.isElement() && chatSetting.getId().toLowerCase(Locale.ROOT).contains("global")) {
                settingRegistry.register(chatSetting);
            }
        }
        return new ChatSettingActivity(settingRegistry);
    }
}
