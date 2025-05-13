// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.program.parameters.DepthMaskRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.DepthTestRenderParameter;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.material.PositionTextureColorRoundShaderMaterial;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.gfx.shader.material.ShaderMaterials;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.shader.UniformContext;
import net.labymod.api.client.gfx.pipeline.program.parameters.NoDepthTestRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.RenderTargetBlendRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.ColorMaskRenderParameter;
import java.util.Locale;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.material.KawaseShaderMaterial;
import net.labymod.api.util.StringUtil;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.draw.BlurRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.labymod.api.util.function.FunctionMemoizeStorage;

public final class RenderPrograms
{
    private static final String POSITION_TEXTURE_COLOR = "labymod:position_texture_color";
    private static final FunctionMemoizeStorage MEMOIZE_STORAGE;
    private static final Function<ShaderCompileMode, RenderProgram> POSITION_TEXTURE_COLOR_PROGRAM;
    private static final RenderProgram RENDER_TARGET_PROGRAM;
    private static final BiFunction<ResourceLocation, ResourceLocation, RenderProgram> SPRAY_PROGRAM;
    private static final Function<BlurRenderer.BlurAlgorithm, RenderProgram> BLUR_PROGRAM;
    private static final RenderProgram RECTANGLE_PROGRAM;
    private static final RenderProgram DEFAULT_POST_PROCESSING_PROGRAM;
    private static final Function<ResourceLocation, RenderProgram> FONT_NORMAL;
    private static final Function<ResourceLocation, RenderProgram> FONT_POLYGON_OFFSET;
    private static final Function<ResourceLocation, RenderProgram> FONT_SEE_THROUGH;
    private static final Function<ResourceLocation, RenderProgram> ITEM;
    private static final Function<ResourceLocation, RenderProgram> TEXTURE_RENDER_PROGRAM;
    private static final Function<ResourceLocation, RenderProgram> SCHEMATIC_PROGRAM;
    
    public static RenderProgram getBlurProgram(final BlurRenderer.BlurAlgorithm blurAlgorithm) {
        return RenderPrograms.BLUR_PROGRAM.apply(blurAlgorithm);
    }
    
    public static RenderProgram getPositionTextureColorProgram() {
        return RenderPrograms.POSITION_TEXTURE_COLOR_PROGRAM.apply(ShaderCompileMode.DEFAULT);
    }
    
    public static RenderProgram getPositionTextureColorRoundProgram() {
        return getPositionTextureColor(ShaderCompileMode.ROUND_RECTANGLE);
    }
    
    public static RenderProgram getPositionTextureColor(final ShaderCompileMode geometryShader) {
        return RenderPrograms.POSITION_TEXTURE_COLOR_PROGRAM.apply(geometryShader);
    }
    
    public static RenderProgram getUIRectangleProgram() {
        return RenderPrograms.RECTANGLE_PROGRAM;
    }
    
    public static RenderProgram getRenderTargetProgram() {
        return RenderPrograms.RENDER_TARGET_PROGRAM;
    }
    
    public static RenderProgram getFontProgram(final TextDrawMode drawMode, final ResourceLocation location) {
        return switch (drawMode) {
            default -> throw new MatchException(null, null);
            case NORMAL -> RenderPrograms.FONT_NORMAL.apply(location);
            case SEE_THROUGH -> RenderPrograms.FONT_SEE_THROUGH.apply(location);
            case POLYGON_OFFSET -> RenderPrograms.FONT_POLYGON_OFFSET.apply(location);
        };
    }
    
    public static RenderProgram getSpray(final ResourceLocation diffuseLocation, final ResourceLocation wearLocation) {
        return RenderPrograms.SPRAY_PROGRAM.apply(diffuseLocation, wearLocation);
    }
    
    public static RenderProgram getItemProgram(final ResourceLocation location) {
        return RenderPrograms.ITEM.apply(location);
    }
    
    public static RenderProgram getDefaultTexture(final ResourceLocation location) {
        return RenderPrograms.TEXTURE_RENDER_PROGRAM.apply(location);
    }
    
    public static RenderProgram getSchematic(final ResourceLocation location) {
        return RenderPrograms.SCHEMATIC_PROGRAM.apply(location);
    }
    
    public static RenderProgram getDefaultPostProcessing() {
        return RenderPrograms.DEFAULT_POST_PROCESSING_PROGRAM;
    }
    
    private static RenderProgram createFontProgram(final ResourceLocation location, final TextDrawMode drawMode) {
        final RenderProgram.Builder programBuilder = RenderProgram.builder();
        programBuilder.name("font_" + drawMode.name()).vertexFormat("labymod:msdf_font").drawingMode(DrawingMode.QUADS);
        programBuilder.addParameter(RenderParameters.createTextureParameter(location, true, true)).addParameter(RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING).addParameter(RenderParameters.WRITE_RGBA);
        switch (drawMode) {
            case SEE_THROUGH: {
                programBuilder.addModernParameter(RenderParameters.WRITE_NO_DEPTH);
                break;
            }
            case NORMAL:
            case POLYGON_OFFSET: {
                programBuilder.addModernParameter(RenderParameters.WRITE_DEPTH);
                programBuilder.addModernParameter(RenderParameters.LEQUAL_DEPTH_TEST);
                if (drawMode == TextDrawMode.POLYGON_OFFSET) {
                    programBuilder.addModernParameter(RenderParameters.createPolygonOffset(-1.0f, -10.0f));
                    break;
                }
                break;
            }
        }
        programBuilder.addModernParameter(RenderParameters.CULL);
        programBuilder.addParameter(RenderParameters.DEFAULT_LIGHTMAP.apply(1));
        return programBuilder.build();
    }
    
    static {
        MEMOIZE_STORAGE = Laby.references().functionMemoizeStorage();
        POSITION_TEXTURE_COLOR_PROGRAM = RenderPrograms.MEMOIZE_STORAGE.memoize(shader -> {
            String name = "position_texture_color";
            final RenderProgram.Builder renderProgramBuilder = RenderProgram.builder();
            if (shader == ShaderCompileMode.ROUND_RECTANGLE || shader == ShaderCompileMode.ROUND_RECTANGLE_POST_PROCESSING) {
                final ShaderConstants.Builder shaderConstantsBuilder = ShaderConstants.builder();
                shaderConstantsBuilder.addConstant("ROUND_RECTANGLE");
                if (shader.isPostProcessing()) {
                    shaderConstantsBuilder.addConstant("POST_PROCESSING");
                }
                final ShaderConstants shaderConstants = shaderConstantsBuilder.build();
                final ShaderMaterial material = ShaderMaterial.builder(context -> {
                    final UniformMatrix4 projectionMatrix = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
                    final UniformMatrix4 modelViewMatrix = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
                    final Uniform4F colorModulator = context.addUniform(new Uniform4F("ColorModulator"));
                    context.addUniform(new UniformSampler("DiffuseSampler", 0));
                    context.addUniform(new Uniform1F("BorderThickness"));
                    context.addUniform(new Uniform1F("BorderSoftness"));
                    context.addUniform(new Uniform4F("BorderColor"));
                    context.addUniform(new Uniform4F("Radius"));
                    context.addUniform(new Uniform2F("Size"));
                    context.addUniform(new Uniform4F("Vertices"));
                    context.addUniform(new Uniform1F("LowerEdgeSoftness"));
                    context.addUniform(new Uniform1F("UpperEdgeSoftness"));
                    context.setUniformApplier(pipeline -> {
                        projectionMatrix.set(pipeline.getProjectionMatrix());
                        modelViewMatrix.set(pipeline.getModelViewMatrix());
                        colorModulator.set(pipeline.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().colorModulator());
                    });
                    return;
                }).addVertexShader(ResourceLocation.create("labymod", "shaders/vertex/position_texture_color.vsh"), shaderConstants).addFragmentShader(ResourceLocation.create("labymod", "shaders/vertex/position_texture_color.fsh"), shaderConstants).withDebugName(StringUtil.capitalizeWords(shader.name().replace("_", " "))).build((Function<ShaderProgram, ShaderMaterial>)PositionTextureColorRoundShaderMaterial::new);
                name = name + "_" + shader.name().toUpperCase(Locale.ROOT);
                renderProgramBuilder.addParameter(RenderParameters.shaderMaterialParameter(material));
            }
            return renderProgramBuilder.name(name).drawingMode(DrawingMode.QUADS).vertexFormat("labymod:position_texture_color").build();
        });
        RENDER_TARGET_PROGRAM = RenderProgram.builder().name("render_target_program").drawingMode(DrawingMode.QUADS).vertexFormat("labymod:position_texture_color").addParameter(new ColorMaskRenderParameter(true, true, true, true)).addParameter(new RenderTargetBlendRenderParameter()).addParameter(new NoDepthTestRenderParameter()).build();
        SPRAY_PROGRAM = RenderPrograms.MEMOIZE_STORAGE.memoize((diffuse, wear) -> {
            final RenderProgram.Builder builder = RenderProgram.builder();
            builder.name("spray").drawingMode(DrawingMode.QUADS).vertexFormat("labymod:position_texture_color").addParameter(RenderParameters.shaderMaterialParameter(ShaderMaterials.SPRAY_SHADER_MATERIAL)).addParameter(RenderParameters.LEQUAL_DEPTH_TEST).addParameter(RenderParameters.WRITE_DEPTH).addParameter(RenderParameters.WRITE_RGBA).addParameter(RenderParameters.NO_CULL).addParameter(RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING);
            builder.addParameter(RenderParameters.createPolygonOffset(-1.0f, -10.0f));
            builder.addParameter(RenderParameters.createTextureParameter(diffuse, 0, false, false));
            builder.addParameter(RenderParameters.DEFAULT_LIGHTMAP.apply(1));
            builder.addParameter(RenderParameters.createTextureParameter(wear, 4, false, false));
            return builder.build();
        });
        BLUR_PROGRAM = RenderPrograms.MEMOIZE_STORAGE.memoize(algorithm -> {
            final ShaderMaterial material2 = ShaderMaterial.builder(context -> {
                final UniformMatrix4 projectionMatrix2 = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
                final UniformMatrix4 modelViewMatrix2 = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
                context.addUniform(new Uniform2F("Size"));
                context.addUniform(new Uniform2F("Offset"));
                context.addUniform(new UniformSampler("DiffuseSampler", 0));
                context.setUniformApplier(applier -> {
                    projectionMatrix.set(applier.getProjectionMatrix());
                    modelViewMatrix.set(applier.getModelViewMatrix());
                });
                return;
            }).addVertexShader(algorithm.vertexShaderLocation()).addFragmentShader(algorithm.fragmentShaderLocation()).withDebugName(StringUtil.capitalizeWords(algorithm.name().replace("_", " "))).build((Function<ShaderProgram, ShaderMaterial>)KawaseShaderMaterial::new);
            return RenderProgram.builder().name(algorithm.name().toLowerCase(Locale.ROOT) + "_program").drawingMode(DrawingMode.QUADS).vertexFormat("labymod:position_texture_color").addParameter(RenderParameters.shaderMaterialParameter(material2)).addParameter(new ColorMaskRenderParameter(true, true, true, true)).addParameter(new RenderTargetBlendRenderParameter()).addParameter(new NoDepthTestRenderParameter()).build();
        });
        RECTANGLE_PROGRAM = RenderProgram.builder().name("ui_rectangle").drawingMode(DrawingMode.QUADS).vertexFormat("labymod:position_texture_color").addParameter(RenderParameters.UI_BLENDING).build();
        DEFAULT_POST_PROCESSING_PROGRAM = new PostProcessRenderProgram("default_post_processing", DrawingMode.QUADS, Laby.references().vertexFormatRegistry().getVertexFormat("labymod:position_texture_color"), new ArrayList<RenderParameter>(List.of(new DepthTestRenderParameter(519), new ColorMaskRenderParameter(true, true, true, true), new DepthMaskRenderParameter(false))));
        FONT_NORMAL = RenderPrograms.MEMOIZE_STORAGE.memoize(location -> createFontProgram(location, TextDrawMode.NORMAL));
        FONT_POLYGON_OFFSET = RenderPrograms.MEMOIZE_STORAGE.memoize(location -> createFontProgram(location, TextDrawMode.POLYGON_OFFSET));
        FONT_SEE_THROUGH = RenderPrograms.MEMOIZE_STORAGE.memoize(location -> createFontProgram(location, TextDrawMode.SEE_THROUGH));
        ITEM = RenderPrograms.MEMOIZE_STORAGE.memoize(location -> {
            final RenderProgram.Builder renderProgramBuilder2 = RenderProgram.builder();
            renderProgramBuilder2.drawingMode(DrawingMode.QUADS).name("item_program").vertexFormat("labymod:simple_model");
            renderProgramBuilder2.addParameter(RenderParameters.LEQUAL_DEPTH_TEST).addParameter(RenderParameters.WRITE_DEPTH).addParameter(RenderParameters.WRITE_RGBA).addParameter(RenderParameters.NO_CULL).addParameter(RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING);
            if (location != null) {
                renderProgramBuilder2.addParameter(RenderParameters.createTextureParameter(location));
            }
            renderProgramBuilder2.addParameter(RenderParameters.DEFAULT_LIGHTMAP.apply(1));
            return new DynamicRenderProgram(renderProgramBuilder2.build());
        });
        TEXTURE_RENDER_PROGRAM = RenderPrograms.MEMOIZE_STORAGE.memoize(location -> {
            final RenderProgram.Builder builder2 = RenderProgram.builder();
            builder2.name("default_texture").drawingMode(DrawingMode.QUADS).vertexFormat("labymod:position_texture_color").addParameter(RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING).addParameter(RenderParameters.createTextureParameter(location));
            return builder2.build();
        });
        SCHEMATIC_PROGRAM = RenderPrograms.MEMOIZE_STORAGE.memoize(location -> {
            final RenderProgram.Builder builder3 = RenderProgram.builder();
            builder3.name("schematic").drawingMode(DrawingMode.QUADS).vertexFormat("labymod:schematic").addParameter(RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING).addParameter(RenderParameters.createTextureParameter(location));
            return builder3.build();
        });
    }
}
