// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.chat;

import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.metadata.MetadataExtension;

public interface ChatMessage extends MetadataExtension
{
    @NotNull
    User sender();
    
    void markAsRead();
    
    boolean isRead();
    
    long getTimestamp();
    
    void deleteMessage();
    
    @NotNull
    Widget createWidget();
}
