// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect;

import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LabyConnect
{
    void connect();
    
    void connect(final String p0, final int p1);
    
    void disconnect(final LabyConnectDisconnectEvent.Initiator p0, final String p1);
    
    LabyConnectState state();
    
    boolean isAuthenticated();
    
    boolean isConnectionEstablished();
    
    @Nullable
    LabyConnectSession getSession();
    
    String getLastDisconnectReason();
    
    ConfigProvider<LabyConnectConfigAccessor> configProvider();
    
    @Deprecated
    default boolean isConnected() {
        return this.isAuthenticated();
    }
}
