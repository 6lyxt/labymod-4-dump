// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketActionBroadcast extends Packet
{
    private UUID uniqueId;
    private ActionType type;
    private byte[] data;
    
    @Override
    public void read(final PacketBuffer buf) {
        this.uniqueId = new UUID(buf.readLong(), buf.readLong());
        this.type = ActionType.values()[buf.readShort() - 1];
        buf.readBytes(this.data = new byte[buf.readVarIntFromBuffer()]);
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeLong(this.uniqueId.getMostSignificantBits());
        buf.writeLong(this.uniqueId.getLeastSignificantBits());
        buf.writeShort((short)this.type.getId());
        buf.writeVarIntToBuffer(this.data.length);
        buf.writeBytes(this.data);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public ActionType getType() {
        return this.type;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public enum ActionType
    {
        EMOTE(1), 
        COSMETIC_CHANGE(2), 
        STICKER(3), 
        SPRAY(4);
        
        private final int id;
        
        private ActionType(final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
    }
}
