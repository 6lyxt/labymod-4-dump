// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.resources.pack;

import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.function.Consumer;
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
    private static final String DEFAULT_NAMESPACE = "minecraft";
    
    @Override
    public void scan() {
        final ach resourceManager = djz.C().N();
        final List<IncompatibleResourcePack> incompatiblePacks = resourceManager.b().filter(this::isExternalPack).map((Function<? super Object, ? extends IncompatibleResourcePack>)this::scanPack).filter(IncompatibleResourcePack::isIncompatible).toList();
        this.onScanned(incompatiblePacks);
    }
    
    private boolean isExternalPack(final abj pack) {
        return pack instanceof abh || pack instanceof abi;
    }
    
    private IncompatibleResourcePack scanPack(final abj pack) {
        final Collection<String> ignoredFiles = this.collectIgnoredFiles(pack);
        final Set<String> unsupportedFiles = new HashSet<String>();
        for (final Map.Entry<String, Set<String>> entry : this.getBlacklistedFiles().entrySet()) {
            final String directory = entry.getKey();
            final Set<String> blacklistedFiles = entry.getValue();
            this.scanPack(pack, directory, location -> this.analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation)location));
        }
        return new IncompatibleResourcePack(pack.a(), ignoredFiles, unsupportedFiles);
    }
    
    private void scanPack(final abj pack, final String baseDirectory, final Consumer<vk> output) {
        final Collection<vk> locations = pack.a(abk.a, "minecraft", baseDirectory, Integer.MAX_VALUE, s -> true);
        for (final vk location : locations) {
            output.accept(location);
        }
    }
    
    private Collection<String> collectIgnoredFiles(final abj pack) {
        final Set<String> ignoredFiles = new HashSet<String>();
        for (final String namespace : pack.a(abk.a)) {
            InputStream resource;
            try {
                resource = pack.a(abk.a, new vk(namespace, "labymod/ignored_files.json"));
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
