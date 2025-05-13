// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.entrypoint;

import net.labymod.api.models.version.Version;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface Entrypoint
{
    void initialize(final Version p0);
}
