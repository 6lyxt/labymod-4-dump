// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayAcceptLanWorldInvite extends Packet
{
    private UUID player;
    private boolean host;
    
    public PacketPlayAcceptLanWorldInvite(final UUID player, final boolean host) {
        this.player = player;
        this.host = host;
    }
    
    public PacketPlayAcceptLanWorldInvite() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.player = buf.readUUID();
        this.host = buf.readBoolean();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUUID(this.player);
        buf.writeBoolean(this.host);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getPlayer() {
        return this.player;
    }
    
    public boolean isHost() {
        return this.host;
    }
}
