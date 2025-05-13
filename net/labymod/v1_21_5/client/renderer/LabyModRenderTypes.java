// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.renderer;

import net.labymod.v1_21_5.client.render.LabyRenderPipelines;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.pipeline.BlendFunction;
import net.labymod.v1_21_5.client.render.vertex.VersionedVertexFormat;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.labymod.api.Laby;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;

public class LabyModRenderTypes
{
    private static final Function<RenderPhase, gry> RENDER_PHASES;
    public static final gry BLOCK_OVERLAY;
    
    public static gry fromPhase(final RenderPhase phase) {
        return LabyModRenderTypes.RENDER_PHASES.apply(phase);
    }
    
    private static gry.b appendState(final gry.b state, final RenderPhase phase) {
        ((CompositeStateAppender)state).append(new LabyModRenderStateShard(phase.getName(), () -> {
            RenderUtil.enableCustomRenderType();
            phase.apply();
            return;
        }, () -> {
            phase.clear();
            RenderUtil.disableCustomRenderType();
            Laby.gfx().blaze3DGlStatePipeline().invalidateBuffers();
            return;
        }));
        return state;
    }
    
    private static RenderPipeline buildPipeline(final RenderPhase phase) {
        final RenderPipeline defaultPipeline = ((VersionedVertexFormat)phase.getVertexFormat()).getRenderPipeline();
        final RenderPipeline.Builder builder = RenderPipeline.builder(new RenderPipeline.Snippet[0]);
        final String phaseName = phase.getName();
        defaultPipeline.getSamplers().forEach(samplerName -> {
            if (phaseName.equals("texture_renderer") && samplerName.equals("Sampler2")) {
                return;
            }
            else {
                builder.withSampler(samplerName);
                return;
            }
        });
        defaultPipeline.getUniforms().forEach(uniformDescription -> builder.withUniform(uniformDescription.name(), uniformDescription.type()));
        return builder.withLocation(fromPath("pipeline/" + phaseName)).withVertexShader(defaultPipeline.getVertexShader()).withFragmentShader(defaultPipeline.getFragmentShader()).withBlend(BlendFunction.TRANSLUCENT).withVertexFormat((VertexFormat)phase.getVertexFormat().getMojangVertexFormat(), ModeUtil.getMode(phase.getMode())).build();
    }
    
    private static alr fromPath(final String path) {
        return alr.a("labymod", path);
    }
    
    static {
        RENDER_PHASES = Laby.references().functionMemoizeStorage().memoize(phase -> {
            final gry.b.a builder = gry.b.a();
            return gry.a(phase.getName(), phase.getBufferSize(), phase.isAffectsCrumbling(), phase.isSortOnUpload(), buildPipeline(phase), appendState(builder.a(false), phase));
        });
        BLOCK_OVERLAY = (gry)gry.a("block_overlay", 65536, LabyRenderPipelines.BLOCK_OVERLAY, gry.b.a().a(false));
    }
}
