// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.render;

import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.pipeline.RenderPipeline;

public final class LabyRenderPipelines
{
    private static final RenderPipeline.Snippet MATRICES_SNIPPET;
    private static final RenderPipeline.Snippet MATRICES_COLOR_SNIPPET;
    public static final RenderPipeline POSITION_COLOR;
    public static final RenderPipeline POSITION_TEX_COLOR;
    public static final RenderPipeline POSITION_COLOR_TEX_LIGHTMAP;
    public static final RenderPipeline BLOCK_OVERLAY;
    
    private static alr buildLocation(final String path) {
        return alr.b("labymod", "pipeline/" + path);
    }
    
    static {
        MATRICES_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[0]).withUniform("ModelViewMat", fku.g).withUniform("ProjMat", fku.g).buildSnippet();
        MATRICES_COLOR_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[] { LabyRenderPipelines.MATRICES_SNIPPET }).withUniform("ColorModulator", fku.f).buildSnippet();
        POSITION_COLOR = RenderPipeline.builder(new RenderPipeline.Snippet[] { LabyRenderPipelines.MATRICES_COLOR_SNIPPET }).withVertexFormat(flb.f, VertexFormat.b.h).withLocation(buildLocation("position_color")).withVertexShader("core/position_color").withFragmentShader("core/position_color").withBlend(BlendFunction.TRANSLUCENT).build();
        POSITION_TEX_COLOR = RenderPipeline.builder(new RenderPipeline.Snippet[] { LabyRenderPipelines.MATRICES_COLOR_SNIPPET }).withVertexFormat(flb.j, VertexFormat.b.h).withLocation(buildLocation("position_tex_color")).withVertexShader("core/position_tex_color").withFragmentShader("core/position_tex_color").withSampler("Sampler0").withBlend(BlendFunction.TRANSLUCENT).build();
        POSITION_COLOR_TEX_LIGHTMAP = RenderPipeline.builder(new RenderPipeline.Snippet[] { LabyRenderPipelines.MATRICES_COLOR_SNIPPET }).withVertexFormat(flb.k, VertexFormat.b.h).withLocation(buildLocation("position_color_tex_lightmap")).withVertexShader("core/position_color_tex_lightmap").withFragmentShader("core/position_color_tex_lightmap").withSampler("Sampler0").withSampler("Sampler2").withBlend(BlendFunction.TRANSLUCENT).build();
        BLOCK_OVERLAY = RenderPipeline.builder(new RenderPipeline.Snippet[] { LabyRenderPipelines.MATRICES_COLOR_SNIPPET }).withLocation(buildLocation("block_overlay")).withVertexShader("core/position_color").withFragmentShader("core/position_color").withDepthWrite(false).withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST).withBlend(BlendFunction.TRANSLUCENT).withVertexFormat(flb.f, VertexFormat.b.h).build();
    }
}
