// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.server;

import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.network.server.DefaultAbstractServerAddressResolver;

@Singleton
@Implements(ServerAddressResolver.class)
public class VersionedServerAddressResolver extends DefaultAbstractServerAddressResolver
{
}
