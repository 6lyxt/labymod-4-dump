// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.connect;

import net.labymod.api.client.component.Component;

public interface AddressResolveCallback
{
    void updateStatus(final Component p0);
    
    void abort();
    
    default void abort(final Component reason) {
        this.updateStatus(reason);
        this.abort();
    }
}
