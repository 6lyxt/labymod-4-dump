// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.advanced;

import java.util.Iterator;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import java.util.function.Supplier;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import java.util.Set;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AdvancedChatController
{
    Set<ChatWindow> getWindows();
    
    void saveConfig();
    
    void reload();
    
    boolean hasSecondaryWindow();
    
    @NotNull
    ChatWindow getOrCreateSecondaryWindow(@Nullable final Supplier<RootChatTabConfig> p0);
    
    @NotNull
    default ChatWindow getOrCreateSecondaryWindow() {
        return this.getOrCreateSecondaryWindow(null);
    }
    
    default <T extends ChatTab> boolean forEachChatTab(@NotNull final Class<T> clazz, @NotNull final Predicate<T> predicate) {
        for (final ChatWindow window : this.getWindows()) {
            if (window.forEachChatTab(clazz, predicate)) {
                return true;
            }
        }
        return false;
    }
    
    default boolean forEachIngameChatTab(@NotNull final Predicate<IngameChatTab> predicate) {
        for (final ChatWindow window : this.getWindows()) {
            if (window.forEachIngameChatTab(predicate)) {
                return true;
            }
        }
        return false;
    }
}
