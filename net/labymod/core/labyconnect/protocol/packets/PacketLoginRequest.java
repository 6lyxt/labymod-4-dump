// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import java.util.ArrayList;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;
import java.util.List;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketLoginRequest extends Packet
{
    private List<DefaultIncomingFriendRequest> requesters;
    
    public PacketLoginRequest(final List<DefaultIncomingFriendRequest> requesters) {
        this.requesters = requesters;
    }
    
    public PacketLoginRequest() {
    }
    
    public List<DefaultIncomingFriendRequest> getRequests() {
        return this.requesters;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.requesters = new ArrayList<DefaultIncomingFriendRequest>();
        for (int a = buf.readInt(), i = 0; i < a; ++i) {
            this.requesters.add((DefaultIncomingFriendRequest)buf.readChatUser());
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeInt(this.getRequests().size());
        for (int i = 0; i < this.getRequests().size(); ++i) {
            buf.writeUser(this.getRequests().get(i));
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
