// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.pack;

import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ResourcePackScanner
{
    void addBlacklist(final ResourceFile p0);
    
    void removeBlacklist(final ResourceFile p0);
    
    void removeBlacklist(final String p0);
    
    @ApiStatus.Internal
    void scan();
}
