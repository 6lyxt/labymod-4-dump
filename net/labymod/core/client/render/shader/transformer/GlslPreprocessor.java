// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader.transformer;

import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;
import java.io.IOException;
import java.util.Locale;
import net.labymod.api.util.io.IOUtil;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.api.client.render.shader.ShaderException;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import java.util.List;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import java.util.regex.Pattern;

public final class GlslPreprocessor
{
    public static final Pattern IMPORT_PATTER;
    public static final String KEYWORD_MINECRAFT_GREATER_OR_EQUALS = "mcgoe";
    public static final String KEYWORD_MINECRAFT_LOWER_OR_EQUALS = "mcloe";
    public static final String KEYWORD_MINECRAFT_EQUALS = "mce";
    public static final String KEYWORD_MINECRAFT_LOWER = "mcl";
    public static final String KEYWORD_MINECRAFT_GREATER = "mcg";
    private final Map<ResourceLocation, String> sources;
    private final List<String> shaderSources;
    private final LabyAPI labyAPI;
    private boolean wrongVersion;
    
    public GlslPreprocessor() {
        this.labyAPI = Laby.labyAPI();
        this.sources = new HashMap<ResourceLocation, String>();
        this.shaderSources = new ArrayList<String>();
    }
    
    @NotNull
    public String process(final String shaderSource) {
        try {
            this.importShader(shaderSource);
        }
        catch (final ShaderException exception) {
            throw new RuntimeException(exception);
        }
        final StringBuilder builder = new StringBuilder();
        for (final String sourceLine : shaderSource.split("\n")) {
            if (!sourceLine.startsWith("#laby-import")) {
                final String trimmedSourceLine = sourceLine.trim();
                if (!this.parseMinecraftVersionCheck(trimmedSourceLine)) {
                    if (trimmedSourceLine.startsWith("#endmc")) {
                        this.wrongVersion = false;
                    }
                    else if (!this.wrongVersion) {
                        builder.append(sourceLine).append("\n");
                        if (sourceLine.startsWith("#version")) {
                            for (final String source : this.shaderSources) {
                                builder.append(source).append("\n");
                            }
                            this.shaderSources.clear();
                        }
                    }
                }
            }
        }
        return builder.toString();
    }
    
    private void importShader(final String shaderSource) throws ShaderException {
        final Matcher matcher = GlslPreprocessor.IMPORT_PATTER.matcher(shaderSource);
        while (matcher.find()) {
            final String id = matcher.group(1).replace("\"", "");
            final ResourceLocation shaderLocation = this.location(id);
            String source = this.sources.get(shaderLocation);
            if (source != null) {
                this.shaderSources.add(source);
            }
            else {
                source = this.readShaderSource(shaderLocation);
                this.sources.put(shaderLocation, source);
                this.shaderSources.add(source);
            }
        }
    }
    
    @NotNull
    private String readShaderSource(@NotNull final ResourceLocation location) throws ShaderException {
        try {
            return IOUtil.toString(location.openStream(), StandardCharsets.UTF_8);
        }
        catch (final IOException exception) {
            throw new ShaderException(String.format(Locale.ROOT, "The shader source could not be read! (%s)", location), exception);
        }
    }
    
    @NotNull
    private ResourceLocation location(@NotNull final String id) {
        final String[] split = id.split(":", 2);
        return ResourceLocation.create(split[0], split[1]);
    }
    
    private boolean parseMinecraftVersionCheck(final String sourceLine) {
        if (!sourceLine.contains("(") || !sourceLine.contains(")")) {
            return false;
        }
        if (!sourceLine.startsWith("#")) {
            return false;
        }
        final int index = sourceLine.indexOf(40);
        final String keyword = sourceLine.substring(1, index);
        final Version runningVersion = this.labyAPI.labyModLoader().version();
        final Version version = VersionDeserializer.from(this.readVersion(sourceLine));
        final String s = keyword;
        switch (s) {
            case "mce": {
                if (runningVersion.equals(version)) {
                    break;
                }
                this.wrongVersion = true;
                break;
            }
            case "mcg": {
                if (runningVersion.isGreaterThan(version)) {
                    break;
                }
                this.wrongVersion = true;
                break;
            }
            case "mcl": {
                if (runningVersion.isLowerThan(version)) {
                    break;
                }
                this.wrongVersion = true;
                break;
            }
            case "mcgoe": {
                if (runningVersion.isGreaterThan(version)) {
                    break;
                }
                if (runningVersion.equals(version)) {
                    break;
                }
                this.wrongVersion = true;
                break;
            }
            case "mcloe": {
                if (runningVersion.isLowerThan(version)) {
                    break;
                }
                if (runningVersion.equals(version)) {
                    break;
                }
                this.wrongVersion = true;
                break;
            }
        }
        return true;
    }
    
    @NotNull
    private String readVersion(@NotNull String line) {
        int index = line.indexOf(34);
        line = line.substring(index + 1);
        index = line.indexOf(34);
        line = line.substring(0, index);
        return line;
    }
    
    static {
        IMPORT_PATTER = Pattern.compile("^#laby-import\\s+(\"*[\\w]+:[\\w/.]+)[ \\t]*.*", 8);
    }
}
