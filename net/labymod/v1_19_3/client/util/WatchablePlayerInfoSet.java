// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.util;

import java.util.Iterator;
import net.labymod.v1_19_3.client.player.VersionedNetworkPlayerInfo;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.Set;
import net.labymod.core.watcher.set.WatchableSet;

public class WatchablePlayerInfoSet implements WatchableSet<eze>
{
    private final Set<NetworkPlayerInfo> playerInfos;
    
    public WatchablePlayerInfoSet(final Set<NetworkPlayerInfo> playerInfos) {
        this.playerInfos = playerInfos;
    }
    
    @Override
    public void onAdd(final eze playerInfo) {
        final NetworkPlayerInfo networkPlayerInfo = this.findNetworkPlayerInfo(playerInfo);
        if (networkPlayerInfo != null) {
            return;
        }
        this.playerInfos.add(new VersionedNetworkPlayerInfo(playerInfo));
    }
    
    @Override
    public void onRemove(final eze playerInfo) {
        final NetworkPlayerInfo networkPlayerInfo = this.findNetworkPlayerInfo(playerInfo);
        if (networkPlayerInfo == null) {
            return;
        }
        this.playerInfos.remove(networkPlayerInfo);
    }
    
    @Override
    public void onClear() {
        this.playerInfos.clear();
    }
    
    private NetworkPlayerInfo findNetworkPlayerInfo(final eze info) {
        for (final NetworkPlayerInfo playerInfo : this.playerInfos) {
            if (playerInfo.getMinecraftInfo().hashCode() == info.hashCode()) {
                return playerInfo;
            }
        }
        return null;
    }
}
