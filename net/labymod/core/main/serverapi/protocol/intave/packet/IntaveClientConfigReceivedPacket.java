// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.intave.packet;

import net.labymod.serverapi.api.packet.Packet;

public class IntaveClientConfigReceivedPacket implements Packet
{
    private boolean received;
    
    public IntaveClientConfigReceivedPacket() {
        this(false);
    }
    
    public IntaveClientConfigReceivedPacket(final boolean received) {
        this.received = received;
    }
    
    public boolean isReceived() {
        return this.received;
    }
    
    public void setReceived(final boolean received) {
        this.received = received;
    }
}
