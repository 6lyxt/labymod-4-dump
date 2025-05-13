// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.attachment;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;
import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;

@AutoWidget
public class URIAttachmentWidget extends AttachmentWidget<AbstractURIAttachment>
{
    public URIAttachmentWidget(final AbstractURIAttachment attachment) {
        super(attachment);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Component buttonComponent = ((AbstractURIAttachment)this.attachment).getButtonComponent();
        final FlexibleContentWidget contentWidget = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)contentWidget).addId("attachment-content");
        if (buttonComponent == null && ((AbstractURIAttachment)this.attachment).isClickable()) {
            final FlexibleContentWidget flexibleContentWidget = contentWidget;
            final AbstractURIAttachment obj = (AbstractURIAttachment)this.attachment;
            Objects.requireNonNull(obj);
            flexibleContentWidget.setPressable(obj::open);
            this.addId("clickable");
        }
        final IconWidget iconWidget = new IconWidget(((AbstractURIAttachment)this.attachment).getIcon());
        iconWidget.addId("attachment-icon");
        contentWidget.addContent(iconWidget);
        final FlexibleContentWidget textWidget = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)textWidget).addId("attachment-text");
        final Component title = ((AbstractURIAttachment)this.attachment).getTitle();
        if (title != null) {
            final ComponentWidget titleWidget = ComponentWidget.component(title);
            titleWidget.addId("attachment-title");
            textWidget.addContent(titleWidget);
        }
        final Component description = ((AbstractURIAttachment)this.attachment).getDescription();
        if (description != null) {
            final ComponentWidget descriptionWidget = ComponentWidget.component(description);
            descriptionWidget.addId("attachment-description");
            textWidget.addFlexibleContent(descriptionWidget);
        }
        if (buttonComponent != null) {
            final ButtonWidget buttonWidget = ButtonWidget.component(buttonComponent);
            ((AbstractWidget<Widget>)buttonWidget).addId("attachment-button");
            final ButtonWidget buttonWidget2 = buttonWidget;
            final AbstractURIAttachment obj2 = (AbstractURIAttachment)this.attachment;
            Objects.requireNonNull(obj2);
            buttonWidget2.setPressable(obj2::open);
            textWidget.addContent(buttonWidget);
        }
        if (textWidget.getChildren().isEmpty()) {
            iconWidget.addId("attachment-preview");
        }
        else {
            contentWidget.addFlexibleContent(textWidget);
        }
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(contentWidget);
    }
}
