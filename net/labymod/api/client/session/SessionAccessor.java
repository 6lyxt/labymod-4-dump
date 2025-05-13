// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface SessionAccessor
{
    @Nullable
    Session getSession();
    
    void updateSession(final Session p0);
    
    Session createSession(final String p0, final UUID p1, final String p2);
    
    boolean isPremium();
    
    @Deprecated
    @Nullable
    default Session session() {
        return this.getSession();
    }
}
