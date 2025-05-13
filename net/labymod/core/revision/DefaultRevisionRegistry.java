// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.revision;

import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.models.version.Version;
import javax.inject.Inject;
import net.labymod.api.revision.SimpleRevision;
import java.util.HashMap;
import net.labymod.api.revision.Revision;
import net.labymod.api.util.version.SemanticVersion;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.revision.RevisionRegistry;

@Singleton
@Implements(RevisionRegistry.class)
public class DefaultRevisionRegistry implements RevisionRegistry
{
    private final Map<String, Map<SemanticVersion, Revision>> revisions;
    
    @Inject
    public DefaultRevisionRegistry() {
        this.revisions = new HashMap<String, Map<SemanticVersion, Revision>>();
        this.register(new PopupRevision("labymod", new SemanticVersion("4.1.0"), "2023-09-25", "textures/revision/4.1.0.png", true));
        this.register(new SimpleRevision("labymod", new SemanticVersion("4.1.4"), "2023-11-13"));
        this.register(new SimpleRevision("labymod", new SemanticVersion("4.1.11"), "2023-12-12"));
        this.register(new PopupRevision("labymod", new SemanticVersion("4.2.0"), "2024-04-01", "textures/revision/4.2.0.png", true));
    }
    
    @Override
    public void register(final Revision revision) {
        this.getRevisions(revision.getNamespace()).put(revision.version(), revision);
    }
    
    @Nullable
    @Override
    public Revision getLastRevision(final String namespace) {
        final Map<SemanticVersion, Revision> revisions = this.getRevisions(namespace);
        Version version = null;
        for (final SemanticVersion semanticVersion : revisions.keySet()) {
            if (version == null || semanticVersion.isGreaterThan(version)) {
                version = semanticVersion;
            }
        }
        return (version == null) ? null : revisions.get(version);
    }
    
    @Nullable
    @Override
    public Revision getRevision(final String namespace, final String version) {
        final Map<SemanticVersion, Revision> revisions = this.getRevisions(namespace);
        for (final Map.Entry<SemanticVersion, Revision> entry : revisions.entrySet()) {
            if (entry.getKey().toString().equals(version)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    @Override
    public Map<SemanticVersion, Revision> getRevisions(final String namespace) {
        return this.revisions.computeIfAbsent(namespace, k -> new HashMap());
    }
    
    @Override
    public boolean isRelevant(final String namespace, final String version) {
        final Revision lastRevision = this.getRevision(namespace, version);
        return lastRevision != null && lastRevision.isRelevant();
    }
}
