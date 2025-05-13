// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketActionRequest extends Packet
{
    private UUID uuid;
    
    public PacketActionRequest() {
    }
    
    public PacketActionRequest(final UUID uuid) {
        this.uuid = uuid;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.uuid = UUID.fromString(buf.readString());
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.uuid.toString());
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
