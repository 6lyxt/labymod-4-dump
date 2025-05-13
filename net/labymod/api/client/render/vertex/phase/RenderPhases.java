// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.phase;

import java.util.Objects;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.vertex.shard.shards.ShaderProgramRenderShard;
import net.labymod.api.client.render.vertex.shard.RenderShard;
import net.labymod.api.client.render.vertex.shard.RenderShards;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import org.jetbrains.annotations.Contract;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.shader.ShaderProgram;
import org.jetbrains.annotations.NotNull;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.labymod.api.util.function.FunctionMemoizeStorage;

public final class RenderPhases
{
    private static final FunctionMemoizeStorage MEMOIZE_STORAGE;
    private static final int MOJANG_DEBUG_LINES = -5;
    private static final Function<Float, RenderPhase> LINES;
    private static Function<TextureData, RenderPhase> TEXTURE_PHASE;
    private static final RenderPhase LINE_PHASE;
    private static final BiFunction<Boolean, Float, RenderPhase> TRIANGLE_PHASE;
    @NotNull
    public static final RenderPhase NO_TEXTURED_RECTANGLE_PHASE;
    public static Function<ShaderProgram, RenderPhase> NO_TEXTURED_RECTANGLE_WITH_SHADER_PHASE;
    private static final Function<ResourceLocation, RenderPhase> TEXTURE_3D_PHASE;
    
    @NotNull
    public static RenderPhase createNoTexturedRectanglePhase(final ShaderProgram shaderProgram) {
        return RenderPhases.NO_TEXTURED_RECTANGLE_WITH_SHADER_PHASE.apply(shaderProgram);
    }
    
    @NotNull
    public static RenderPhase create3DTexturePhase(@NotNull final ResourceLocation location) {
        return RenderPhases.TEXTURE_3D_PHASE.apply(location);
    }
    
    @NotNull
    public static RenderPhase createTexturePhase(final ResourceLocation location, final boolean userInterface) {
        return createTexturePhase(location, false, false, userInterface);
    }
    
    @NotNull
    public static RenderPhase createTexturePhase(final ResourceLocation location, final ShaderInstance program, final boolean userInterface) {
        return createTexturePhase(location, program, false, false, userInterface);
    }
    
    @Contract("_, _, _, _ -> new")
    @NotNull
    public static RenderPhase createTexturePhase(final ResourceLocation location, final boolean blur, final boolean mipmap, final boolean userInterface) {
        return RenderPhases.TEXTURE_PHASE.apply(new TextureData(location, null, blur, mipmap, userInterface));
    }
    
    @NotNull
    public static RenderPhase createTexturePhase(final ResourceLocation location, final ShaderInstance program, final boolean blur, final boolean mipmap, final boolean userInterface) {
        return RenderPhases.TEXTURE_PHASE.apply(new TextureData(location, program, blur, mipmap, userInterface));
    }
    
    @NotNull
    public static RenderPhase triangle(final boolean filled) {
        return triangle(filled, 1.0f);
    }
    
    @NotNull
    public static RenderPhase triangle(final boolean filled, final float lineWidth) {
        return RenderPhases.TRIANGLE_PHASE.apply(filled, lineWidth);
    }
    
    @NotNull
    public static RenderPhase lines() {
        return lines(2.5f);
    }
    
    @NotNull
    public static RenderPhase lines(final float lineWidth) {
        return RenderPhases.LINES.apply(lineWidth);
    }
    
    @NotNull
    public static RenderPhase line() {
        return RenderPhases.LINE_PHASE;
    }
    
    @NotNull
    private static OldVertexFormatRegistry formatRegistry() {
        return Laby.labyAPI().renderPipeline().vertexFormatRegistry();
    }
    
    static {
        MEMOIZE_STORAGE = Laby.references().functionMemoizeStorage();
        LINES = RenderPhases.MEMOIZE_STORAGE.memoize(lineWidth -> RenderPhase.builder().name("lines").vertexFormat(formatRegistry().getPositionColorNormal()).mode(1).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY).addModernShard(RenderShards.LEQUAL_DEPTH_TEST).addShard(RenderShards.COLOR_DEPTH_WRITE).addShard(RenderShards.lineWidth(Math.max(lineWidth, Laby.labyAPI().minecraft().minecraftWindow().getScaledWidth() / 1920.0f * lineWidth))).addShard(RenderShards.VIEW_OFFSET_Z_LAYERING).addShard(RenderShards.NO_CULL).addShard(RenderShards.NO_TEXTURING).addShard(RenderShards.ITEM_ENTITY_TARGET).build());
        RenderPhases.TEXTURE_PHASE = RenderPhases.MEMOIZE_STORAGE.memoize(textureData -> {
            final OldVertexFormat format = formatRegistry().getPositionColorTextureLightmap();
            final ShaderInstance program = textureData.program;
            final RenderPhaseBuilder builder = RenderPhase.builder();
            builder.name("texture_renderer").vertexFormat(format).mode(7).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY_HUD).addShard(RenderShards.createTexturing(textureData.location, textureData.blur, textureData.mipmap));
            if (program != null) {
                builder.addShard(new ShaderProgramRenderShard(program, format));
            }
            if (PlatformEnvironment.isNoShader()) {
                builder.addShard(RenderShards.DEFAULT_ALPHA);
            }
            else if (!textureData.shaders) {
                builder.addModernShard(!textureData.userInterface, new ShaderProgramRenderShard("renderphase_3d_environment_texture", format));
            }
            return builder.addModernShard(RenderShards.LIGHTMAP).build();
        });
        LINE_PHASE = RenderPhase.builder().name("line").vertexFormat(formatRegistry().getPositionColor()).mode(PlatformEnvironment.isAncientOpenGL() ? 1 : -5).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY).addShard(RenderShards.NO_TEXTURING).addShard(RenderShards.SMOOTH_LINE).build();
        TRIANGLE_PHASE = RenderPhases.MEMOIZE_STORAGE.memoize((filled, lineWidth) -> RenderPhase.builder().name("triangle_phase").vertexFormat(formatRegistry().getPositionColor()).mode(filled ? 6 : 3).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY_HUD).addShard(RenderShards.NO_TEXTURING).addShard(RenderShards.lineWidth(lineWidth)).addShard(!filled, RenderShards.SMOOTH_LINE).build());
        NO_TEXTURED_RECTANGLE_PHASE = RenderPhase.builder().name("rectangle_phase").vertexFormat(formatRegistry().getPositionColor()).mode(7).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY_HUD).addShard(RenderShards.NO_TEXTURING).build();
        RenderPhases.NO_TEXTURED_RECTANGLE_WITH_SHADER_PHASE = RenderPhases.MEMOIZE_STORAGE.memoize(shaderProgram -> RenderPhase.builder().name("rectangle_phase").vertexFormat(formatRegistry().getPositionColor()).mode(7).addShard(new ShaderProgramRenderShard(shaderProgram)).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY_HUD).addShard(RenderShards.NO_TEXTURING).build());
        TEXTURE_3D_PHASE = RenderPhases.MEMOIZE_STORAGE.memoize(location -> {
            final OldVertexFormat vertexFormat = formatRegistry().getPositionColorTextureLightmap();
            return RenderPhase.builder().name("3d_texture_phase").vertexFormat(vertexFormat).mode(7).sortOnUpload(true).affectsCrumbling(true).addShard(RenderShards.createTexturing(location, false, false)).addShard(RenderShards.TRANSLUCENT_TRANSPARENCY).addModernShard(new ShaderProgramRenderShard("renderphase_3d_environment_texture", vertexFormat)).addModernShard(RenderShards.LEQUAL_DEPTH_TEST).addShard(RenderShards.COLOR_DEPTH_WRITE).addShard(RenderShards.NO_CULL).addShard(RenderShards.LIGHTMAP).build();
        });
    }
    
    static class TextureData
    {
        private final ResourceLocation location;
        private final ShaderInstance program;
        private final boolean blur;
        private final boolean mipmap;
        private final boolean userInterface;
        private final boolean shaders;
        
        public TextureData(final ResourceLocation location, final ShaderInstance program, final boolean blur, final boolean mipmap, final boolean userInterface) {
            this.location = location;
            this.program = program;
            this.blur = blur;
            this.mipmap = mipmap;
            this.userInterface = userInterface;
            this.shaders = OptiFine.config().hasShaders();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final TextureData that = (TextureData)o;
            return this.blur == that.blur && this.mipmap == that.mipmap && this.userInterface == that.userInterface && this.shaders == that.shaders && Objects.equals(this.location, that.location) && Objects.equals(this.program, that.program);
        }
        
        @Override
        public int hashCode() {
            int result = (this.location != null) ? this.location.hashCode() : 0;
            result = 31 * result + ((this.program != null) ? this.program.hashCode() : 0);
            result = 31 * result + (this.blur ? 1 : 0);
            result = 31 * result + (this.mipmap ? 1 : 0);
            result = 31 * result + (this.userInterface ? 1 : 0);
            result = 31 * result + (this.shaders ? 1 : 0);
            return result;
        }
    }
}
