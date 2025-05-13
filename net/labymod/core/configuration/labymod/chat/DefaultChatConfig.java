// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.chat;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import java.util.List;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.configuration.loader.Config;

@ConfigName("chat")
public class DefaultChatConfig extends Config implements ChatConfigAccessor
{
    private List<ChatWindowConfig> windows;
    
    public DefaultChatConfig() {
        this.windows = new ArrayList<ChatWindowConfig>(Collections.singletonList(new ChatWindowConfig()));
    }
    
    @Override
    public List<ChatWindowConfig> getWindows() {
        return this.windows;
    }
    
    public void setWindows(final List<ChatWindowConfig> windows) {
        this.windows = windows;
    }
    
    public static class ChatConfigProvider extends ConfigProvider<ChatConfigAccessor>
    {
        public static final ChatConfigProvider INSTANCE;
        
        private ChatConfigProvider() {
        }
        
        @Override
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultChatConfig.class;
        }
        
        static {
            INSTANCE = new ChatConfigProvider();
        }
    }
}
