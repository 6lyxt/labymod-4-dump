// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;
import java.util.Iterator;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class LabyConnectChatMessageContentWidget extends VerticalListWidget<Widget>
{
    private final TextChatMessage message;
    private final ComponentWidget componentMessageWidget;
    
    public LabyConnectChatMessageContentWidget(final TextChatMessage message) {
        this.message = message;
        (this.componentMessageWidget = ComponentWidget.component(PlainTextComponentSerializer.plainUrl().deserialize(message.getMessage()))).addId("component-message", "tile");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (!this.message.getMessage().isEmpty()) {
            ((AbstractWidget<ComponentWidget>)this).addChild(this.componentMessageWidget);
        }
        for (final Attachment attachment : this.message.getAttachments()) {
            final AttachmentWidget<?> widget = attachment.createWidget();
            widget.addId("attachment", "tile");
            ((AbstractWidget<AttachmentWidget<?>>)this).addChild(widget);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        for (final Widget widget : this.children) {
            if (!(widget instanceof AttachmentWidget)) {
                continue;
            }
            final AttachmentWidget<?> attachmentWidget = (AttachmentWidget<?>)widget;
            final int widgetVersion = attachmentWidget.getVersion();
            if (widgetVersion == ((Attachment)attachmentWidget.attachment()).getVersion()) {
                continue;
            }
            attachmentWidget.reInitialize();
        }
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        float width = this.componentMessageWidget.getContentWidth(BoundsType.OUTER);
        for (final Attachment attachment : this.message.getAttachments()) {
            width = Math.max(width, attachment.getWidth());
        }
        return width + this.bounds().getHorizontalOffset(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        float height = 0.0f;
        if (!this.message.getMessage().isEmpty()) {
            height += this.componentMessageWidget.getContentHeight(BoundsType.OUTER);
        }
        for (final Attachment attachment : this.message.getAttachments()) {
            height += attachment.getHeight();
        }
        final float spaceBetweenEntries = this.spaceBetweenEntries().get();
        if (spaceBetweenEntries > 0.0f && this.children.size() > 1) {
            height += spaceBetweenEntries * (this.children.size() - 1);
        }
        return height + this.bounds().getVerticalOffset(type);
    }
}
