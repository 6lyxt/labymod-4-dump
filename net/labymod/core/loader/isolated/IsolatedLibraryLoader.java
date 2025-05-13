// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.Iterator;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import net.labymod.core.loader.isolated.util.IsolatedClassLoader;
import java.util.Map;
import java.util.List;
import net.labymod.api.util.logging.Logging;

public abstract class IsolatedLibraryLoader
{
    protected static final Logging LOGGER;
    protected final List<IsolatedLibraryPredicate> predicates;
    protected final Map<String, IsolatedClassLoader> classLoaderMap;
    protected final Set<IsolatedLibrary> loadedLibraries;
    protected final List<IsolatedLibraryFinishHandler> finishHandlers;
    
    public IsolatedLibraryLoader() {
        this.predicates = new ArrayList<IsolatedLibraryPredicate>();
        this.classLoaderMap = new HashMap<String, IsolatedClassLoader>();
        this.loadedLibraries = new HashSet<IsolatedLibrary>();
        this.finishHandlers = new ArrayList<IsolatedLibraryFinishHandler>();
        this.registerClassLoader("org.lwjgl", IsolatedClassLoaders.LWJGL_CLASS_LOADER);
        this.registerClassLoader("org.lwjgl.lwjgl", IsolatedClassLoaders.LWJGL2_CLASS_LOADER);
        this.registerClassLoader("net.java.dev.jna", IsolatedClassLoaders.JNA_CLASS_LOADER);
        this.registerClassLoader("net.labymod", IsolatedClassLoaders.THIN_LWJGL_LOADER);
    }
    
    public void load(final ClassLoader loader) {
        this.onLoad(loader);
        this.onFinish();
    }
    
    protected abstract void onLoad(final ClassLoader p0);
    
    public void addFilter(final IsolatedLibraryPredicate predicate) {
        this.predicates.add(predicate);
    }
    
    public void addFinishHandler(final IsolatedLibraryFinishHandler finishHandler) {
        this.finishHandlers.add(finishHandler);
    }
    
    private void onFinish() {
        for (final IsolatedLibrary loadedLibrary : this.loadedLibraries) {
            for (final IsolatedLibraryFinishHandler finishHandler : this.finishHandlers) {
                finishHandler.onAccept(loadedLibrary);
            }
        }
        for (final IsolatedLibraryFinishHandler finishHandler2 : this.finishHandlers) {
            finishHandler2.onFinish();
        }
    }
    
    public void registerClassLoader(final String group, final IsolatedClassLoader classLoader) {
        this.registerClassLoader(group, classLoader, true);
    }
    
    public void registerClassLoader(final String group, final IsolatedClassLoader classLoader, final boolean isolated) {
        this.classLoaderMap.put(group, classLoader);
        if (!isolated) {
            PlatformEnvironment.getPlatformClassloader().registerChildClassloader(classLoader.getName(), classLoader);
        }
    }
    
    protected IsolatedClassLoader getClassLoader(final String group) {
        final IsolatedClassLoader loader = this.classLoaderMap.get(group);
        if (loader == null) {
            throw new IllegalStateException("No ClassLoader could be found for the group \"" + group);
        }
        return loader;
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger(IsolatedLibrary.class);
    }
}
