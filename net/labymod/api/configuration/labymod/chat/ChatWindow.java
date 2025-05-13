// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat;

import net.labymod.api.client.chat.advanced.IngameChatTab;
import java.util.function.Predicate;
import java.util.Iterator;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import java.util.List;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;

public interface ChatWindow
{
    ChatWindowConfig config();
    
    List<ChatTab> getTabs();
    
    ChatTab getActiveTab();
    
    void save();
    
    void switchToTab(final ChatTab p0);
    
    void deleteTab(final ChatTab p0);
    
    ChatTab initializeTab(final RootChatTabConfig p0, final ChatTab p1, final boolean p2);
    
    default ChatTab initializeTab(final RootChatTabConfig config) {
        return this.initializeTab(config, null, true);
    }
    
    default boolean isMainWindow() {
        for (final ChatTab tab : this.getTabs()) {
            if (tab.rootConfig().type().get().equals(RootChatTabConfig.Type.SERVER)) {
                return true;
            }
        }
        return false;
    }
    
    default <T extends ChatTab> boolean forEachChatTab(final Class<T> clazz, final Predicate<T> predicate) {
        for (final ChatTab tab : this.getTabs()) {
            if (clazz.isInstance(tab) && predicate.test((T)tab)) {
                return true;
            }
        }
        return false;
    }
    
    default boolean forEachIngameChatTab(final Predicate<IngameChatTab> predicate) {
        for (final ChatTab tab : this.getTabs()) {
            if (tab instanceof final IngameChatTab ingameChatTab) {
                if (predicate.test(ingameChatTab)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    default ChatTab getFirstChatTabWithType(final RootChatTabConfig.Type type) {
        for (final ChatTab tab : this.getTabs()) {
            if (tab.rootConfig().type().get().equals(type)) {
                return tab;
            }
        }
        return null;
    }
}
