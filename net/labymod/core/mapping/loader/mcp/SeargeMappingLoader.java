// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.loader.mcp;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.Iterator;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import java.util.zip.ZipEntry;
import java.io.IOException;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import java.nio.file.OpenOption;
import net.labymod.api.util.io.zip.Zips;
import net.minecraftforge.srgutils.IMappingFile;
import net.minecraftforge.srgutils.IMappingBuilder;
import java.io.ByteArrayInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Paths;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Constants;
import java.util.Locale;
import java.nio.file.Path;
import net.labymod.api.mapping.loader.MappingLoader;

public class SeargeMappingLoader implements MappingLoader
{
    private final McpConfig mcpConfig;
    private final Path mappingsPath;
    
    public SeargeMappingLoader(final McpConfig mcpConfig) {
        this.mcpConfig = mcpConfig;
        this.mappingsPath = Paths.get(String.format(Locale.ROOT, Constants.Files.SEARGE_MAPPINGS_PATH, PlatformEnvironment.getRunningVersion()), new String[0]);
    }
    
    public McpConfig mcpConfig() {
        return this.mcpConfig;
    }
    
    @NotNull
    @Override
    public String getSourceNamespace() {
        return "official";
    }
    
    @NotNull
    @Override
    public String getTargetNamespace() {
        return "searge";
    }
    
    @NotNull
    @Override
    public MappingType type() {
        return MappingType.TSRG2;
    }
    
    @NotNull
    @Override
    public InputStream load() throws IOException {
        if (!Files.exists(this.mappingsPath, new LinkOption[0])) {
            final Response<WebInputStream> configResponse = ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(this.mcpConfig.getConfigUrl(), new Object[0])).executeSync();
            if (configResponse.hasException()) {
                throw configResponse.exception();
            }
            try (final InputStream stream = configResponse.get()) {
                Zips.readStream(stream, (entry, bytes) -> {
                    final String mappingsFileName = this.mcpConfig.isForge() ? "config/joined.tsrg" : "joined.srg";
                    if (!entry.getName().equals(mappingsFileName)) {
                        return Boolean.valueOf(false);
                    }
                    else {
                        final String sourceNamespace = this.getSourceNamespace();
                        final String targetNamespace = this.getTargetNamespace();
                        try (final InputStream mappingsStream = new ByteArrayInputStream(bytes)) {
                            final IMappingBuilder mappingBuilder = IMappingBuilder.create(new String[] { sourceNamespace, targetNamespace });
                            final IMappingFile mappings = IMappingFile.load(mappingsStream);
                            Zips.read(PlatformEnvironment.getObfuscatedJarPath(), (jarEntry, jarEntryData) -> {
                                this.loadFieldDescriptor(mappingBuilder, mappings, jarEntry, jarEntryData);
                                return Boolean.valueOf(false);
                            });
                            final IMappingFile finalMappings = mappingBuilder.build().getMap(sourceNamespace, targetNamespace);
                            finalMappings.write(this.mappingsPath, IMappingFile.Format.TSRG2, false);
                        }
                        return Boolean.valueOf(true);
                    }
                });
            }
        }
        return Files.newInputStream(this.mappingsPath, new OpenOption[0]);
    }
    
    private void loadFieldDescriptor(final IMappingBuilder mappingBuilder, final IMappingFile file, final ZipEntry entry, final byte[] data) {
        if (!entry.getName().endsWith(".class")) {
            return;
        }
        final ClassReader reader = new ClassReader(data);
        final ClassNode node = new ClassNode();
        reader.accept((ClassVisitor)node, 0);
        final IMappingFile.IClass classMapping = file.getClass(node.name);
        if (classMapping == null) {
            return;
        }
        final IMappingBuilder.IClass newClass = mappingBuilder.addClass(new String[] { classMapping.getOriginal(), classMapping.getMapped() });
        for (final IMappingFile.IMethod method : classMapping.getMethods()) {
            newClass.method(method.getDescriptor(), new String[] { method.getOriginal(), method.getMapped() });
        }
        for (final FieldNode field : node.fields) {
            final IMappingFile.IField fieldMapping = classMapping.getField(field.name);
            if (fieldMapping == null) {
                continue;
            }
            newClass.field(new String[] { fieldMapping.getOriginal(), fieldMapping.getMapped() }).descriptor(field.desc);
        }
    }
}
