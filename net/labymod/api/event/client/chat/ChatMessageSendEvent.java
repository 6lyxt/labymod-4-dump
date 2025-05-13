// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ChatMessageSendEvent extends DefaultCancellable implements Event
{
    private static final String COMMAND_LITERAL = "/";
    private final String originalMessage;
    private String message;
    private String historyText;
    
    public ChatMessageSendEvent(@NotNull final String message, final boolean showInHistory) {
        this.originalMessage = message;
        this.message = message;
        if (showInHistory) {
            this.historyText = message;
        }
    }
    
    public String getOriginalMessage() {
        return this.originalMessage;
    }
    
    public boolean isOriginalMessageCommand() {
        return this.originalMessage.startsWith("/");
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public boolean isMessageCommand() {
        return this.message.startsWith("/");
    }
    
    public String getHistoryText() {
        return this.historyText;
    }
    
    public void hideInHistory() {
        this.historyText = null;
    }
    
    public void changeMessage(@NotNull final String message) {
        this.changeMessage(message, message);
    }
    
    public void changeMessage(@NotNull final String message, @Nullable final String historyText) {
        this.message = message;
        this.historyText = historyText;
    }
}
