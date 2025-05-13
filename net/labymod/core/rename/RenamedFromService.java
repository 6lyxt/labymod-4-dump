// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.rename;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;
import java.net.URL;
import java.io.IOException;
import net.labymod.api.mappings.io.SrgMappingReader;
import org.objectweb.asm.commons.Remapper;
import net.labymod.api.mappings.MappingFile;
import net.labymod.api.volt.rename.ClassProvider;
import net.labymod.api.util.logging.Logging;

public class RenamedFromService
{
    private static final Logging LOGGER;
    private final ClassProvider classProvider;
    private final String namespace;
    private final ClassLoader classLoader;
    private MappingFile mappingFile;
    private Remapper remapper;
    
    public RenamedFromService(final String namespace, final ClassLoader classLoader, final ClassProvider.ResourceSupplier resourceSupplier) {
        this.classProvider = ClassProvider.getSingleton(resourceSupplier);
        this.namespace = namespace;
        this.classLoader = classLoader;
    }
    
    public void load() {
        final URL mappingsResource = this.classLoader.getResource("assets/" + this.namespace + "/mappings/" + this.namespace + ".srg");
        if (mappingsResource == null) {
            RenamedFromService.LOGGER.debug("", new Object[0]);
            return;
        }
        try (final InputStream stream = mappingsResource.openStream()) {
            final SrgMappingReader reader = new SrgMappingReader();
            this.mappingFile = reader.read(stream);
            this.remapper = new RenamedFromRemapper(this.mappingFile, this.classProvider);
        }
        catch (final IOException exception) {
            throw new IllegalStateException("Unable to load mappings", exception);
        }
    }
    
    public MappingFile getMappingFile() {
        return this.mappingFile;
    }
    
    @Nullable
    public Remapper getRemapper() {
        return this.remapper;
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger(RenamedFromService.class);
    }
}
