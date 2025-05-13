// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.pack;

import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import java.util.ArrayList;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.resources.pack.AbstractResourcePackScanner;

@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner
{
    private static final String DEFAULT_NAMESPACE = "minecraft";
    
    @Override
    public void scan() {
        final bnm packRepository = ave.A().R();
        final List<bnm.a> entries = packRepository.c();
        final List<IncompatibleResourcePack> incompatibleResourcePacks = new ArrayList<IncompatibleResourcePack>();
        for (final bnm.a entry : entries) {
            final bnk resourcePack = entry.c();
            if (this.isExternalPack(resourcePack)) {
                continue;
            }
            final IncompatibleResourcePack incompatibleResourcePack = this.scanPack(resourcePack);
            if (incompatibleResourcePack.isCompatible()) {
                continue;
            }
            incompatibleResourcePacks.add(incompatibleResourcePack);
        }
        this.onScanned(incompatibleResourcePacks);
    }
    
    private boolean isExternalPack(final bnk pack) {
        return pack instanceof bnc;
    }
    
    private IncompatibleResourcePack scanPack(final bnk pack) {
        final Collection<String> ignoredFiles = this.collectIgnoredFiles(pack);
        final Set<String> unsupportedFiles = new HashSet<String>();
        for (final Map.Entry<String, Set<String>> entry : this.getBlacklistedFiles().entrySet()) {
            final String directory = entry.getKey();
            final Set<String> blacklistedFiles = entry.getValue();
            this.scanPack(pack, directory, location -> this.analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation)location));
        }
        return new IncompatibleResourcePack(pack.b(), ignoredFiles, unsupportedFiles);
    }
    
    private void scanPack(final bnk pack, final String baseDirectory, final Consumer<jy> output) {
        ResourceLister.listResources(pack, "minecraft", baseDirectory, output);
    }
    
    private Collection<String> collectIgnoredFiles(final bnk pack) {
        final Set<String> ignoredFiles = new HashSet<String>();
        for (final String namespace : pack.c()) {
            final jy location = new jy(namespace, "labymod/ignored_files.json");
            if (!pack.b(location)) {
                continue;
            }
            try (final InputStreamReader reader = new InputStreamReader(pack.a(location))) {
                this.collectIgnoredFiles(reader, ignoredFiles);
            }
            catch (final IOException exception) {
                VersionedResourcePackScanner.LOGGER.error("Could not read resource file", exception);
            }
        }
        return ignoredFiles;
    }
}
