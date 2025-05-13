// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import net.labymod.api.Laby;
import net.labymod.api.util.gson.PostProcessorTargetTypeAdapter;
import java.lang.reflect.Type;
import net.labymod.api.util.gson.ResourceLocationTypeAdapter;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.gfx.shader.UniformContext;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorUniform;
import java.util.ArrayList;
import java.util.Collections;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.post.data.EffectData;
import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.post.data.PostEffectPassData;
import net.labymod.api.client.gfx.shader.uniform.Uniform;
import java.util.List;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorConfig;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.labymod.api.client.gfx.pipeline.post.exception.PostProcessorException;
import java.util.function.Consumer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gfx.pipeline.post.util.UniformParser;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import com.google.gson.Gson;

public final class PostProcessorLoader
{
    private static final Gson GSON;
    private static final PostProcessorLoader INSTANCE;
    private static final ResourcesReloadWatcher RESOURCES_RELOAD_WATCHER;
    private static final UniformParser UNIFORM_PARSER;
    
    private PostProcessorLoader() {
    }
    
    public static PostProcessor load(final RenderTarget destinationTarget, final ResourceLocation configLocation) {
        return PostProcessorLoader.INSTANCE.loadInternal(destinationTarget, configLocation);
    }
    
    public static void loadDynamic(final RenderTarget destinationTarget, final ResourceLocation configLocation, final Consumer<PostProcessor> processorConsumer) {
        PostProcessorLoader.RESOURCES_RELOAD_WATCHER.addOrExecuteInitializeListener(() -> {
            final PostProcessor processor = load(destinationTarget, configLocation);
            processorConsumer.accept(processor);
        });
    }
    
    public PostProcessor loadInternal(final RenderTarget destinationTarget, final ResourceLocation configLocation) {
        if (!configLocation.exists()) {
            throw new PostProcessorException("The given \"" + String.valueOf(configLocation) + "\" config does not exist.");
        }
        final PostProcessor processor = new PostProcessor(destinationTarget, configLocation);
        try (final InputStream stream = configLocation.openStream();
             final InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            final PostProcessorConfig config = (PostProcessorConfig)PostProcessorLoader.GSON.fromJson((Reader)reader, (Class)PostProcessorConfig.class);
            final List<Uniform> uniforms = this.createUniforms(config);
            this.registerRenderTargets(processor, config);
            final PostPassEffectHolder effectHolder = this.createEffectHolder(processor, config, uniforms);
            this.registerPasses(processor, config, effectHolder);
        }
        catch (final IOException exception) {
            throw new PostProcessorException("Failed to read post processor config", exception);
        }
        return processor;
    }
    
    private void registerPasses(final PostProcessor processor, final PostProcessorConfig config, final PostPassEffectHolder effectHolder) {
        for (final PostEffectPassData effect : config.getPasses()) {
            this.registerPass(processor, effect, effectHolder);
        }
    }
    
    private void registerPass(final PostProcessor processor, final PostEffectPassData effect, final PostPassEffectHolder effectHolder) {
        final RenderTarget sourceTarget = this.findRenderTarget(processor, effect.getSourceTarget(), name -> "Source target \"" + name + "\" does not exist");
        final RenderTarget destinationTarget = this.findRenderTarget(processor, effect.getDestinationTarget(), name -> "Destination target \"" + name + "\" does not exist");
        final PostPass pass = processor.addPass(effect.getName(), new PostPassRenderTarget(sourceTarget, effect.isClearSourceTarget()), new PostPassRenderTarget(destinationTarget, effect.isClearDestinationTarget()), effectHolder);
        this.registerPostPassEffects(pass, effect);
    }
    
    private void registerPostPassEffects(final PostPass pass, final PostEffectPassData effectData) {
        for (final EffectData effect : effectData.getEffects()) {
            this.registerPostPassEffect(pass, effect);
        }
    }
    
    private void registerPostPassEffect(final PostPass pass, final EffectData effect) {
        String effectId = effect.getId();
        switch (effect.getType()) {
            case RENDER_TARGET: {
                final PostProcessor processor = pass.processor();
                boolean depthBuffer;
                if (effectId.endsWith(":depth")) {
                    depthBuffer = true;
                    effectId = effectId.substring(0, effectId.lastIndexOf(58));
                }
                else {
                    depthBuffer = false;
                }
                final RenderTarget effectTarget = this.findRenderTarget(processor, effectId, name -> "Effect target \"" + name + " does not exist");
                pass.addEffect(effect.getName(), () -> depthBuffer ? effectTarget.findDepthAttachment().getId() : effectTarget.findColorAttachment().getId());
                break;
            }
            case TEXTURE: {
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(effect.getType()));
            }
        }
    }
    
    private RenderTarget findRenderTarget(final PostProcessor processor, final String renderTargetName, final Function<String, String> exceptionMessageFactory) {
        final RenderTarget renderTarget = processor.getRenderTarget(renderTargetName);
        if (renderTarget == null) {
            throw new PostProcessorException(exceptionMessageFactory.apply(renderTargetName));
        }
        return renderTarget;
    }
    
    private void registerRenderTargets(final PostProcessor processor, final PostProcessorConfig config) {
        final PostProcessorRenderTargetRegistry registry = processor.getRenderTargetRegistry();
        for (final PostProcessorTarget target : config.getTargets()) {
            registry.registerTarget(target);
        }
    }
    
    private List<Uniform> createUniforms(final PostProcessorConfig config) {
        final List<PostProcessorUniform> configUniforms = config.getUniforms();
        if (configUniforms.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Uniform> uniforms = new ArrayList<Uniform>(configUniforms.size());
        PostProcessorLoader.UNIFORM_PARSER.begin();
        for (final PostProcessorUniform configUniform : configUniforms) {
            uniforms.add(PostProcessorLoader.UNIFORM_PARSER.parse(configUniform.getType(), configUniform.getName()));
        }
        return uniforms;
    }
    
    private PostPassEffectHolder createEffectHolder(final PostProcessor processor, final PostProcessorConfig config, final List<Uniform> uniforms) {
        return PostPassEffectHolder.builder(processor.toString()).withFragmentShader(config.getFragment()).withVertexShader(config.getVertex()).withKeywords(config.getKeywords()).withUniformContext(context -> {
            Objects.requireNonNull(context);
            uniforms.forEach(context::addUniform);
        }).build();
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)ResourceLocation.class, (Object)new ResourceLocationTypeAdapter()).registerTypeAdapter((Type)PostProcessorTarget.class, (Object)new PostProcessorTargetTypeAdapter()).create();
        INSTANCE = new PostProcessorLoader();
        RESOURCES_RELOAD_WATCHER = Laby.references().resourcesReloadWatcher();
        UNIFORM_PARSER = new UniformParser();
    }
}
