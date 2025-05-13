// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;
import java.util.List;

public interface TextChatMessage extends ChatMessage
{
    String getRawMessage();
    
    String getMessage();
    
    List<Attachment> getAttachments();
    
    void updateMessage(final String p0);
    
    @NotNull
    default Component toComponent() {
        final String message = this.getMessage();
        if (message != null && !message.isEmpty()) {
            return Component.text(message);
        }
        final List<Attachment> attachments = this.getAttachments();
        return attachments.isEmpty() ? Component.empty() : attachments.get(0).toComponent();
    }
}
