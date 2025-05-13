// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ide.IdeUtil;
import java.io.InputStream;
import net.labymod.core.main.LabyMod;
import java.nio.file.Path;
import net.labymod.core.main.debug.filewatcher.WatchablePath;

public class PathResourceLocation extends AbstractResourceLocation
{
    private final WatchablePath watchablePath;
    
    public PathResourceLocation(final Path file, final String namespace, final String path) {
        super(namespace, path);
        this.watchablePath = LabyMod.references().watchablePathManager().create(file);
    }
    
    @Override
    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        return this.watchablePath.openStream();
    }
    
    @Override
    public boolean exists() {
        return this.watchablePath.exists();
    }
    
    public boolean isModified() {
        return this.watchablePath.isModified();
    }
}
