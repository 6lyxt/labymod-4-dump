// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import net.labymod.api.util.CharSequences;
import net.labymod.api.event.Phase;

public abstract class AbstractServer
{
    private final String name;
    
    public AbstractServer(final String name) {
        this.name = name;
    }
    
    public abstract void loginOrSwitch(final LoginPhase p0);
    
    public abstract void disconnect(final Phase p0);
    
    public boolean isServer(final ServerAddress address) {
        if (address == null) {
            return false;
        }
        String host = address.getHost();
        if (host == null) {
            return false;
        }
        host = CharSequences.toString(CharSequences.toLowerCase(host));
        return host.contains(this.name);
    }
    
    public enum LoginPhase
    {
        LOGIN, 
        SWITCH;
    }
}
