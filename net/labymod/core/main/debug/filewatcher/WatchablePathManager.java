// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug.filewatcher;

import java.nio.file.Path;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class WatchablePathManager
{
    private final Map<String, WatchablePath> watchablePaths;
    
    @Inject
    public WatchablePathManager() {
        this.watchablePaths = new HashMap<String, WatchablePath>();
    }
    
    public WatchablePath create(Path path) {
        final String filename = path.getFileName().toString();
        NamedPathType pathType = NamedPathType.UNKNOWN;
        for (final NamedPathType value : NamedPathType.VALUES) {
            for (final String extension : value.getFileExtensions()) {
                if (filename.endsWith(extension)) {
                    pathType = value;
                    break;
                }
            }
        }
        final WatchablePath watchablePath = new WatchablePath(path, pathType);
        path = path.toAbsolutePath();
        path = path.normalize();
        this.watchablePaths.putIfAbsent(path.toAbsolutePath().toString(), watchablePath);
        return watchablePath;
    }
    
    public void onUpdate(final Path path, final WatchablePath.WatchableEventType eventType) {
        final WatchablePath watchablePath = this.watchablePaths.get(path.toAbsolutePath().toString());
        if (watchablePath == null) {
            return;
        }
        watchablePath.onUpdate(eventType);
    }
}
