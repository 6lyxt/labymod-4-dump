// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ServerAddressResolver
{
    ServerAddress resolve(final ServerAddress p0);
    
    void register(final ServerAddress p0);
}
