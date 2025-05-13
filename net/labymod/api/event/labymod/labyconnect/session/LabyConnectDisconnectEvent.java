// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectDisconnectEvent extends LabyConnectEvent
{
    private final String reason;
    private final Initiator initiator;
    
    public LabyConnectDisconnectEvent(final LabyConnect labyConnect, final Initiator initiator, final String reason) {
        super(labyConnect);
        this.initiator = initiator;
        this.reason = reason;
    }
    
    public Initiator getInitiator() {
        return this.initiator;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public enum Initiator
    {
        USER, 
        CLIENT, 
        SERVER;
    }
}
