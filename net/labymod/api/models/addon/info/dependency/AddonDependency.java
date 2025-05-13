// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.addon.info.dependency;

import java.util.Optional;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;

public class AddonDependency
{
    private final String namespace;
    private final VersionCompatibility compatability;
    private final boolean optional;
    
    @Deprecated
    public AddonDependency(final String namespace, final boolean optional) {
        this(namespace, null, optional);
    }
    
    public AddonDependency(final String namespace, final Version compatability, final boolean optional) {
        this.namespace = namespace;
        this.compatability = compatability;
        this.optional = optional;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public Optional<VersionCompatibility> getCompatability() {
        return Optional.ofNullable(this.compatability);
    }
    
    public boolean isOptional() {
        return this.optional;
    }
}
