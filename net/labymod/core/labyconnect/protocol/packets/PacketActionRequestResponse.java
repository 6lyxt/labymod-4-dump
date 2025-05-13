// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketActionRequestResponse extends Packet
{
    private UUID uuid;
    private short actionId;
    private byte[] data;
    
    public PacketActionRequestResponse() {
    }
    
    public PacketActionRequestResponse(final UUID uuid, final short actionId, final byte[] data) {
        this.uuid = uuid;
        this.actionId = actionId;
        this.data = data;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.uuid = buf.readUUID();
        this.actionId = buf.readShort();
        final int length = buf.readVarIntFromBuffer();
        buf.readBytes(this.data = new byte[length]);
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.uuid.toString());
        buf.writeShort(this.actionId);
        if (this.data == null) {
            buf.writeVarIntToBuffer(0);
        }
        else {
            buf.writeVarIntToBuffer(this.data.length);
            buf.writeBytes(this.data);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
