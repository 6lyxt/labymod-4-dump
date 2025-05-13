// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.util.TextFormat;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.NotNull;
import java.net.URI;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;

public class LanAttachment extends AbstractURIAttachment
{
    @Nullable
    private final SharedLanWorldInvite invite;
    
    private LanAttachment(final URI uri, @NotNull final SharedLanWorldInvite invite) {
        super(uri);
        this.invite = invite;
    }
    
    private LanAttachment(final URI uri) {
        super(uri);
        this.invite = null;
    }
    
    @Override
    public Icon getIcon() {
        if (this.invite == null || this.invite.options().getIcon() == null) {
            return Textures.SpriteCommon.QUESTION_MARK;
        }
        return Icon.url(this.invite.options().getIcon());
    }
    
    @Override
    public Component getTitle() {
        if (this.invite == null) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.title", new Component[0]);
        }
        return Component.text(this.invite.options().getWorldName());
    }
    
    @Nullable
    @Override
    public Component getButtonComponent() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (this.invite == null || session == null || this.invite.status() != SharedLanWorldInvite.InviteStatus.PENDING || session.self().getUniqueId().equals(this.invite.getSender())) {
            return null;
        }
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.action.join", new Component[0]);
    }
    
    @Override
    public boolean isClickable() {
        return false;
    }
    
    @Override
    public Component getDescription() {
        if (this.invite != null && this.invite.status() == SharedLanWorldInvite.InviteStatus.ACCEPTED) {
            final Component connectError = this.invite.getConnectError();
            if (connectError != null) {
                return connectError;
            }
        }
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (this.invite == null || session == null || this.invite.status() != SharedLanWorldInvite.InviteStatus.PENDING) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description." + TextFormat.SNAKE_CASE.toLowerCamelCase(((this.invite != null) ? this.invite.status() : SharedLanWorldInvite.InviteStatus.EXPIRED).name()), new Component[0]);
        }
        if (session.self().getUniqueId().equals(this.invite.getSender())) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.sent", Component.text(this.invite.options().getWorldName()));
        }
        final Friend sender = session.getFriend(this.invite.getSender());
        final String senderName = (sender == null) ? "Unknown" : sender.getName();
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.pending", Component.text(senderName));
    }
    
    @Override
    public void open() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (this.invite == null || session == null || session.self().getUniqueId().equals(this.invite.getSender())) {
            return;
        }
        final Friend sender = session.getFriend(this.invite.getSender());
        if (sender == null) {
            return;
        }
        session.acceptLanWorldInvite(sender);
    }
    
    @Override
    public boolean shouldHideURI() {
        return true;
    }
    
    @NotNull
    @Override
    public Component toComponent() {
        return this.getTitle();
    }
    
    public static LanAttachment createExpired(final URI uri) {
        return new LanAttachment(uri);
    }
    
    public static LanAttachment createOf(final URI uri, final SharedLanWorldInvite invite) {
        final LanAttachment attachment = new LanAttachment(uri, invite);
        invite.setAttachment(attachment);
        return attachment;
    }
}
