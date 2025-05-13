// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import net.labymod.api.client.gfx.pipeline.program.parameters.PolygonOffsetRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.DirectTexturingRenderParameter;
import java.util.function.IntSupplier;
import net.labymod.api.client.gfx.pipeline.program.parameters.TexturingRenderParameter;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.pipeline.program.parameters.ShaderMaterialRenderParameter;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.parameters.ShaderProgramRenderParameter;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.parameters.ui.UIBlendingRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.TranslucentTransparencyBlendingRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.NoTransparencyRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.LightmapParameter;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.program.parameters.CullRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.ColorMaskRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.DepthMaskRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.DepthTestRenderParameter;

public final class RenderParameters
{
    public static DepthTestRenderParameter ALWAYS_DEPTH_TEST;
    public static DepthTestRenderParameter EQUAL_DEPTH_TEST;
    public static DepthTestRenderParameter LEQUAL_DEPTH_TEST;
    public static DepthMaskRenderParameter WRITE_DEPTH;
    public static DepthMaskRenderParameter WRITE_NO_DEPTH;
    public static ColorMaskRenderParameter WRITE_RGBA;
    public static ColorMaskRenderParameter WRITE_RGB;
    public static CullRenderParameter NO_CULL;
    public static CullRenderParameter CULL;
    public static Function<Integer, LightmapParameter> DEFAULT_LIGHTMAP;
    public static NoTransparencyRenderParameter NO_TRANSPARENCY;
    public static TranslucentTransparencyBlendingRenderParameter TRANSLUCENT_TRANSPARENCY_BLENDING;
    public static UIBlendingRenderParameter UI_BLENDING;
    
    public static RenderParameter shaderProgramParameter(final ShaderProgram shaderProgram) {
        return new ShaderProgramRenderParameter(shaderProgram);
    }
    
    public static RenderParameter shaderMaterialParameter(final ShaderMaterial shaderMaterial) {
        return ShaderMaterialRenderParameter.of(shaderMaterial);
    }
    
    public static TexturingRenderParameter createTextureParameter(final ResourceLocation location) {
        return createTextureParameter(location, false, false);
    }
    
    public static TexturingRenderParameter createTextureParameter(final ResourceLocation location, final boolean blur, final boolean mipmap) {
        return createTextureParameter(location, 0, blur, mipmap);
    }
    
    public static TexturingRenderParameter createTextureParameter(final ResourceLocation location, final int textureSlot, final boolean blur, final boolean mipmap) {
        return new TexturingRenderParameter(location, textureSlot, blur, mipmap);
    }
    
    public static DirectTexturingRenderParameter createDirectTextureParameter(final IntSupplier textureId) {
        return new DirectTexturingRenderParameter(0, textureId);
    }
    
    public static DirectTexturingRenderParameter createDirectTextureParameter(final int textureSlot, final IntSupplier textureId) {
        return new DirectTexturingRenderParameter(textureSlot, textureId);
    }
    
    public static PolygonOffsetRenderParameter createPolygonOffset(final float factor, final float units) {
        return new PolygonOffsetRenderParameter(factor, units);
    }
    
    static {
        RenderParameters.ALWAYS_DEPTH_TEST = new DepthTestRenderParameter(519);
        RenderParameters.EQUAL_DEPTH_TEST = new DepthTestRenderParameter(514);
        RenderParameters.LEQUAL_DEPTH_TEST = new DepthTestRenderParameter(515);
        RenderParameters.WRITE_DEPTH = new DepthMaskRenderParameter(true);
        RenderParameters.WRITE_NO_DEPTH = new DepthMaskRenderParameter(false);
        RenderParameters.WRITE_RGBA = new ColorMaskRenderParameter(true, true, true, true);
        RenderParameters.WRITE_RGB = new ColorMaskRenderParameter(true, true, true, false);
        RenderParameters.NO_CULL = new CullRenderParameter(false);
        RenderParameters.CULL = new CullRenderParameter(true);
        RenderParameters.DEFAULT_LIGHTMAP = LightmapParameter::new;
        RenderParameters.NO_TRANSPARENCY = new NoTransparencyRenderParameter();
        RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING = new TranslucentTransparencyBlendingRenderParameter();
        RenderParameters.UI_BLENDING = new UIBlendingRenderParameter();
    }
}
