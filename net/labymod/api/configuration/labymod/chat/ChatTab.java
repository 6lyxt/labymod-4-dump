// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.metadata.MetadataExtension;

public abstract class ChatTab implements MetadataExtension
{
    private final RootChatTabConfig rootConfig;
    private final ChatWindow chatWindow;
    private Metadata metadata;
    protected int unread;
    
    protected ChatTab(@NotNull final ChatWindow chatWindow, @NotNull final RootChatTabConfig rootConfig) {
        Objects.requireNonNull(chatWindow, "Chat window cannot be null!");
        Objects.requireNonNull(rootConfig, "Root config cannot be null!");
        this.chatWindow = chatWindow;
        this.rootConfig = rootConfig;
    }
    
    @NotNull
    public final RootChatTabConfig rootConfig() {
        return this.rootConfig;
    }
    
    @NotNull
    public final ChatWindow window() {
        return this.chatWindow;
    }
    
    @NotNull
    public final GeneralChatTabConfig config() {
        return this.rootConfig().config();
    }
    
    @NotNull
    @ApiStatus.NonExtendable
    public String getName() {
        final String name = this.config().name().get();
        if (name == null || name.trim().isEmpty()) {
            return this.getCustomName();
        }
        return name.trim();
    }
    
    @NotNull
    public String getCustomName() {
        return I18n.translate("labymod.activity.chat.context.newTab", new Object[0]);
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        throw new UnsupportedOperationException("Cannot overwrite Metadata of ChatTabs");
    }
    
    @Override
    public Metadata metadata() {
        if (this.metadata == null) {
            this.metadata = Metadata.create();
        }
        return this.metadata;
    }
    
    public int getUnread() {
        return this.unread;
    }
    
    public void resetUnread() {
        this.unread = 0;
    }
    
    public abstract void copy(@NotNull final ChatTab p0);
    
    @NotNull
    public abstract Widget createContentWidget(final WindowAccessor p0);
    
    public void invalidateCache() {
    }
}
