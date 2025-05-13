// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketActionPlay extends Packet
{
    private short requestId;
    private short actionType;
    private byte[] data;
    
    public PacketActionPlay(final int requestId, final PacketActionBroadcast.ActionType actionType, final byte[] data) {
        this(requestId, actionType.getId(), data);
    }
    
    public PacketActionPlay(final int requestId, final int action, final byte[] data) {
        this.requestId = (short)requestId;
        this.actionType = (short)action;
        this.data = data;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.requestId = buf.readShort();
        this.actionType = buf.readShort();
        final int length = buf.readVarIntFromBuffer();
        buf.readBytes(this.data = new byte[length]);
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeShort(this.requestId);
        buf.writeShort(this.actionType);
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
    }
}
