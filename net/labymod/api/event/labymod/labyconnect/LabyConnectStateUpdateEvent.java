// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.LabyConnectState;

public class LabyConnectStateUpdateEvent extends LabyConnectEvent
{
    private final LabyConnectState state;
    
    public LabyConnectStateUpdateEvent(final LabyConnect api, final LabyConnectState state) {
        super(api);
        this.state = state;
    }
    
    public LabyConnectState state() {
        return this.state;
    }
}
