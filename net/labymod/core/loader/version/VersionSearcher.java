// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.version;

import java.util.Collection;
import java.nio.file.Path;
import net.labymod.api.loader.platform.PlatformClassloader;

public interface VersionSearcher
{
    void searchClasspath(final PlatformClassloader p0);
    
    String getMinecraftVersion();
    
    Path getClientJarPath();
    
    Collection<String> getMainClasses();
}
