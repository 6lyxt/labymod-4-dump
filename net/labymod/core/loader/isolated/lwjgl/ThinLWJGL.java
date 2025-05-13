// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.lwjgl;

import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.Iterator;
import java.nio.file.Path;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;

public class ThinLWJGL implements IsolatedLibraryFinishHandler
{
    @Override
    public void onAccept(final IsolatedLibrary library) {
    }
    
    @Override
    public void onFinish() {
        for (final Path file : IsolatedClassLoaders.THIN_LWJGL_LOADER.getFiles()) {
            this.addThinLWJGL(file);
        }
    }
    
    private void addThinLWJGL(final Path library) {
        final PlatformClassloader classloader = PlatformEnvironment.getPlatformClassloader();
        classloader.addPath(library);
    }
}
