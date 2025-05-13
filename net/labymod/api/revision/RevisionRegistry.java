// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.revision;

import net.labymod.api.util.version.SemanticVersion;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RevisionRegistry
{
    void register(final Revision p0);
    
    @Nullable
    Revision getLastRevision(final String p0);
    
    @Nullable
    Revision getRevision(final String p0, final String p1);
    
    Map<SemanticVersion, Revision> getRevisions(final String p0);
    
    boolean isRelevant(final String p0, final String p1);
}
