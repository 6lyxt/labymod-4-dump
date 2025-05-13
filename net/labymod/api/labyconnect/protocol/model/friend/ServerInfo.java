// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.friend;

import net.labymod.api.util.AddressUtil;
import org.jetbrains.annotations.Nullable;

public interface ServerInfo
{
    int getPort();
    
    String getAddress();
    
    @Nullable
    String getGameModeName();
    
    boolean isSameServer(final ServerInfo p0);
    
    default String getDisplayInfo() {
        final int port = this.getPort();
        if (port == 25565) {
            return this.getAddress();
        }
        return this.getAddress() + ":" + port;
    }
    
    default boolean isLocalHost() {
        return AddressUtil.isLocalHost(this.getAddress());
    }
}
