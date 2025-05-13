// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session;

import java.util.concurrent.CompletableFuture;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MinecraftAuthenticator
{
    default CompletableFuture<Boolean> joinServer(final Session session, final String serverId) {
        return this.joinServer(session, serverId, 0);
    }
    
    CompletableFuture<Boolean> joinServer(final Session p0, final String p1, final int p2);
}
