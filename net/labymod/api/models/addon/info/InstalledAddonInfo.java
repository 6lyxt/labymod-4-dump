// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.addon.info;

import java.util.Collections;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collection;
import java.util.Optional;
import java.util.ArrayList;
import net.labymod.api.models.version.Version;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.models.addon.annotation.AddonEntryPoint;
import java.util.Map;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.addon.info.dependency.MavenDependency;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.models.OperatingSystem;

public class InstalledAddonInfo
{
    private String namespace;
    private String[] incompatibles;
    private OperatingSystem[] os;
    private VersionCompatibility compatibleMinecraftVersions;
    private AddonMeta[] meta;
    private MavenDependency[] mavenDependencies;
    private AddonDependency[] addonDependencies;
    private String displayName;
    private String description;
    private String version;
    private String author;
    private String mainClass;
    private Map<AddonEntryPoint.Point, List<AddonEntrypoint>> entrypoints;
    private String[] earlyTransformers;
    private String[] transformers;
    private int requiredLabyModBuild;
    private String releaseChannel;
    private boolean flint;
    private String directory;
    private String fileName;
    private String fileHash;
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public String getDirectory() {
        return this.directory;
    }
    
    public Path getDirectoryPath() {
        return Paths.get(this.directory, new String[0]);
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public String getFileHash() {
        return this.fileHash;
    }
    
    public Path getPath() {
        return Paths.get(this.directory, new String[0]).resolve(this.fileName);
    }
    
    public VersionCompatibility getCompatibleMinecraftVersions() {
        return this.compatibleMinecraftVersions;
    }
    
    public String[] getIncompatibleAddons() {
        return this.incompatibles;
    }
    
    public AddonMeta[] meta() {
        return this.meta;
    }
    
    public boolean hasMeta(final AddonMeta meta) {
        for (final AddonMeta presentMeta : this.meta) {
            if (presentMeta == meta) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isIncompatibleWith(final String addonId) {
        for (final String incompatible : this.incompatibles) {
            if (incompatible.equalsIgnoreCase(addonId)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCurrentOsSupported() {
        if (this.os == null || this.os.length == 0) {
            return true;
        }
        final OperatingSystem platform = OperatingSystem.getPlatform();
        for (final OperatingSystem os : this.os) {
            if (platform == os) {
                return true;
            }
        }
        return false;
    }
    
    public OperatingSystem[] getSupportedOperatingSystems() {
        return this.os;
    }
    
    public MavenDependency[] getMavenDependencies() {
        return this.mavenDependencies;
    }
    
    @Deprecated
    public AddonDependency[] getAddonDependencies() {
        return this.addonDependencies;
    }
    
    public AddonDependency[] getCompatibleAddonDependencies(final Version version) {
        if (this.addonDependencies == null) {
            return new AddonDependency[0];
        }
        final List<AddonDependency> compatible = new ArrayList<AddonDependency>();
        for (final AddonDependency dependency : this.addonDependencies) {
            final Optional<VersionCompatibility> compatability = dependency.getCompatability();
            if (!compatability.isPresent() || compatability.get().isCompatible(version)) {
                compatible.add(dependency);
            }
        }
        return compatible.toArray(new AddonDependency[0]);
    }
    
    public String getMainClass() {
        return this.mainClass;
    }
    
    public int getRequiredLabyModBuild() {
        return this.requiredLabyModBuild;
    }
    
    public String getReleaseChannel() {
        return this.releaseChannel;
    }
    
    public Map<AddonEntryPoint.Point, List<AddonEntrypoint>> getEntrypoints() {
        return this.entrypoints;
    }
    
    public String[] getEarlyTransformers() {
        return this.earlyTransformers;
    }
    
    public String[] getTransformers() {
        return this.transformers;
    }
    
    public boolean isFlintAddon() {
        return this.flint;
    }
    
    public boolean hasAddonDependencies() {
        return this.addonDependencies != null && this.addonDependencies.length != 0;
    }
    
    public InstalledAddonInfo merge(final InstalledAddonInfo other) {
        final InstalledAddonInfo merged = new InstalledAddonInfo();
        merged.namespace = this.getValue(this.namespace, other.namespace);
        merged.displayName = this.getValue(this.displayName, other.displayName);
        merged.description = this.getValue(this.description, other.description);
        merged.version = this.getValue(this.version, other.version);
        merged.author = this.getValue(this.author, other.author);
        merged.mainClass = this.getValue(this.mainClass, other.mainClass);
        merged.os = ((this.os == null || this.os.length == 0) ? other.os : this.os);
        merged.compatibleMinecraftVersions = this.compatibleMinecraftVersions;
        merged.meta = this.meta;
        final List<MavenDependency> dependencies = new ArrayList<MavenDependency>();
        this.addAll(dependencies, this.mavenDependencies);
        this.addAll(dependencies, other.mavenDependencies);
        merged.mavenDependencies = dependencies.toArray(new MavenDependency[0]);
        final List<AddonDependency> addonDependencies = new ArrayList<AddonDependency>();
        this.addAll(addonDependencies, this.addonDependencies);
        this.addAll(addonDependencies, other.addonDependencies);
        merged.addonDependencies = addonDependencies.toArray(new AddonDependency[0]);
        final List<String> incompatibles = new ArrayList<String>();
        this.addAll(incompatibles, this.incompatibles);
        this.addAll(incompatibles, other.incompatibles);
        merged.incompatibles = incompatibles.toArray(new String[0]);
        final List<String> earlyTransformers = new ArrayList<String>();
        this.addAll(earlyTransformers, this.earlyTransformers);
        this.addAll(earlyTransformers, other.earlyTransformers);
        merged.earlyTransformers = earlyTransformers.toArray(new String[0]);
        final List<String> transformers = new ArrayList<String>();
        this.addAll(transformers, this.transformers);
        this.addAll(transformers, other.transformers);
        merged.transformers = transformers.toArray(new String[0]);
        final Map<AddonEntryPoint.Point, List<AddonEntrypoint>> entrypoints = new HashMap<AddonEntryPoint.Point, List<AddonEntrypoint>>();
        if (this.entrypoints != null) {
            for (final Map.Entry<AddonEntryPoint.Point, List<AddonEntrypoint>> entry : this.entrypoints.entrySet()) {
                entrypoints.computeIfAbsent((AddonEntryPoint.Point)entry.getKey(), k -> new ArrayList()).addAll(entry.getValue());
            }
        }
        if (other.entrypoints != null) {
            for (final Map.Entry<AddonEntryPoint.Point, List<AddonEntrypoint>> entry : other.entrypoints.entrySet()) {
                entrypoints.computeIfAbsent((AddonEntryPoint.Point)entry.getKey(), k -> new ArrayList()).addAll(entry.getValue());
            }
        }
        merged.entrypoints = entrypoints;
        return merged;
    }
    
    private String getValue(final String value, final String otherValue) {
        return (value == null || value.isEmpty()) ? otherValue : value;
    }
    
    private <T> void addAll(final Collection<? super T> collection, final T... elements) {
        if (elements == null) {
            return;
        }
        Collections.addAll(collection, elements);
    }
}
