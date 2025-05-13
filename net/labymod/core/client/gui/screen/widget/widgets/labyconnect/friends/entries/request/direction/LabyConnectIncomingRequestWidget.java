// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.direction;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.LabyConnectRequestWidget;

@AutoWidget
public class LabyConnectIncomingRequestWidget extends LabyConnectRequestWidget<IncomingFriendRequest>
{
    public LabyConnectIncomingRequestWidget(final ParentScreen contentDisplay, final IncomingFriendRequest request) {
        super(contentDisplay, request);
    }
    
    @Override
    protected void initializeButtons(final DivWidget container) {
        final ButtonWidget widgetAccept = ButtonWidget.icon(Textures.SpriteCommon.SMALL_CHECKED);
        ((AbstractWidget<Widget>)widgetAccept).addId("action", "accept");
        final ButtonWidget buttonWidget = widgetAccept;
        final IncomingFriendRequest obj = (IncomingFriendRequest)this.request;
        Objects.requireNonNull(obj);
        buttonWidget.setPressable(obj::accept);
        ((AbstractWidget<ButtonWidget>)container).addChild(widgetAccept);
        final ButtonWidget widgetDecline = ButtonWidget.icon(Textures.SpriteCommon.SMALL_X_WITH_SHADOW);
        ((AbstractWidget<Widget>)widgetDecline).addId("action", "decline");
        final ButtonWidget buttonWidget2 = widgetDecline;
        final IncomingFriendRequest obj2 = (IncomingFriendRequest)this.request;
        Objects.requireNonNull(obj2);
        buttonWidget2.setPressable(obj2::decline);
        ((AbstractWidget<ButtonWidget>)container).addChild(widgetDecline);
    }
}
