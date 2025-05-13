// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.shader.preprocessor;

import net.labymod.api.util.io.IOUtil;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.function.Consumer;
import java.util.Objects;
import java.util.Iterator;
import java.util.Collections;
import java.io.IOException;
import java.util.Collection;
import net.labymod.api.client.gfx.opengl.NamedOpenGLVersion;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.gfx.shader.Shader;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class GlslPreprocessor
{
    private static final Processor LEGACY_SHADER_PROCESSOR;
    private static final ResourceLocation BUILT_IN_DEFAULT;
    private static final String IMPORT_KEYWORD = "#laby-import";
    private static final String VERSION_KEYWORD = "#version";
    private static final String NEW_LINE = "\n";
    private static final Pattern IMPORT_PATTERN;
    private final Map<ResourceLocation, String> cachedShaderSources;
    private final List<String> builtInSources;
    private final List<String> sources;
    private boolean lazyLoad;
    
    @Inject
    public GlslPreprocessor() {
        this.cachedShaderSources = new HashMap<ResourceLocation, String>();
        this.builtInSources = new ArrayList<String>();
        this.sources = new ArrayList<String>();
    }
    
    public String process(final String source, final Shader shader) throws IOException {
        if (!this.lazyLoad) {
            this.registerBuiltInShader(GlslPreprocessor.BUILT_IN_DEFAULT);
            this.lazyLoad = true;
        }
        this.importShader(source);
        final List<String> lines = new ArrayList<String>();
        for (final String line : source.split("\n")) {
            if (!line.startsWith("#laby-import")) {
                lines.add(line);
                if (line.startsWith("#version")) {
                    this.applyImports(lines, this.builtInSources, false);
                    this.applyImports(lines, this.sources, true);
                }
            }
        }
        boolean needsLegacyShaderSource = OperatingSystem.isOSX() && PlatformEnvironment.isNoShader();
        if (!NamedOpenGLVersion.GL30.isSupported()) {
            needsLegacyShaderSource = true;
        }
        final String[] oldLines = lines.toArray(new String[0]);
        lines.clear();
        for (String line2 : oldLines) {
            if (needsLegacyShaderSource) {
                line2 = GlslPreprocessor.LEGACY_SHADER_PROCESSOR.process(line2, shader.type());
            }
            if (line2 != null) {
                lines.add(line2);
                if (line2.startsWith("#version")) {
                    lines.addAll(shader.shaderConstants().getDefinedConstants());
                }
            }
        }
        GlslPreprocessor.LEGACY_SHADER_PROCESSOR.finished();
        return String.join("\n", lines);
    }
    
    private void applyImports(final List<String> lines, final List<String> importSources, final boolean clear) {
        for (final String importSource : importSources) {
            Collections.addAll(lines, importSource.split("\n"));
            lines.add("\n");
        }
        if (clear) {
            importSources.clear();
        }
    }
    
    private void importShader(final String shaderSource) throws IOException {
        final Matcher matcher = GlslPreprocessor.IMPORT_PATTERN.matcher(shaderSource);
        while (matcher.find()) {
            final String importShaderLocation = matcher.group(1).replace("\"", "");
            final String[] split = importShaderLocation.split(":", 2);
            final ResourceLocation create;
            final ResourceLocation shaderLocation = create = ResourceLocation.create(split[0], split[1]);
            final List<String> sources = this.sources;
            Objects.requireNonNull(sources);
            this.registerShader(create, (Consumer<String>)sources::add, false);
        }
    }
    
    private void registerBuiltInShader(final ResourceLocation location) throws IOException {
        final List<String> builtInSources = this.builtInSources;
        Objects.requireNonNull(builtInSources);
        this.registerShader(location, (Consumer<String>)builtInSources::add, true);
    }
    
    private void registerShader(final ResourceLocation location, final Consumer<String> sourceConsumer, final boolean builtIn) throws IOException {
        if (!builtIn && Objects.equals(location, GlslPreprocessor.BUILT_IN_DEFAULT)) {
            return;
        }
        String source = this.cachedShaderSources.get(location);
        if (source != null) {
            sourceConsumer.accept(source);
            return;
        }
        source = IOUtil.toString(location.openStream(), StandardCharsets.UTF_8);
        sourceConsumer.accept(source);
        this.cachedShaderSources.put(location, source);
    }
    
    static {
        LEGACY_SHADER_PROCESSOR = new Version150ToVersion120Processor();
        BUILT_IN_DEFAULT = ResourceLocation.create("labymod", "shaders/builtin/default.glsl");
        IMPORT_PATTERN = Pattern.compile("^#laby-import\\s+(\"*[\\w]+:[\\w/.]+)[ \\t]*.*", 8);
    }
}
