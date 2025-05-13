// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.loader;

import java.io.IOException;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.minecraftforge.srgutils.IMappingFile;
import net.labymod.api.mapping.provider.MappingProvider;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.loader.MappingReader;

public class FartMappingReader implements MappingReader
{
    @NotNull
    @Override
    public MappingType[] supportedTypes() {
        return MappingType.values();
    }
    
    @NotNull
    @Override
    public MappingProvider read(@NotNull final InputStream stream, @NotNull final String sourceNamespace, @NotNull final String targetNamespace, @NotNull final MappingType type) throws IOException {
        final IMappingFile mappingFile = IMappingFile.load(stream);
        return new FartMappingProvider(sourceNamespace, targetNamespace, mappingFile);
    }
}
