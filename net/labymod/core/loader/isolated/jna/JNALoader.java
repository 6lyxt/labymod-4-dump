// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.jna;

import java.util.Iterator;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.nio.file.Path;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;

public class JNALoader implements IsolatedLibraryFinishHandler
{
    @Override
    public void onAccept(final IsolatedLibrary library) {
    }
    
    @Override
    public void onFinish() {
        for (final Path file : IsolatedClassLoaders.JNA_CLASS_LOADER.getFiles()) {
            PlatformEnvironment.getPlatformClassloader().addPath(file);
        }
    }
}
