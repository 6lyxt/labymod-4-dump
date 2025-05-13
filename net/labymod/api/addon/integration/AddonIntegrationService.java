// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.integration;

import java.util.Collection;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AddonIntegrationService
{
    void registerIntegration(final String p0, final Class<? extends AddonIntegration> p1);
    
    void registerIntegration(final String p0, final Class<? extends AddonIntegration> p1, final Object... p2);
    
    Collection<AddonIntegrationMeta> getByIntegrating(final String p0);
    
    Collection<AddonIntegrationMeta> getByIntegrated(final String p0);
}
