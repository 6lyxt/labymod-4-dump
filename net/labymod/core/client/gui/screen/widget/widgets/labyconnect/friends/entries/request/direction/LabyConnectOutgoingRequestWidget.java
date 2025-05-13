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
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.LabyConnectRequestWidget;

@AutoWidget
public class LabyConnectOutgoingRequestWidget extends LabyConnectRequestWidget<OutgoingFriendRequest>
{
    public LabyConnectOutgoingRequestWidget(final ParentScreen contentDisplay, final OutgoingFriendRequest request) {
        super(contentDisplay, request);
    }
    
    @Override
    protected void initializeButtons(final DivWidget container) {
        final ButtonWidget widgetDecline = ButtonWidget.icon(Textures.SpriteCommon.SMALL_X_WITH_SHADOW);
        ((AbstractWidget<Widget>)widgetDecline).addId("action", "decline");
        final ButtonWidget buttonWidget = widgetDecline;
        final OutgoingFriendRequest obj = (OutgoingFriendRequest)this.request;
        Objects.requireNonNull(obj);
        buttonWidget.setPressable(obj::withdraw);
        ((AbstractWidget<ButtonWidget>)container).addChild(widgetDecline);
    }
}
