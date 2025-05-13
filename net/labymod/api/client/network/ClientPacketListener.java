// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network;

import net.labymod.api.client.component.Component;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

public interface ClientPacketListener
{
    @Nullable
    NetworkPlayerInfo getNetworkPlayerInfo(final UUID p0);
    
    @Nullable
    NetworkPlayerInfo getNetworkPlayerInfo(final String p0);
    
    Collection<NetworkPlayerInfo> getNetworkPlayerInfos();
    
    default Collection<NetworkPlayerInfo> getShownOnlinePlayers() {
        return this.getNetworkPlayerInfos();
    }
    
    default int getPlayerCount() {
        return this.getShownOnlinePlayers().size();
    }
    
    void simulateKick(final Component p0);
}
