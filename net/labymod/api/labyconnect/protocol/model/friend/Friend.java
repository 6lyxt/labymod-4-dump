// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.friend;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.labyconnect.protocol.model.User;

public interface Friend extends User
{
    boolean isOnline();
    
    @Nullable
    ServerInfo getServer();
    
    @NotNull
    Chat chat();
    
    void remove();
    
    boolean isPinned();
    
    void pin();
    
    void unpin();
    
    long getLastOnline();
    
    @Deprecated
    default Chat getChat() {
        return this.chat();
    }
    
    String getNote();
    
    void openNoteEditor();
    
    long getLastServerChange();
}
