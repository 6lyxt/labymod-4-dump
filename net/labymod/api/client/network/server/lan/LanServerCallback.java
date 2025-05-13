// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.lan;

import net.labymod.api.client.network.server.ConnectableServerData;

public interface LanServerCallback
{
    void onServerAdd(final ConnectableServerData p0);
    
    void onServerRemove(final ConnectableServerData p0);
}
