// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Iterator;
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
        final aue resourceManager = fgo.Q().ab();
        final List<IncompatibleResourcePack> incompatiblePacks = resourceManager.b().filter(this::isExternalPack).map((Function<? super Object, ? extends IncompatibleResourcePack>)this::scanPack).filter(IncompatibleResourcePack::isIncompatible).toList();
        this.onScanned(incompatiblePacks);
    }
    
    private boolean isExternalPack(final asq pack) {
        if (pack instanceof final WrappedPackResources wrappedPackResources) {
            return this.isExternalPack(wrappedPackResources.delegate());
        }
        return pack instanceof asn || pack instanceof ast || pack instanceof asj;
    }
    
    private IncompatibleResourcePack scanPack(final asq pack) {
        final Collection<String> ignoredFiles = this.collectIgnoredFiles(pack);
        final Set<String> unsupportedFiles = new HashSet<String>();
        for (final Map.Entry<String, Set<String>> entry : this.getBlacklistedFiles().entrySet()) {
            final String directory = entry.getKey();
            final Set<String> blacklistedFiles = entry.getValue();
            this.scanPack(pack, directory, (location, supplier) -> this.analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation)location));
        }
        return new IncompatibleResourcePack(pack.b(), ignoredFiles, unsupportedFiles);
    }
    
    private void scanPack(final asq pack, final String baseDirectory, final asq.a output) {
        pack.a(ass.a, "minecraft", baseDirectory, output);
    }
    
    private Collection<String> collectIgnoredFiles(final asq pack) {
        final Set<String> ignoredFiles = new HashSet<String>();
        for (final String namespace : pack.a(ass.a)) {
            final atw<InputStream> resource = (atw<InputStream>)pack.a(ass.a, akr.a(namespace, "labymod/ignored_files.json"));
            if (resource == null) {
                continue;
            }
            try (final InputStreamReader reader = new InputStreamReader((InputStream)resource.get())) {
                this.collectIgnoredFiles(reader, ignoredFiles);
            }
            catch (final IOException exception) {
                VersionedResourcePackScanner.LOGGER.error("Could not read resource file", exception);
            }
        }
        return ignoredFiles;
    }
}
