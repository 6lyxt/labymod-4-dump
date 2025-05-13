// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import java.nio.file.OpenOption;
import net.labymod.api.util.HashUtil;
import net.labymod.api.mapping.remap.JarRemapper;
import net.labymod.core.mapping.transformer.AccessWidenerTransformer;
import net.minecraftforge.fart.api.Transformer;
import net.labymod.core.mapping.transformer.MixinTransformer;
import net.labymod.core.mapping.remap.FartJarRemapper;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.IOException;
import java.nio.file.Path;
import net.labymod.api.mapping.MixinRemapperInjector;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.util.logging.Logging;

public class AddonRemapper
{
    private static final String VERSION;
    private static final Logging LOGGER;
    private MappingService mappingService;
    private MappingProvider mappingProvider;
    private boolean initialized;
    
    private void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.mappingService = MappingService.instance();
        this.mappingProvider = this.mappingService.mappings("official", "named");
        MixinRemapperInjector.instance().injectRemapper(this.mappingService.mixinRemapper(this.mappingProvider));
    }
    
    public Path remap(final Path source) {
        return this.remap(new ChecksumPath(source));
    }
    
    public Path remap(final ChecksumPath checksumSource) {
        this.initialize();
        final Path source = checksumSource.source();
        final Path workingDirectory = source.getParent().resolve("remapped").resolve(AddonRemapper.VERSION);
        boolean validChecksum = false;
        try {
            validChecksum = checksumSource.checkChecksum(workingDirectory);
        }
        catch (final IOException exception) {
            AddonRemapper.LOGGER.error("Failed to check checksum", exception);
        }
        final Path destination = workingDirectory.resolve(source.getFileName().toString());
        if (validChecksum && Files.exists(destination, new LinkOption[0])) {
            return destination;
        }
        final JarRemapper remapper = this.mappingService.jarRemapper(this.mappingProvider).entry(source, destination).library(PlatformEnvironment.getObfuscatedJarPath()).build();
        if (remapper instanceof final FartJarRemapper fartRemapper) {
            fartRemapper.addTransformer((Transformer)new MixinTransformer(this.mappingProvider));
            fartRemapper.addTransformer((Transformer)new AccessWidenerTransformer(this.mappingProvider));
        }
        try {
            AddonRemapper.LOGGER.debug(remapper.execute(), new Object[0]);
        }
        catch (final Exception exception2) {
            AddonRemapper.LOGGER.error("Remapping failed", exception2);
            return source;
        }
        return destination;
    }
    
    static {
        VERSION = PlatformEnvironment.getRunningVersion();
        LOGGER = Logging.getLogger();
    }
    
    record ChecksumPath(Path source) {
        public boolean checkChecksum(final Path destination) throws IOException {
            final Path checksumPath = this.getChecksumPath(destination);
            if (!Files.exists(checksumPath, new LinkOption[0])) {
                final byte[] sourceBytes = Files.readAllBytes(this.source);
                final String checksum = HashUtil.md5Hex(sourceBytes);
                this.saveChecksum(checksumPath, checksum);
                return false;
            }
            final String checksumValue = Files.readString(checksumPath);
            final byte[] sourceBytes2 = Files.readAllBytes(this.source);
            final String sourceChecksum = HashUtil.md5Hex(sourceBytes2);
            if (!checksumValue.equals(sourceChecksum)) {
                AddonRemapper.LOGGER.warn("Checksum mismatch for file: {}", this.source);
                this.saveChecksum(checksumPath, sourceChecksum);
                return false;
            }
            return true;
        }
        
        private void saveChecksum(final Path checksumPath, final String checksum) throws IOException {
            Files.writeString(checksumPath, checksum, new OpenOption[0]);
        }
        
        public Path getChecksumPath(final Path destination) {
            return destination.resolve(this.source.getFileName().toString() + ".checksum");
        }
    }
}
