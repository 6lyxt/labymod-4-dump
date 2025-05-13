// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon;

import net.labymod.api.util.version.SemanticVersion;
import org.jetbrains.annotations.ApiStatus;
import java.util.ArrayList;
import net.labymod.api.models.version.Version;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import java.util.List;

public class LoadedAddon
{
    private final ClassLoader classLoader;
    private final Class<?> mainClass;
    private final List<LoadedAddonClassTransformer> transformers;
    private final List<String> mixinConfigs;
    private final InstalledAddonInfo info;
    private final List<Widget> settings;
    private final boolean classpath;
    private Version version;
    
    public LoadedAddon(final ClassLoader classLoader, final Class<?> mainClass, final List<LoadedAddonClassTransformer> transformers, final List<String> mixinConfigs, final InstalledAddonInfo info) {
        this(classLoader, mainClass, transformers, mixinConfigs, info, false);
    }
    
    @ApiStatus.Internal
    public LoadedAddon(final ClassLoader classLoader, final Class<?> mainClass, final List<LoadedAddonClassTransformer> transformers, final List<String> mixinConfigs, final InstalledAddonInfo info, final boolean classPath) {
        this.settings = new ArrayList<Widget>();
        this.classLoader = classLoader;
        this.mainClass = mainClass;
        this.transformers = transformers;
        this.mixinConfigs = mixinConfigs;
        this.info = info;
        this.classpath = classPath;
    }
    
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    public Class<?> getMainClass() {
        return this.mainClass;
    }
    
    public List<LoadedAddonClassTransformer> getTransformers() {
        return this.transformers;
    }
    
    public List<String> getMixinConfigs() {
        return this.mixinConfigs;
    }
    
    public InstalledAddonInfo info() {
        return this.info;
    }
    
    public List<Widget> getSettings() {
        return this.settings;
    }
    
    public Version getVersion() {
        if (this.version == null) {
            this.version = new SemanticVersion(this.info().getVersion());
        }
        return this.version;
    }
    
    @ApiStatus.Internal
    public boolean isClasspath() {
        return this.classpath;
    }
    
    @Override
    public String toString() {
        return this.info().getNamespace() + " v" + this.info().getVersion();
    }
}
