// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests.LabyConnectOutgoingRequestsActivity;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendPinUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectUpdateSettingEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageReadEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageDeleteEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectFriendAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import java.util.function.Predicate;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import java.util.Locale;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectOfflineActivity;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectRequestsButtonWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectFriendWidget;
import java.util.Comparator;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.ParentScreen;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/labyconnect/laby-connect-friends.lss")
@AutoActivity
public class LabyConnectFriendsActivity extends Activity
{
    private static final ListSession<LabyConnectEntryWidget> SESSION;
    private static final int MAX_OFFLINE_FRIENDS = 100;
    @Nullable
    private ScreenRendererWidget contentScreenRendererWidget;
    private ParentScreen contentDisplay;
    private ScrollWidget friendsScroll;
    private VerticalListWidget<LabyConnectEntryWidget> listFriends;
    private ComponentWidget friendCounter;
    private String filterQuery;
    
    public LabyConnectFriendsActivity() {
        this.filterQuery = "";
    }
    
    public void setContentDisplay(final ParentScreen contentDisplay) {
        if (contentDisplay instanceof final ScreenRendererWidget screenRendererWidget) {
            this.contentScreenRendererWidget = screenRendererWidget;
        }
        this.contentDisplay = contentDisplay;
        final LabyConnectEntryWidget selected = LabyConnectFriendsActivity.SESSION.getSelectedEntry();
        if (selected != null) {
            selected.updateContentDisplay(contentDisplay);
            selected.select();
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        final FlexibleContentWidget containers = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)containers).addId("containers");
        final DivWidget header = new DivWidget();
        header.addId("header-container");
        (this.friendCounter = ComponentWidget.empty()).addId("title");
        this.updateFriendCounter();
        ((AbstractWidget<ComponentWidget>)header).addChild(this.friendCounter);
        final ButtonWidget buttonAdd = ButtonWidget.icon(Textures.SpriteCommon.SMALL_ADD_WITH_SHADOW);
        ((AbstractWidget<Widget>)buttonAdd).addId("button-add");
        buttonAdd.setPressable(this::openOutgoingRequests);
        ((AbstractWidget<ButtonWidget>)header).addChild(buttonAdd);
        containers.addContent(header);
        final DivWidget search = new DivWidget();
        search.addId("search-container");
        final TextFieldWidget textField = new TextFieldWidget();
        textField.addId("search-field");
        textField.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        textField.setText(this.filterQuery);
        textField.updateListener(this::applyFriendsFilter);
        ((AbstractWidget<TextFieldWidget>)search).addChild(textField);
        containers.addContent(search);
        (this.listFriends = new VerticalListWidget<LabyConnectEntryWidget>(LabyConnectFriendsActivity.SESSION)).addId("friends-container");
        this.listFriends.setSelectCallback(LabyConnectEntryWidget::select);
        this.listFriends.setComparator(new Comparator<Widget>(this) {
            @Override
            public int compare(final Widget f1, final Widget f2) {
                if (!(f1 instanceof LabyConnectFriendWidget) || !(f2 instanceof LabyConnectFriendWidget)) {
                    if (f1 instanceof LabyConnectRequestsButtonWidget) {
                        return -1;
                    }
                    if (f2 instanceof LabyConnectRequestsButtonWidget) {
                        return 1;
                    }
                    return 0;
                }
                else {
                    final Friend friend1 = ((LabyConnectFriendWidget)f1).friend();
                    final Friend friend2 = ((LabyConnectFriendWidget)f2).friend();
                    final int cp = Boolean.compare(friend2.isPinned(), friend1.isPinned());
                    if (cp != 0) {
                        return cp;
                    }
                    final int cs = Boolean.compare(friend2.isOnline(), friend1.isOnline());
                    if (cs != 0) {
                        return cs;
                    }
                    if (friend1.chat().getLastMessage() != null || friend2.chat().getLastMessage() != null) {
                        final long t1 = (friend1.chat().getLastMessage() != null) ? friend1.chat().getLastMessage().getTimestamp() : 0L;
                        final long t2 = (friend2.chat().getLastMessage() != null) ? friend2.chat().getLastMessage().getTimestamp() : 0L;
                        final int cm = Long.compare(t2, t1);
                        if (cm != 0) {
                            return cm;
                        }
                    }
                    return Long.compare(friend2.getLastOnline(), friend1.getLastOnline());
                }
            }
        });
        this.initializeFriendList(false);
        containers.addFlexibleContent(this.friendsScroll = new ScrollWidget(this.listFriends));
        final DivWidget footer = new DivWidget();
        footer.addId("footer-container");
        final User self = session.self();
        footer.addChild(new DivWidget().addId("split"));
        footer.addChild(new IconWidget(Icon.head(self.getUniqueId())).addId("icon"));
        footer.addChild(ComponentWidget.text(self.getName()).addId("name"));
        final UserStatus userStatus = self.userStatus();
        final TranslatableComponent statusComponent = Component.translatable(userStatus.getRemoteTranslationKey(true), userStatus.textColor());
        footer.addChild(ComponentWidget.component(statusComponent).addId("status"));
        final ButtonWidget settingsButton = ButtonWidget.icon(Textures.SpriteCommon.SETTINGS);
        settingsButton.setPressable(this::openSettings);
        LabyConnectOfflineActivity.fillContextMenu(footer.createContextMenu());
        ((AbstractWidget<ButtonWidget>)footer).addChild(settingsButton);
        containers.addContent(footer);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(containers);
    }
    
    private void updateFriendCounter() {
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        final Component component = ((BaseComponent<Component>)Component.translatable("labymod.activity.labyconnect.friends.title", new Component[0])).append(((BaseComponent<Component>)Component.text(" (").append(((BaseComponent<Component>)Component.text(session.getOnlineFriendCount())).color(NamedTextColor.GREEN)).append(Component.text("/")).append(((BaseComponent<Component>)Component.text(session.getFriends().size())).color(NamedTextColor.GRAY))).append(Component.text(")")));
        this.friendCounter.setComponent(component);
    }
    
    private void applyFriendsFilter(final String query) {
        this.filterQuery = query;
        this.initializeFriendList(true);
    }
    
    private void initializeFriendList(final boolean initialized) {
        if (this.listFriends == null) {
            return;
        }
        this.listFriends.getChildren().clear();
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        if (!this.hasFilter()) {
            final LabyConnectRequestsButtonWidget requests = new LabyConnectRequestsButtonWidget(this.contentDisplay, session.getIncomingRequests());
            if (initialized) {
                this.listFriends.addChildInitialized(requests);
            }
            else {
                this.listFriends.addChild(requests);
            }
        }
        final List<LabyConnectEntryWidget> children = new ArrayList<LabyConnectEntryWidget>();
        int addedOfflineFriends = 0;
        for (final Friend friend : session.getFriends()) {
            final boolean offline = !friend.isOnline();
            if (offline && addedOfflineFriends >= 100 && !friend.isPinned()) {
                continue;
            }
            if (!this.isUserInFilter(friend)) {
                continue;
            }
            children.add(new LabyConnectFriendWidget(this.contentDisplay, friend));
            if (!offline) {
                continue;
            }
            ++addedOfflineFriends;
        }
        if (initialized) {
            this.listFriends.addChildrenInitialized(children, true);
        }
        else {
            this.listFriends.addChildren(children, true);
        }
    }
    
    private boolean hasFilter() {
        return this.filterQuery != null && !this.filterQuery.isEmpty();
    }
    
    private boolean isUserInFilter(final User user) {
        return !this.hasFilter() || user.getName().toLowerCase(Locale.ROOT).contains(this.filterQuery.toLowerCase(Locale.ROOT));
    }
    
    @Override
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        this.initializeFriendList(true);
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestAdd(final LabyConnectIncomingFriendRequestAddEvent event) {
        this.listFriends.reInitializeChildrenIf(new Predicate<LabyConnectEntryWidget>(this) {
            @Override
            public boolean test(final LabyConnectEntryWidget widget) {
                return widget instanceof LabyConnectRequestsButtonWidget;
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestAddBulkEvent(final LabyConnectIncomingFriendRequestAddBulkEvent event) {
        this.listFriends.reInitializeChildrenIf(new Predicate<LabyConnectEntryWidget>(this) {
            @Override
            public boolean test(final LabyConnectEntryWidget widget) {
                return widget instanceof LabyConnectRequestsButtonWidget;
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestRemove(final LabyConnectIncomingFriendRequestRemoveEvent event) {
        this.listFriends.reInitializeChildrenIf(new Predicate<LabyConnectEntryWidget>(this) {
            @Override
            public boolean test(final LabyConnectEntryWidget widget) {
                return widget instanceof LabyConnectRequestsButtonWidget;
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectFriendAdd(final LabyConnectFriendAddEvent event) {
        this.addFriend(event.friend());
        this.updateFriendCounter();
    }
    
    @Subscribe
    public void onLabyConnectFriendAddBulk(final LabyConnectFriendAddBulkEvent event) {
        for (final Friend friend : event.getFriends()) {
            this.addFriend(friend);
        }
        this.updateFriendCounter();
    }
    
    @Subscribe
    public void onLabyConnectFriendRemove(final LabyConnectFriendRemoveEvent event) {
        final UUID uniqueId = event.friend().getUniqueId();
        this.listFriends.removeChildIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
            @Override
            public boolean test(final LabyConnectFriendWidget widget) {
                return widget.friend().getUniqueId().equals(uniqueId);
            }
        });
        this.updateFriendCounter();
    }
    
    @Subscribe
    public void onLabyConnectChatMessage(final LabyConnectChatMessageEvent event) {
        final UUID chatId = event.chat().getId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
            @Override
            public boolean test(final LabyConnectFriendWidget widget) {
                return widget.friend().chat().getId().equals(chatId);
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectChatMessage(final LabyConnectChatMessageDeleteEvent event) {
        final UUID chatId = event.chat().getId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
            @Override
            public boolean test(final LabyConnectFriendWidget widget) {
                return widget.friend().chat().getId().equals(chatId);
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectChatMessageRead(final LabyConnectChatMessageReadEvent event) {
        final UUID chatId = event.chat().getId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
            @Override
            public boolean test(final LabyConnectFriendWidget widget) {
                return widget.friend().chat().getId().equals(chatId);
            }
        });
    }
    
    @Subscribe
    public void onUserUpdateData(final UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.labyAPI.minecraft().executeOnRenderThread(new Runnable() {
            @Override
            public void run() {
                final UUID uniqueId = event.gameUser().getUniqueId();
                LabyConnectFriendsActivity.this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
                    @Override
                    public boolean test(final LabyConnectFriendWidget widget) {
                        return widget.friend().getUniqueId().equals(uniqueId);
                    }
                });
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectFriendStatus(final LabyConnectFriendStatusEvent event) {
        final UUID uniqueId = event.friend().getUniqueId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
            @Override
            public boolean test(final LabyConnectFriendWidget widget) {
                return widget.friend().getUniqueId().equals(uniqueId);
            }
        });
        this.updateFriendCounter();
    }
    
    @Subscribe
    public void onLabyConnectUpdateSetting(final LabyConnectUpdateSettingEvent event) {
        this.reload();
    }
    
    @Subscribe
    public void onLabyConnectFriendServer(final LabyConnectFriendServerEvent event) {
        final UUID uniqueId = event.friend().getUniqueId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
            @Override
            public boolean test(final LabyConnectFriendWidget widget) {
                return widget.friend().getUniqueId().equals(uniqueId);
            }
        });
    }
    
    @Subscribe
    public void onUserPinEvent(final LabyConnectFriendPinUpdateEvent event) {
        this.labyAPI.minecraft().executeOnRenderThread(new Runnable() {
            @Override
            public void run() {
                final UUID uniqueId = event.friend().getUniqueId();
                LabyConnectFriendsActivity.this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, new Predicate<LabyConnectFriendWidget>(this) {
                    @Override
                    public boolean test(final LabyConnectFriendWidget widget) {
                        return widget.friend().getUniqueId().equals(uniqueId);
                    }
                });
            }
        });
    }
    
    public ListSession<LabyConnectEntryWidget> session() {
        return LabyConnectFriendsActivity.SESSION;
    }
    
    private void addFriend(@NotNull final Friend friend) {
        if (!this.isUserInFilter(friend)) {
            return;
        }
        this.listFriends.addChildAsync(new LabyConnectFriendWidget(this.contentDisplay, friend));
    }
    
    public void openChat(final UUID uuid) {
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        final Friend friend = session.getFriend(uuid);
        if (friend == null) {
            return;
        }
        final LabyConnectFriendWidget widget = new LabyConnectFriendWidget(this.contentDisplay, friend);
        if (this.contentDisplay != null) {
            widget.select();
        }
        LabyConnectFriendsActivity.SESSION.setSelectedEntry(widget);
    }
    
    public void openOutgoingRequests() {
        this.openOutgoingRequests("");
    }
    
    public void openOutgoingRequests(final String username) {
        this.displayScreen(new LabyConnectOutgoingRequestsActivity(username));
    }
    
    public void openSettings() {
        final ConfigProvider<LabyConnectConfigAccessor> configProvider = this.labyAPI.labyConnect().configProvider();
        final AbstractSettingRegistry labyconnect = configProvider.get().asRegistry("labyconnect");
        labyconnect.initialize();
        final Activity activity = labyconnect.createActivityLazy();
        activity.addInitializeRunnable(() -> activity.addStyle("activity/labyconnect/laby-connect-settings.lss"));
        activity.addCloseRunnable(new Runnable(this) {
            @Override
            public void run() {
                configProvider.save();
            }
        });
        this.contentDisplay.displayScreen(activity);
        LabyConnectFriendsActivity.SESSION.setSelectedEntry(null);
    }
    
    static {
        SESSION = new ListSession<LabyConnectEntryWidget>();
    }
}
