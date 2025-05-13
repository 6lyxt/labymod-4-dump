// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.thirdparty;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@Referenceable
public interface SentryService
{
    void capture(final Throwable p0);
}
