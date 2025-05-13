// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.HashMap;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddResponseEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectOutgoingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestRemoveEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddEvent;
import java.util.Locale;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Iterator;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.direction.LabyConnectOutgoingRequestWidget;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.LabyConnectRequestWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.notification.Notification;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/labyconnect/laby-connect-outgoing-requests.lss")
@AutoActivity
public class LabyConnectOutgoingRequestsActivity extends Activity
{
    private static final Map<String, Notification> NOTIFICATIONS;
    private String username;
    private VerticalListWidget<LabyConnectRequestWidget<?>> listRequests;
    private ComponentWidget pendingTitle;
    
    public LabyConnectOutgoingRequestsActivity(@NotNull final String username) {
        this();
        this.username = username;
    }
    
    public LabyConnectOutgoingRequestsActivity() {
        LabyConnectOutgoingRequestsActivity.NOTIFICATIONS.clear();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        final DivWidget header = new DivWidget();
        header.addId("header");
        final ComponentWidget title = ComponentWidget.i18n("labymod.activity.labyconnect.outgoingRequests.title");
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)header).addChild(title);
        final FlexibleContentWidget inputContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)inputContainer).addId("input-container");
        final TextFieldWidget input = new TextFieldWidget();
        input.addId("input");
        input.maximalLength(19);
        input.placeholder(Component.translatable("labymod.ui.textfield.username", new Component[0]));
        input.setFocused(true);
        input.setText(this.username);
        input.setCursorAtEnd();
        input.setPressable(() -> this.username = input.getText());
        input.submitHandler(username -> this.sendFriendRequest(username, input));
        inputContainer.addFlexibleContent(input);
        final ButtonWidget sendButton = ButtonWidget.i18n("labymod.ui.button.send");
        ((AbstractWidget<Widget>)sendButton).addId("send-button");
        sendButton.setPressable(() -> this.sendFriendRequest(input.getText(), input));
        inputContainer.addContent(sendButton);
        ((AbstractWidget<FlexibleContentWidget>)header).addChild(inputContainer);
        container.addContent(header);
        final DivWidget listContainer = new DivWidget();
        listContainer.addId("list-container");
        (this.pendingTitle = ComponentWidget.i18n("labymod.activity.labyconnect.outgoingRequests.list.title")).addId("list-title");
        this.pendingTitle.setVisible(false);
        ((AbstractWidget<ComponentWidget>)listContainer).addChild(this.pendingTitle);
        (this.listRequests = new VerticalListWidget<LabyConnectRequestWidget<?>>()).addId("list");
        this.initializeRequests();
        ((AbstractWidget<VerticalListWidget<LabyConnectRequestWidget<?>>>)listContainer).addChild(this.listRequests);
        container.addFlexibleContent(listContainer);
        final ButtonWidget cancelButton = ButtonWidget.i18n("labymod.ui.button.cancel");
        ((AbstractWidget<Widget>)cancelButton).addId("cancel-button");
        cancelButton.setPressable(this::displayPreviousScreen);
        container.addContent(cancelButton);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    private void initializeRequests() {
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        for (final OutgoingFriendRequest request : session.getOutgoingRequests()) {
            this.listRequests.addChildAsync(new LabyConnectOutgoingRequestWidget(this, request));
            this.pendingTitle.setVisible(true);
        }
    }
    
    private void sendFriendRequest(final String username, final TextFieldWidget input) {
        final String trim = username.trim();
        if (trim.isEmpty()) {
            return;
        }
        final String ownUsername = this.labyAPI.minecraft().sessionAccessor().getSession().getUsername();
        if (trim.equalsIgnoreCase(ownUsername)) {
            this.displayNotification(Icon.head(ownUsername), Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP, trim, ((BaseComponent<Component>)Component.translatable("labymod.activity.labyconnect.outgoingRequests.result.self", new Component[0])).color(NamedTextColor.RED));
            input.setText("");
            return;
        }
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session != null) {
            final Notification notification = this.displayNotification(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP, null, trim, ((BaseComponent<Component>)Component.translatable("labymod.activity.labyconnect.outgoingRequests.sending", new Component[0])).color(NamedTextColor.GREEN));
            LabyConnectOutgoingRequestsActivity.NOTIFICATIONS.put(trim.toLowerCase(Locale.ROOT), notification);
            session.sendFriendRequest(trim);
            input.setText("");
        }
    }
    
    @Subscribe
    public void onFriendRequestAdd(final LabyConnectOutgoingFriendRequestAddEvent event) {
        this.listRequests.addChildAsync(new LabyConnectOutgoingRequestWidget(this, event.request()));
    }
    
    @Subscribe
    public void onFriendRequestRemove(final LabyConnectOutgoingFriendRequestRemoveEvent event) {
        this.listRequests.removeChildIf(widget -> widget instanceof LabyConnectOutgoingRequestWidget && ((LabyConnectOutgoingRequestWidget)widget).getRequest().equals(event.request()));
    }
    
    @Subscribe
    public void onFriendRequestAddBulk(final LabyConnectOutgoingFriendRequestAddBulkEvent event) {
        for (final OutgoingFriendRequest request : event.getRequests()) {
            this.listRequests.addChildAsync(new LabyConnectOutgoingRequestWidget(this, request));
        }
    }
    
    @Subscribe
    public void onFriendRequestResponse(final LabyConnectOutgoingFriendRequestAddResponseEvent event) {
        final String translationKeyPrefix = "labymod.activity.labyconnect.outgoingRequests.result.";
        final String query = event.getQuery();
        final Notification notification = LabyConnectOutgoingRequestsActivity.NOTIFICATIONS.remove(query.toLowerCase(Locale.ROOT));
        if (notification != null) {
            Laby.references().notificationController().pop(notification);
        }
        Component component;
        if (event.wasSent()) {
            component = ((BaseComponent<Component>)Component.translatable(translationKeyPrefix + "success", new Component[0])).color(NamedTextColor.GREEN);
        }
        else {
            final String reason = event.getReason();
            if (reason.contains(" ")) {
                final LegacyResponse response = LegacyResponse.of(reason, query);
                if (response == null) {
                    component = Component.text(reason);
                }
                else {
                    component = ((BaseComponent<Component>)Component.translatable(translationKeyPrefix + String.valueOf(response), new Component[0])).color(NamedTextColor.RED);
                    if (response.other) {
                        component = ((TranslatableComponent)component).args(Component.text(query));
                    }
                }
            }
            else {
                final String translationKey = TextFormat.SNAKE_CASE.toCamelCase(reason, true);
                component = Component.translatable(translationKeyPrefix + translationKey, new Component[0]);
            }
            component = component.color(NamedTextColor.RED);
        }
        this.displayNotification(event.wasSent() ? Icon.head(query) : Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP, event.wasSent() ? Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP : null, query, component);
    }
    
    private Notification displayNotification(final Icon icon, final Icon secondaryIcon, final String username, final Component text) {
        final Notification notification = Notification.builder().icon(icon).secondaryIcon(secondaryIcon).title(Component.text(username)).text(text).build();
        Laby.references().notificationController().push(notification);
        return notification;
    }
    
    static {
        NOTIFICATIONS = new HashMap<String, Notification>();
    }
    
    private enum LegacyResponse
    {
        SERVER_ERROR("Our server is having some problems. Please try again in a few minutes.", false), 
        ALREADY_REQUESTED("You already sent a request to that user", true), 
        REQUEST_LIMIT_REACHED("Too many outgoing friend requests.", false), 
        FRIEND_LIMIT_REACHED_OTHER("{} already has a lot of friends (Limit of 256)!", true), 
        FRIEND_LIMIT_REACHED_SELF("You already have a lot of friends (Limit of 256)!", false), 
        ALREADY_FRIENDS("You are already friends with {}", true), 
        NOT_FOUND("User {} was not found", true);
        
        private static final LegacyResponse[] VALUES;
        private final String response;
        private final boolean other;
        
        private LegacyResponse(final String response, final boolean other) {
            this.response = response.toLowerCase(Locale.ROOT);
            this.other = other;
        }
        
        @Override
        public String toString() {
            return TextFormat.SNAKE_CASE.toLowerCamelCase(this.name());
        }
        
        public static LegacyResponse of(String response, final String user) {
            response = response.toLowerCase(Locale.ROOT);
            for (final LegacyResponse value : LegacyResponse.VALUES) {
                final String legacyResponse = value.response.replace("{}", user.toLowerCase(Locale.ROOT));
                if (legacyResponse.equals(response)) {
                    return value;
                }
            }
            return null;
        }
        
        static {
            VALUES = values();
        }
    }
}
