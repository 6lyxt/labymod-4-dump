// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.text.SimpleDateFormat;
import java.util.Objects;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.user.GameUser;
import java.text.DateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

@AutoWidget
public class LabyConnectChatMessageWidget extends FlexibleContentWidget
{
    private static final DateFormat TIME_FORMAT;
    private final GameUser sender;
    private final ChatMessage message;
    private boolean headerVisible;
    
    public LabyConnectChatMessageWidget(final ChatMessage message) {
        this(message.sender().gameUser(), message);
    }
    
    public LabyConnectChatMessageWidget(final GameUser sender, final ChatMessage message) {
        this.headerVisible = true;
        this.sender = sender;
        this.message = message;
        ((AbstractWidget<Widget>)this).addId(sender.getUniqueId().equals(this.labyAPI.getUniqueId()) ? "self" : "other");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final User sender = this.message.sender();
        final VerticalListWidget<Widget> flex = new VerticalListWidget<Widget>();
        flex.addId("flex");
        if (this.headerVisible) {
            final HorizontalListWidget header = new HorizontalListWidget();
            ((AbstractWidget<Widget>)header).addId("message-header");
            final TextColor color = this.sender.displayColor();
            header.addEntry(ComponentWidget.component(((BaseComponent<Component>)Component.text(sender.getName())).color(color)).addId("sender"));
            final ComponentWidget timestamp = ComponentWidget.text(LabyConnectChatMessageWidget.TIME_FORMAT.format(this.message.getTimestamp()));
            timestamp.addId("timestamp");
            header.addEntry(timestamp);
            flex.addChild(header);
        }
        final Widget messageContentWidget = this.message.createWidget();
        messageContentWidget.addId("message-content");
        if (!this.headerVisible) {
            messageContentWidget.addId("headless");
        }
        flex.addChild(messageContentWidget);
        this.addContent(flex);
        if (this.headerVisible) {
            final IconWidget avatar = new IconWidget(Icon.head(sender.getUniqueId()));
            avatar.addId("avatar");
            this.addContent(avatar);
        }
    }
    
    public void setHeaderVisible(final boolean headerVisible) {
        this.headerVisible = headerVisible;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        this.message.markAsRead();
    }
    
    public ChatMessage message() {
        return this.message;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final LabyConnectChatMessageWidget that = (LabyConnectChatMessageWidget)o;
        return Objects.equals(this.message, that.message);
    }
    
    @Override
    public int hashCode() {
        return (this.message != null) ? this.message.hashCode() : 0;
    }
    
    static {
        TIME_FORMAT = new SimpleDateFormat("HH:mm");
    }
}
