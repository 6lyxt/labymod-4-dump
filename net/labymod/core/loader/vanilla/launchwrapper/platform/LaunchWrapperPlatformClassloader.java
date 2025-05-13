// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.platform;

import java.io.IOException;
import net.minecraft.launchwrapper.loader.BaseClassLoader;
import java.util.Enumeration;
import org.objectweb.asm.commons.Remapper;
import net.labymod.api.loader.platform.Platform;
import net.minecraft.launchwrapper.classpath.Classpath;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import net.labymod.api.loader.platform.PlatformClassTransformer;
import net.minecraft.launchwrapper.loader.ChildClassLoader;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import net.minecraft.launchwrapper.Launch;
import java.net.URL;
import net.labymod.api.loader.platform.PlatformAccessWidener;
import net.labymod.api.loader.platform.PlatformClassloader;

public class LaunchWrapperPlatformClassloader implements PlatformClassloader
{
    private final PlatformAccessWidener platformAccessWidener;
    
    public LaunchWrapperPlatformClassloader() {
        this.platformAccessWidener = new LaunchWrapperPlatformAccessWidener();
    }
    
    @Override
    public void addUrl(final URL url) {
        Launch.classLoader.addURL(url);
    }
    
    @Override
    public Class<?> findClass(final String name) throws ClassNotFoundException {
        return Launch.classLoader.findClass(name);
    }
    
    @Override
    public void registerChildClassloader(final String name, final PlatformChildClassloader classloader) {
        Launch.classLoader.registerChildClassloader((ChildClassLoader)new WrappedPlatformChildClassloader(classloader));
    }
    
    @Override
    public void registerTransformer(final TransformerPhase phase, final String transformerName) {
        Launch.classLoader.registerTransformer(transformerName);
    }
    
    @Override
    public void registerTransformer(final TransformerPhase phase, final PlatformClassTransformer transformer) {
        IClassTransformer classTransformer = null;
        if (transformer instanceof final IClassTransformer classTransformer2) {
            classTransformer = classTransformer2;
        }
        if (classTransformer == null) {
            return;
        }
        Launch.classLoader.registerTransformer(classTransformer);
    }
    
    @Override
    public PlatformAccessWidener getAccessWidener() {
        return this.platformAccessWidener;
    }
    
    @Override
    public ClassLoader getPlatformClassloader() {
        return (ClassLoader)Launch.classLoader;
    }
    
    @Override
    public List<URL> getPotentialClasspathAddons() {
        return Classpath.potentialClasspathAddons();
    }
    
    @Override
    public List<URL> getClasspath() {
        return Launch.classLoader.getSources();
    }
    
    @Override
    public Platform getPlatform() {
        return Platform.VANILLA;
    }
    
    @Override
    public Remapper getRuntimeRemapper() {
        return null;
    }
    
    @Override
    public Enumeration<URL> getResources(final ClassLoader loader, final String name) throws IOException {
        if (loader instanceof final BaseClassLoader baseClassLoader) {
            return baseClassLoader.findResourceObjects(name);
        }
        return loader.getResources(name);
    }
}
