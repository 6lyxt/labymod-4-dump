// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.transform;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable(named = true)
public interface ResourceTransformer
{
    byte[] transform(final byte[] p0);
    
    default int priority() {
        return 0;
    }
}
