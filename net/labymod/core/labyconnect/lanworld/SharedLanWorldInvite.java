// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.lanworld;

import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan.LanAttachment;
import net.labymod.api.client.component.Component;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import java.util.UUID;

public class SharedLanWorldInvite
{
    private final UUID sender;
    private final UUID receiver;
    private final PacketPlayInviteLanWorld.LanWorldOptions options;
    private final long timestamp;
    private InviteStatus status;
    private Component connectError;
    @Nullable
    private LanAttachment attachment;
    
    public SharedLanWorldInvite(final UUID sender, final UUID receiver, final PacketPlayInviteLanWorld.LanWorldOptions options, final long timestamp) {
        this.status = InviteStatus.PENDING;
        this.connectError = null;
        this.sender = sender;
        this.receiver = receiver;
        this.options = options;
        this.timestamp = timestamp;
    }
    
    public UUID getSender() {
        return this.sender;
    }
    
    public UUID getReceiver() {
        return this.receiver;
    }
    
    public PacketPlayInviteLanWorld.LanWorldOptions options() {
        return this.options;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public Icon icon() {
        return (this.options.getIcon() != null) ? Icon.url(this.options.getIcon()) : Icon.head(this.sender);
    }
    
    public InviteStatus status() {
        return this.status;
    }
    
    public void setStatus(final InviteStatus status) {
        this.status = status;
        if (this.attachment != null) {
            this.attachment.update();
        }
    }
    
    public void setConnectError(final Component connectError) {
        this.connectError = connectError;
        if (this.attachment != null) {
            this.attachment.update();
        }
    }
    
    public Component getConnectError() {
        return this.connectError;
    }
    
    public void setAttachment(@Nullable final LanAttachment attachment) {
        this.attachment = attachment;
    }
    
    @Nullable
    public LanAttachment getAttachment() {
        return this.attachment;
    }
    
    @Override
    public String toString() {
        return "lan://" + this.sender.toString();
    }
    
    public enum InviteStatus
    {
        PENDING, 
        ACCEPTED, 
        REJECTED, 
        RETRACTED, 
        EXPIRED, 
        DISCONNECTED, 
        INCOMPATIBLE_MINECRAFT_VERSION, 
        ERROR, 
        INVALID;
    }
}
