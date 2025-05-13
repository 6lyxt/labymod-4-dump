// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketEncryptionRequest extends Packet
{
    private String serverId;
    private byte[] publicKey;
    private byte[] verifyToken;
    
    public PacketEncryptionRequest(final String serverId, final byte[] publicKey, final byte[] verifyToken) {
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }
    
    public PacketEncryptionRequest() {
    }
    
    public byte[] getPublicKey() {
        return this.publicKey;
    }
    
    public String getServerId() {
        return this.serverId;
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.serverId = buf.readString();
        this.publicKey = buf.readByteArray();
        this.verifyToken = buf.readByteArray();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.serverId);
        buf.writeByteArray(this.publicKey);
        buf.writeByteArray(this.verifyToken);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
