// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.event.Event;

public class LabyConnectEvent implements Event
{
    private final LabyConnect api;
    
    protected LabyConnectEvent(final LabyConnect api) {
        this.api = api;
    }
    
    public LabyConnect labyConnect() {
        return this.api;
    }
}
