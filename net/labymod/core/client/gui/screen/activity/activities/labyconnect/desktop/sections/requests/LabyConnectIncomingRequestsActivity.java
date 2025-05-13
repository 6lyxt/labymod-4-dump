// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.direction.LabyConnectIncomingRequestWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/labyconnect/laby-connect-incoming-requests.lss")
@AutoActivity
public class LabyConnectIncomingRequestsActivity extends Activity
{
    private VerticalListWidget<LabyConnectIncomingRequestWidget> listRequests;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final LabyConnect labyConnect = this.labyAPI.labyConnect();
        if (!labyConnect.isConnected()) {
            return;
        }
        final List<IncomingFriendRequest> friendRequests = labyConnect.getSession().getIncomingRequests();
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        final DivWidget header = new DivWidget();
        header.addId("header");
        final ComponentWidget title = ComponentWidget.i18n("labymod.activity.labyconnect.incomingRequests.title");
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)header).addChild(title);
        container.addContent(header);
        final DivWidget listContainer = new DivWidget();
        listContainer.addId("list-container");
        (this.listRequests = new VerticalListWidget<LabyConnectIncomingRequestWidget>()).addId("list");
        for (final IncomingFriendRequest friendRequest : friendRequests) {
            this.listRequests.addChild(new LabyConnectIncomingRequestWidget(this, friendRequest));
        }
        final ScrollWidget scrollWidget = new ScrollWidget(this.listRequests);
        scrollWidget.addId("scroll");
        ((AbstractWidget<ScrollWidget>)listContainer).addChild(scrollWidget);
        container.addFlexibleContent(listContainer);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestAdd(final LabyConnectIncomingFriendRequestAddEvent event) {
        this.addFriendRequest(event.request());
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestAddBulk(final LabyConnectIncomingFriendRequestAddBulkEvent event) {
        for (final IncomingFriendRequest friendRequest : event.getRequests()) {
            this.addFriendRequest(friendRequest);
        }
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestRemove(final LabyConnectIncomingFriendRequestRemoveEvent event) {
        this.listRequests.removeChildIf(LabyConnectIncomingRequestWidget.class, request -> request.getRequest().getUniqueId().equals(event.request().getUniqueId()));
    }
    
    private void addFriendRequest(final IncomingFriendRequest request) {
        final List<Widget> widget = this.listRequests.findChildrenIf(child -> child.getRequest().equals(request));
        if (widget != null && !widget.isEmpty()) {
            return;
        }
        this.listRequests.addChildAsync(new LabyConnectIncomingRequestWidget(this, request));
    }
}
