// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.loader.mcp;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.zip.ZipEntry;
import java.io.IOException;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import java.nio.file.OpenOption;
import net.labymod.api.util.io.zip.Zips;
import java.util.HashMap;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.nio.file.Paths;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Constants;
import java.util.Locale;
import java.nio.file.Path;
import net.labymod.api.mapping.loader.MappingLoader;

public class McpMappingLoader implements MappingLoader
{
    private final SeargeMappingLoader seargeMappingLoader;
    private final Path mappingsPath;
    
    public McpMappingLoader(final SeargeMappingLoader seargeMappingLoader) {
        this.seargeMappingLoader = seargeMappingLoader;
        this.mappingsPath = Paths.get(String.format(Locale.ROOT, Constants.Files.MCP_MAPPINGS_PATH, PlatformEnvironment.getRunningVersion()), new String[0]);
    }
    
    private static void readCsv(final byte[] csvData, final Map<String, String> target) {
        final String csv = new String(csvData, StandardCharsets.UTF_8);
        final String[] lines = csv.split("\n");
        for (int i = 1; i < lines.length; ++i) {
            final String line = lines[i];
            final String[] parts = line.split(",");
            if (parts.length >= 2) {
                target.put(parts[0], parts[1]);
            }
        }
    }
    
    private static String replacement(final String content, final Map<String, String> replacements) {
        final StringBuilder builder = new StringBuilder(content);
        for (final Map.Entry<String, String> entry : replacements.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            int nextSearchStart;
            for (int start = builder.indexOf(key, 0); start > -1; start = builder.indexOf(key, nextSearchStart)) {
                final int end = start + key.length();
                nextSearchStart = start + value.length();
                builder.replace(start, end, value);
            }
        }
        return builder.toString();
    }
    
    @NotNull
    @Override
    public String getSourceNamespace() {
        return "official";
    }
    
    @NotNull
    @Override
    public String getTargetNamespace() {
        return "named";
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
            final Response<WebInputStream> mappingsResponse = ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(this.seargeMappingLoader.mcpConfig().getMappingUrl(), new Object[0])).executeSync();
            if (mappingsResponse.hasException()) {
                throw mappingsResponse.exception();
            }
            final Map<String, String> methodMappings = new HashMap<String, String>();
            final Map<String, String> fieldMappings = new HashMap<String, String>();
            try (final InputStream stream = mappingsResponse.get()) {
                Zips.readStream(stream, (entry, bytes) -> {
                    if (entry.getName().equals("methods.csv")) {
                        readCsv(bytes, methodMappings);
                    }
                    if (entry.getName().equals("fields.csv")) {
                        readCsv(bytes, fieldMappings);
                    }
                    return Boolean.valueOf(!methodMappings.isEmpty() && !fieldMappings.isEmpty());
                });
            }
            try (final InputStream seargeMappingsStream = this.seargeMappingLoader.load()) {
                String seargeMappings = new String(seargeMappingsStream.readAllBytes(), StandardCharsets.UTF_8);
                seargeMappings = replacement(seargeMappings, methodMappings);
                seargeMappings = replacement(seargeMappings, fieldMappings);
                Files.writeString(this.mappingsPath, seargeMappings, new OpenOption[0]);
            }
        }
        return Files.newInputStream(this.mappingsPath, new OpenOption[0]);
    }
}
