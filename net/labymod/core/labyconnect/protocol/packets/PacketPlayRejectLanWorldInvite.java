// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.api.labyconnect.LanWorldRejectReason;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayRejectLanWorldInvite extends Packet
{
    private UUID player;
    private LanWorldRejectReason reason;
    private boolean host;
    
    public PacketPlayRejectLanWorldInvite(final UUID player, final LanWorldRejectReason reason, final boolean host) {
        this.player = player;
        this.reason = reason;
        this.host = host;
    }
    
    public PacketPlayRejectLanWorldInvite() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.player = buf.readUUID();
        this.reason = LanWorldRejectReason.fromId(buf.readShort());
        this.host = buf.readBoolean();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUUID(this.player);
        buf.writeShort((short)this.reason.getId());
        buf.writeBoolean(this.host);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getPlayer() {
        return this.player;
    }
    
    public LanWorldRejectReason getReason() {
        return this.reason;
    }
    
    public boolean isHost() {
        return this.host;
    }
}
