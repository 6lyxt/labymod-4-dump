// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.core.client.chat.autotext.DefaultAutoTextService;
import javax.inject.Inject;
import net.labymod.core.client.chat.autotext.AutoTextHandler;
import net.labymod.core.configuration.labymod.DefaultAutoTextConfig;
import net.labymod.core.configuration.labymod.chat.DefaultChatConfig;
import net.labymod.core.client.chat.prefix.DefaultChatPrefixRegistry;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.client.chat.filter.FilterChatService;
import net.labymod.api.client.chat.input.ChatInputRegistry;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatProvider;

@Singleton
@Implements(ChatProvider.class)
public final class DefaultChatProvider implements ChatProvider
{
    private final ChatController chatController;
    private final ChatInputRegistry chatInputRegistry;
    private final FilterChatService filterChatService;
    private final ConfigProvider<AutoTextConfigAccessor> autoTextConfig;
    private final ConfigProvider<ChatConfigAccessor> chatConfig;
    private final ChatPrefixRegistry prefixRegistry;
    
    @Inject
    public DefaultChatProvider(final ChatController chatController, final ChatInputRegistry chatInputRegistry, final FilterChatService filterChatService, final EventBus eventBus) {
        this.prefixRegistry = new DefaultChatPrefixRegistry();
        this.chatController = chatController;
        this.chatInputRegistry = chatInputRegistry;
        this.filterChatService = filterChatService;
        (this.chatConfig = DefaultChatConfig.ChatConfigProvider.INSTANCE).loadJson();
        (this.autoTextConfig = DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE).loadJson();
        eventBus.registerListener(new AutoTextHandler());
    }
    
    public void initialize() {
        ((DefaultAutoTextService)this.autoTextService()).initialize();
    }
    
    @Override
    public ChatController chatController() {
        return this.chatController;
    }
    
    @Override
    public ChatInputRegistry chatInputService() {
        return this.chatInputRegistry;
    }
    
    @Override
    public FilterChatService filterChatService() {
        return this.filterChatService;
    }
    
    @Override
    public AutoTextService autoTextService() {
        return Laby.references().autoTextService();
    }
    
    @Override
    public AutoTextConfigAccessor autoTextConfigAccessor() {
        return this.autoTextConfig.get();
    }
    
    @Override
    public ChatPrefixRegistry prefixRegistry() {
        return this.prefixRegistry;
    }
    
    public ConfigProvider<AutoTextConfigAccessor> autoTextConfigProvider() {
        return this.autoTextConfig;
    }
    
    @Override
    public ChatConfigAccessor chatConfigAccessor() {
        return this.chatConfig.get();
    }
    
    public ConfigProvider<ChatConfigAccessor> chatConfigProvider() {
        return this.chatConfig;
    }
}
