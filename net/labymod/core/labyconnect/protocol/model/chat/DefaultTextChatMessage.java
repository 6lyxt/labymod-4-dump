// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatMessageContentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Iterator;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.api.Laby;
import java.util.Objects;
import java.util.ArrayList;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;
import java.util.List;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;

public class DefaultTextChatMessage extends DefaultChatMessage implements TextChatMessage
{
    private String rawMessage;
    private String message;
    private final List<Attachment> attachments;
    
    public DefaultTextChatMessage(final Chat chat, final User sender, final long timestamp, final String message) {
        this(chat, sender, timestamp, message, null);
    }
    
    public DefaultTextChatMessage(final Chat chat, final User sender, final long timestamp, final String message, final Metadata metadata) {
        super(chat, sender, timestamp);
        this.attachments = new ArrayList<Attachment>();
        if (metadata != null) {
            this.metadata(metadata);
        }
        this.updateMessage(message);
    }
    
    @Override
    public List<Attachment> getAttachments() {
        return this.attachments;
    }
    
    @Override
    public String getRawMessage() {
        return this.rawMessage;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public void updateMessage(final String message) {
        if (Objects.equals(this.rawMessage, message)) {
            return;
        }
        this.rawMessage = message;
        this.message = message.trim();
        this.attachments.clear();
        try {
            final List<URIAttachment> attachments = Laby.references().attachmentParser().parse(this.rawMessage);
            for (final URIAttachment attachment : attachments) {
                if (attachment.shouldHideURI()) {
                    this.message = this.message.replace(attachment.getUrl(), "").trim();
                }
                this.attachments.add(attachment);
            }
        }
        catch (final Throwable e) {
            e.printStackTrace();
        }
    }
    
    @NotNull
    @Override
    public Widget createWidget() {
        return new LabyConnectChatMessageContentWidget(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultTextChatMessage that = (DefaultTextChatMessage)o;
        return Objects.equals(this.timestamp, that.timestamp) && Objects.equals(this.message, that.message);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ((this.message != null) ? this.message.hashCode() : 0);
        return result;
    }
}
