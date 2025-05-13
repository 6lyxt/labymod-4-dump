// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.friend;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendNoteUpdateEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import java.util.Map;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendPinUpdateEvent;
import java.util.Set;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.LabyConnectSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.core.labyconnect.util.LoremIpsum;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultChat;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;

public class DefaultFriend extends DefaultUser implements Friend
{
    private String statusMessage;
    private DefaultServerInfo server;
    private long lastServerChange;
    private int unreadMessages;
    private final long lastInteraction;
    private final long lastTyping;
    private final String timeZone;
    private final long firstJoined;
    private final int contactAmount;
    private long lastOnline;
    private final boolean party;
    private final DefaultChat chat;
    
    public DefaultFriend(final UUID uniqueId, final String name, final UserStatus status, final String statusMessage, final DefaultServerInfo server, final int unreadMessages, final long lastInteraction, final long lastTyping, final String timeZone, final long lastOnline, final long firstJoined, final int contactAmount, final boolean party) {
        super(uniqueId, name);
        this.unreadMessages = 0;
        this.updateStatus(status);
        this.statusMessage = statusMessage;
        this.server = server;
        this.unreadMessages = unreadMessages;
        this.lastInteraction = lastInteraction;
        this.lastTyping = lastTyping;
        this.timeZone = timeZone;
        this.lastOnline = lastOnline;
        this.firstJoined = firstJoined;
        this.contactAmount = contactAmount;
        this.party = party;
        (this.chat = new DefaultChat(this.uniqueId)).addParticipant(this);
        if (Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment() && uniqueId.equals(UUID.fromString("3a440181-e057-46ae-ad79-79873f03ddbe"))) {
            LoremIpsum.addMessages(this.chat);
        }
    }
    
    @Nullable
    @Override
    public ServerInfo getServer() {
        return (this.server != null && !this.server.getAddress().isEmpty()) ? this.server : null;
    }
    
    @NotNull
    @Override
    public Chat chat() {
        return this.chat;
    }
    
    @Override
    public void remove() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session != null) {
            session.removeFriend(this.uniqueId);
        }
    }
    
    @Override
    public boolean isPinned() {
        final LabyConnectConfigAccessor config = Laby.labyAPI().labyConnect().configProvider().get();
        return config != null && config.pinnedFriends() != null && config.pinnedFriends().get().contains(this.uniqueId);
    }
    
    @Override
    public void pin() {
        this.setPin(true);
    }
    
    @Override
    public void unpin() {
        this.setPin(false);
    }
    
    private void setPin(final boolean pin) {
        final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        final LabyConnectConfigAccessor config = labyConnect.configProvider().get();
        final Set<UUID> uuids = config.pinnedFriends().get();
        if (pin) {
            uuids.add(this.uniqueId);
        }
        else {
            uuids.remove(this.uniqueId);
        }
        config.pinnedFriends().set(uuids);
        Laby.labyAPI().labyConnect().configProvider().save();
        Laby.fireEvent(new LabyConnectFriendPinUpdateEvent(labyConnect, this));
    }
    
    @Override
    public String getNote() {
        final LabyConnectConfigAccessor config = Laby.labyAPI().labyConnect().configProvider().get();
        return (String)config.friendNotes().get().getOrDefault((Object)this.uniqueId, (Object)null);
    }
    
    @Override
    public void openNoteEditor() {
        final LabyConnectConfigAccessor config = Laby.labyAPI().labyConnect().configProvider().get();
        final TextFieldWidget textFieldWidget = new TextFieldWidget();
        textFieldWidget.setText((String)config.friendNotes().get().getOrDefault((Object)this.uniqueId, (Object)""));
        textFieldWidget.maximalLength(64);
        PopupWidget.builder().title(Component.translatable("labymod.activity.labyconnect.chat.action.note", new Component[0])).widgetSupplier(() -> {
            textFieldWidget.setFocused(true);
            textFieldWidget.setCursorAtEnd();
            return textFieldWidget;
        }).confirmComponent(Component.translatable("labymod.ui.button.save", new Component[0])).confirmCallback(() -> {
            if (textFieldWidget.getText().isEmpty()) {
                config.friendNotes().get().remove(this.uniqueId);
            }
            else {
                config.friendNotes().get().put(this.uniqueId, textFieldWidget.getText());
            }
            Laby.labyAPI().labyConnect().configProvider().save();
            Laby.fireEvent(new LabyConnectFriendNoteUpdateEvent(Laby.labyAPI().labyConnect(), this));
        }).build().displayInOverlay();
    }
    
    public void increaseUnreadMessages() {
        ++this.unreadMessages;
    }
    
    public void setUnreadMessages(final int amount) {
        final boolean changed = this.unreadMessages != amount;
        this.unreadMessages = amount;
    }
    
    public int getUnreadMessages() {
        return this.unreadMessages;
    }
    
    public long getLastInteraction() {
        return this.lastInteraction;
    }
    
    public long getLastTyping() {
        return this.lastTyping;
    }
    
    public boolean isParty() {
        return this.party;
    }
    
    public boolean isFriendRequest() {
        return this instanceof DefaultIncomingFriendRequest;
    }
    
    @Override
    public boolean isOnline() {
        return this.userStatus() != UserStatus.OFFLINE;
    }
    
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    public String getTimeZone() {
        return this.timeZone;
    }
    
    @Override
    public long getLastOnline() {
        return this.lastOnline;
    }
    
    public long getFirstJoined() {
        return this.firstJoined;
    }
    
    public int getContactAmount() {
        return this.contactAmount;
    }
    
    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    @Override
    public void updateStatus(@NotNull final UserStatus userStatus) {
        super.updateStatus(userStatus);
        this.lastOnline = TimeUtil.getCurrentTimeMillis();
    }
    
    public boolean equals(final DefaultFriend friend) {
        return (this.party && friend.party) || (!friend.party && !this.party && friend.getUniqueId().equals(this.getUniqueId()));
    }
    
    public void setServer(final DefaultServerInfo server) {
        if ((this.server == null && server != null) || (this.server != null && !this.server.isSameServer(server))) {
            this.lastServerChange = TimeUtil.getCurrentTimeMillis();
        }
        this.server = server;
    }
    
    @Override
    public long getLastServerChange() {
        return this.lastServerChange;
    }
}
