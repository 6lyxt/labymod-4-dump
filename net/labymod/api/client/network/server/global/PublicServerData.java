// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.global;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ConnectableServerData;

public class PublicServerData extends ConnectableServerData
{
    private final boolean partner;
    
    public PublicServerData(final String address, final boolean partner) {
        super(address, ServerAddress.parse(address), ServerType.THIRD_PARTY, null);
        this.partner = partner;
    }
    
    public boolean isPartner() {
        return this.partner;
    }
}
