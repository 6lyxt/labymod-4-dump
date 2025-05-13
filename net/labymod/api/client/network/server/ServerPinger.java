// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import net.labymod.api.client.resources.ResourceLocation;
import java.util.concurrent.CompletableFuture;
import java.io.IOException;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ServerPinger
{
    ServerInfo pingServer(final String p0, final ServerAddress p1, final int p2) throws IOException;
    
    CompletableFuture<ServerInfo> pingServerAsync(final String p0, final ServerAddress p1, final int p2);
    
    ResourceLocation getDefaultServerIcon();
}
