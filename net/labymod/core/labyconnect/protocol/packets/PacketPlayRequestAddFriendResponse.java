// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayRequestAddFriendResponse extends Packet
{
    private String searched;
    private boolean requestSent;
    private String reason;
    
    public PacketPlayRequestAddFriendResponse(final String searched, final boolean sent) {
        this.searched = searched;
        this.requestSent = sent;
    }
    
    public PacketPlayRequestAddFriendResponse(final String searched, final boolean sent, final String reason) {
        this.searched = searched;
        this.requestSent = sent;
        this.reason = reason;
    }
    
    public PacketPlayRequestAddFriendResponse() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.searched = buf.readString();
        if (!(this.requestSent = buf.readBoolean())) {
            this.reason = buf.readString();
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.searched);
        buf.writeBoolean(this.requestSent);
        if (!this.isRequestSent()) {
            buf.writeString(this.reason);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public boolean isRequestSent() {
        return this.requestSent;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public String getSearched() {
        return this.searched;
    }
}
