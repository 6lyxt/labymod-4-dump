// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectUpdateSettingEvent extends LabyConnectEvent
{
    private final LabyConnectConfigAccessor config;
    
    public LabyConnectUpdateSettingEvent(final LabyConnect api, final LabyConnectConfigAccessor config) {
        super(api);
        this.config = config;
    }
    
    public LabyConnectConfigAccessor config() {
        return this.config;
    }
}
