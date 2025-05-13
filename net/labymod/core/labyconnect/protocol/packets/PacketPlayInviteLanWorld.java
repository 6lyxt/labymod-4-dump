// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.client.entity.player.GameMode;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayInviteLanWorld extends Packet
{
    private boolean allowAll;
    private UUID player;
    private LanWorldOptions options;
    private long timeoutTimestamp;
    
    public PacketPlayInviteLanWorld(final SharedLanWorldInvite invite) {
        this(invite.getReceiver(), invite.options());
    }
    
    public PacketPlayInviteLanWorld(final LanWorldOptions options) {
        this.allowAll = true;
        this.options = options;
    }
    
    public PacketPlayInviteLanWorld(final UUID player, final LanWorldOptions options) {
        this.allowAll = false;
        this.player = player;
        this.options = options;
    }
    
    public PacketPlayInviteLanWorld() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.allowAll = buf.readBoolean();
        this.player = (this.allowAll ? null : buf.readUUID());
        this.options = new LanWorldOptions(buf.readString(), buf.readByte(), buf.readBoolean(), buf.readBoolean() ? buf.readString() : null);
        this.timeoutTimestamp = buf.readLong();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeBoolean(this.allowAll);
        if (!this.allowAll) {
            buf.writeUUID(this.player);
        }
        buf.writeString(this.options.worldName);
        buf.writeByte(this.options.gameMode);
        buf.writeBoolean(this.options.allowCheats);
        buf.writeBoolean(this.options.icon != null);
        if (this.options.icon != null) {
            buf.writeString(this.options.icon);
        }
        buf.writeLong(this.timeoutTimestamp);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public boolean isAllowAll() {
        return this.allowAll;
    }
    
    public UUID getPlayer() {
        return this.player;
    }
    
    public LanWorldOptions getOptions() {
        return this.options;
    }
    
    public long getTimeoutTimestamp() {
        return this.timeoutTimestamp;
    }
    
    public static class LanWorldOptions
    {
        private final String worldName;
        private final byte gameMode;
        private final boolean allowCheats;
        private final String icon;
        
        public LanWorldOptions(final String worldName, final byte gameMode, final boolean allowCheats, final String icon) {
            this.worldName = worldName;
            this.gameMode = gameMode;
            this.allowCheats = allowCheats;
            this.icon = ((icon != null && icon.startsWith("data:image/png;base64,")) ? icon : null);
        }
        
        public String getWorldName() {
            return this.worldName;
        }
        
        public GameMode gameMode() {
            return GameMode.fromId(this.gameMode);
        }
        
        public boolean isAllowCheats() {
            return this.allowCheats;
        }
        
        public String getIcon() {
            return this.icon;
        }
    }
}
