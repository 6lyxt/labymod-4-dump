// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug.filewatcher;

import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
import java.io.InputStream;
import java.nio.file.Path;

public class WatchablePath
{
    private final Path path;
    private final PathType pathType;
    private boolean nioCalled;
    private boolean exists;
    private boolean modified;
    
    public WatchablePath(final Path path) {
        this(path, NamedPathType.UNKNOWN);
    }
    
    public WatchablePath(final Path path, final PathType pathType) {
        this.path = path;
        this.pathType = pathType;
    }
    
    public Path getPath() {
        return this.path;
    }
    
    public InputStream openStream() throws IOException {
        return IOUtil.newInputStream(this.path);
    }
    
    public boolean exists() {
        if (this.nioCalled && this.exists) {
            return true;
        }
        this.nioCalled = true;
        return this.exists = IOUtil.exists(this.path);
    }
    
    public void onUpdate(final WatchableEventType eventType) {
        switch (eventType.ordinal()) {
            case 1: {
                this.modified = true;
                break;
            }
            case 2: {
                this.exists = true;
                this.modified = true;
                break;
            }
            case 3: {
                this.exists = false;
                break;
            }
        }
    }
    
    public boolean isModified() {
        final boolean modified = this.modified;
        this.modified = false;
        return modified;
    }
    
    public enum WatchableEventType
    {
        UNKNOWN, 
        MODIFY, 
        CREATE, 
        DELETE, 
        OVERFLOW;
    }
}
