// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.vertex;

import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.core.event.client.render.SetupVertexFormatShaderConstantsEvent;
import java.util.function.BiConsumer;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.gfx.shader.UniformContext;
import java.util.function.Consumer;
import net.labymod.api.client.gfx.pipeline.Fog;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute;
import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute1F;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.api.client.gfx.shader.uniform.UniformDynamicSampler;
import net.labymod.api.client.gfx.vertex.attribute.DefaultVertexAttributes;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.Laby;
import java.util.Map;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.client.gfx.vertex.VertexFormatStorage;

@AutoService(VertexFormatStorage.class)
public class DefaultVertexFormatStorage implements VertexFormatStorage
{
    private static final String POSITION_TEXTURE_COLOR_NAME = "position_texture_color";
    private static final String POSITION_TEXTURE_COLOR_NORMAL_NAME = "position_texture_color_normal";
    private static final String SIMPLE_MODEL_NAME = "simple_model";
    private static final String MSDF_FONT_NAME = "msdf_font";
    private final ShaderProgram.Factory shaderFactory;
    private final VertexFormat.Builder formatBuilder;
    private Map<String, VertexFormat> vertexFormatMap;
    
    public DefaultVertexFormatStorage() {
        this.shaderFactory = Laby.references().shaderProgramFactory();
        this.formatBuilder = Laby.references().vertexFormatBuilder();
    }
    
    @Override
    public void store(final Map<String, VertexFormat> map) {
        this.vertexFormatMap = map;
        this.store("position_texture_color", context -> {
            final UniformMatrix4 projectionMatrix = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
            final UniformMatrix4 modelViewMatrix = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
            final Uniform4F colorModulator = context.addUniform(new Uniform4F("ColorModulator"));
            context.addUniform(new UniformSampler("DiffuseSampler", 0));
            context.setUniformApplier(renderPipeline -> {
                projectionMatrix.set(renderPipeline.getProjectionMatrix());
                modelViewMatrix.set(renderPipeline.getModelViewMatrix());
                colorModulator.set(renderPipeline.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().colorModulator());
            });
            return;
        }, this::defaultAttributes);
        this.store("position_texture_color_normal", context -> {
            final UniformMatrix4 projectionMatrix2 = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
            final UniformMatrix4 modelViewMatrix2 = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
            final Uniform4F colorModulator2 = context.addUniform(new Uniform4F("ColorModulator"));
            context.addUniform(new UniformSampler("DiffuseSampler", 0));
            context.setUniformApplier(renderPipeline -> {
                projectionMatrix.set(renderPipeline.getProjectionMatrix());
                modelViewMatrix.set(renderPipeline.getModelViewMatrix());
                colorModulator.set(renderPipeline.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().colorModulator());
            });
            return;
        }, builder -> {
            this.defaultAttributes(builder);
            builder.addAttribute("Normal", DefaultVertexAttributes.NORMAL);
            builder.addAttribute("Padding", DefaultVertexAttributes.PADDING);
            return;
        });
        this.store("simple_model", context -> {
            final UniformMatrix4 projectionMatrix3 = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
            final UniformMatrix4 modelViewMatrix3 = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
            final Uniform4F colorModulator3 = context.addUniform(new Uniform4F("ColorModulator"));
            context.addUniform(new UniformSampler("DiffuseSampler", 0));
            context.addUniform(UniformDynamicSampler.LIGHTMAP_SAMPLER.apply("LightSampler", 1));
            context.addUniform(new UniformBone("BoneMatrices"));
            context.addUniform(new Uniform2I("LightCoords"));
            context.addUniform(new Uniform3F("LightDirection0"));
            context.addUniform(new Uniform3F("LightDirection1"));
            final Uniform1F gameTime = context.addUniform(new Uniform1F("GameTime"));
            final Uniform1I environmentContext = context.addUniform(new Uniform1I("EnvironmentContext"));
            environmentContext.set(0);
            final Uniform1F fogStart = context.addUniform(new Uniform1F("FogStart"));
            final Uniform1F fogEnd = context.addUniform(new Uniform1F("FogEnd"));
            final Uniform4F fogColor = context.addUniform(new Uniform4F("FogColor"));
            context.setUniformApplier(renderPipeline -> {
                projectionMatrix.set(renderPipeline.getProjectionMatrix());
                modelViewMatrix.set(renderPipeline.getModelViewMatrix());
                final Blaze3DShaderUniformPipeline uniformPipeline = renderPipeline.gfx().blaze3DGlStatePipeline().shaderUniformPipeline();
                colorModulator.set(uniformPipeline.colorModulator());
                gameTime.set((float)(TimeUtil.getMillis() % 10000000L));
                final Fog fog = uniformPipeline.fog();
                fogStart.set(fog.getSuppliedStart());
                fogEnd.set(fog.getSuppliedEnd());
                fogColor.set(fog.color());
                environmentContext.set(renderPipeline.renderEnvironmentContext().isScreenContext());
            });
            return;
        }, builder -> {
            this.defaultAttributes(builder);
            builder.addAttribute("Normal", DefaultVertexAttributes.NORMAL);
            builder.addAttribute("Padding", DefaultVertexAttributes.PADDING);
            builder.addAttribute("BoneID", new VertexAttribute1F(false));
            builder.addAttribute("Glow", new VertexAttribute1F(false));
            builder.addAttribute("RainbowCycle", new VertexAttribute1F(false));
            return;
        });
        this.store("msdf_font", context -> {
            final UniformMatrix4 projectionMatrix4 = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
            final UniformMatrix4 modelViewMatrix4 = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
            final Uniform4F colorModulator4 = context.addUniform(new Uniform4F("ColorModulator"));
            context.addUniform(new UniformSampler("DiffuseSampler", 0));
            context.addUniform(UniformDynamicSampler.LIGHTMAP_SAMPLER.apply("LightSampler", 1));
            context.addUniform(new Uniform1F("EdgeStrength"));
            final Uniform1F fogStart2 = context.addUniform(new Uniform1F("FogStart"));
            final Uniform1F fogEnd2 = context.addUniform(new Uniform1F("FogEnd"));
            final Uniform4F fogColor2 = context.addUniform(new Uniform4F("FogColor"));
            final Uniform1I environmentContext2 = context.addUniform(new Uniform1I("EnvironmentContext"));
            context.setUniformApplier(renderPipeline -> {
                projectionMatrix.set(renderPipeline.getProjectionMatrix());
                modelViewMatrix.set(renderPipeline.getModelViewMatrix());
                final Blaze3DShaderUniformPipeline uniformPipeline2 = renderPipeline.gfx().blaze3DGlStatePipeline().shaderUniformPipeline();
                colorModulator.set(uniformPipeline2.colorModulator());
                final Fog fog2 = uniformPipeline2.fog();
                fogStart.set(fog2.getSuppliedStart());
                fogEnd.set(fog2.getSuppliedEnd());
                fogColor.set(fog2.color());
                environmentContext.set(renderPipeline.renderEnvironmentContext().isScreenContext());
            });
        }, builder -> {
            this.defaultAttributes(builder);
            builder.addAttribute("UV2", DefaultVertexAttributes.UV2);
            builder.addAttribute("BoldFontWeight", new VertexAttribute1F(false));
        });
    }
    
    private void store(final String name, final Consumer<UniformContext> uniformConsumer, final Consumer<VertexFormat.Builder> vertexFormatBuilder) {
        this.vertexFormatMap.put(name, this.createFormat(name, uniformConsumer, this::addShaderSource, vertexFormatBuilder));
    }
    
    private void defaultAttributes(final VertexFormat.Builder formatBuilder) {
        formatBuilder.addAttribute("Position", DefaultVertexAttributes.POSITION);
        formatBuilder.addAttribute("UV", DefaultVertexAttributes.UV0);
        formatBuilder.addAttribute("Color", DefaultVertexAttributes.COLOR);
    }
    
    private void addShaderSource(final String name, final ShaderProgram shaderProgram) {
        shaderProgram.setDebugName(TextFormat.SNAKE_CASE.toCamelCase(name, false));
        this.addShader(shaderProgram, name, Shader.Type.VERTEX);
        this.addShader(shaderProgram, name, Shader.Type.FRAGMENT);
    }
    
    private VertexFormat createFormat(final String name, final Consumer<UniformContext> uniformConsumer, final BiConsumer<String, ShaderProgram> shaderConsumer, final Consumer<VertexFormat.Builder> formatBuilderConsumer) {
        final ShaderProgram shaderProgram = this.shaderFactory.create(uniformConsumer);
        shaderConsumer.accept(name, shaderProgram);
        this.formatBuilder.addShaderProgram(shaderProgram);
        formatBuilderConsumer.accept(this.formatBuilder);
        return this.formatBuilder.build();
    }
    
    private void addShader(final ShaderProgram shaderProgram, final String name, final Shader.Type type) {
        final SetupVertexFormatShaderConstantsEvent event = Laby.fireEvent(new SetupVertexFormatShaderConstantsEvent(name, type));
        this.addShader(shaderProgram, name, type, event.shaderConstants());
    }
    
    private void addShader(final ShaderProgram shaderProgram, final String name, final Shader.Type type, final ShaderConstants shaderConstants) {
        shaderProgram.addShader(ResourceLocation.create("labymod", "shaders/vertex/" + name + "." + type.toString()), type, shaderConstants);
    }
}
