// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.resources.pack;

import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Iterator;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import java.util.function.Function;
import java.util.function.Predicate;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.resources.pack.AbstractResourcePackScanner;

@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner
{
    @Override
    public void scan() {
        final aim resourceManager = efu.I().S();
        final List<IncompatibleResourcePack> incompatiblePacks = resourceManager.b().filter(this::isExternalPack).map((Function<? super Object, ? extends IncompatibleResourcePack>)this::scanPack).filter(IncompatibleResourcePack::isIncompatible).toList();
        this.onScanned(incompatiblePacks);
    }
    
    private boolean isExternalPack(final ahl pack) {
        return pack instanceof ahj || pack instanceof ahk;
    }
    
    private IncompatibleResourcePack scanPack(final ahl pack) {
        final Collection<String> ignoredFiles = this.collectIgnoredFiles(pack);
        final Set<String> unsupportedFiles = new HashSet<String>();
        for (final Map.Entry<String, Set<String>> entry : this.getBlacklistedFiles().entrySet()) {
            final String directory = entry.getKey();
            final Set<String> blacklistedFiles = entry.getValue();
            this.scanPack(pack, directory, location -> {
                this.analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation)location);
                return false;
            });
        }
        return new IncompatibleResourcePack(pack.a(), ignoredFiles, unsupportedFiles);
    }
    
    private void scanPack(final ahl pack, final String baseDirectory, final Predicate<abb> output) {
        pack.a(ahm.a, "minecraft", baseDirectory, (Predicate)output);
    }
    
    private Collection<String> collectIgnoredFiles(final ahl pack) {
        final Set<String> ignoredFiles = new HashSet<String>();
        for (final String namespace : pack.a(ahm.a)) {
            InputStream resource = null;
            try {
                resource = pack.a(ahm.a, new abb(namespace, "labymod/ignored_files.json"));
            }
            catch (final IOException ignored) {
                continue;
            }
            try (final InputStreamReader reader = new InputStreamReader(resource)) {
                this.collectIgnoredFiles(reader, ignoredFiles);
            }
            catch (final IOException exception) {
                VersionedResourcePackScanner.LOGGER.error("Could not read resource file", exception);
            }
        }
        return ignoredFiles;
    }
}
