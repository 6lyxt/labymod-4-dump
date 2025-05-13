// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import java.util.Objects;
import java.util.List;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.util.ChatHistoryWidget;

@AutoWidget
public class LabyConnectChatWidget extends ChatHistoryWidget
{
    private static final long FIVE_MINUTES = 300000L;
    private final Chat chat;
    
    public LabyConnectChatWidget(final Chat chat, final ListSession<Widget> listSession) {
        super(listSession);
        this.chat = chat;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.clearHistory();
        final List<ChatMessage> messages = this.chat.getMessages();
        for (int i = messages.size() - 1; i >= 0; --i) {
            final ChatMessage message = messages.get(i);
            final ChatMessage previousMessage = (i > 0) ? messages.get(i - 1) : null;
            message.markAsRead();
            this.addToHistory(this.createWidget(message, previousMessage));
        }
    }
    
    public void addMessage(final ChatMessage message) {
        final Widget widget = this.createWidget(message, this.getPreviousMessage(2));
        if (this.initialized) {
            this.addChildInitialized(0, widget);
        }
        else {
            this.addChild(0, widget);
        }
    }
    
    private ChatMessage getPreviousMessage(final int offset) {
        final List<ChatMessage> messages = this.chat.getMessages();
        return (messages.size() > 1) ? messages.get(messages.size() - offset) : null;
    }
    
    private Widget createWidget(final ChatMessage message, final ChatMessage previousMessage) {
        final boolean headerVisible = previousMessage == null || !Objects.equals(previousMessage.sender(), message.sender()) || message.getTimestamp() - previousMessage.getTimestamp() > 300000L;
        final LabyConnectChatMessageWidget messageWidget = new LabyConnectChatMessageWidget(message);
        messageWidget.setHeaderVisible(headerVisible);
        return messageWidget;
    }
}
