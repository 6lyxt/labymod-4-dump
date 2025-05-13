// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import java.util.ArrayList;
import net.labymod.api.client.render.vertex.shard.RenderShard;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import java.util.Objects;
import net.labymod.api.util.KeyValue;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

@Deprecated
public class RenderPhaseRenderProgram extends RenderProgram
{
    public RenderPhaseRenderProgram(final RenderPhase renderPhase) {
        super(renderPhase.getName(), findBestMode(renderPhase.getMode()), bestVertexFormat(renderPhase.getVertexFormat()), buildParameters(renderPhase.getShards()));
    }
    
    private static DrawingMode findBestMode(final int mode) {
        return switch (mode) {
            case 4 -> DrawingMode.TRIANGLES;
            case 1 -> DrawingMode.LINES;
            default -> DrawingMode.QUADS;
        };
    }
    
    private static VertexFormat bestVertexFormat(final OldVertexFormat oldFormat) {
        String name = null;
        final OldVertexFormatRegistry registry = Laby.references().oldVertexFormatRegistry();
        for (final KeyValue<OldVertexFormat> element : registry.getElements()) {
            if (Objects.equals(element.getValue(), oldFormat)) {
                name = element.getKey();
                break;
            }
        }
        final String s = name;
        switch (s) {
            default: {
                return Laby.references().vertexFormatRegistry().getVertexFormat("labymod:position_texture_color");
            }
        }
    }
    
    private static List<RenderParameter> buildParameters(final List<RenderShard> shards) {
        final List<RenderParameter> parameters = new ArrayList<RenderParameter>();
        for (final RenderShard shard : shards) {
            parameters.add(new RenderShardRenderParameter(shard));
        }
        return parameters;
    }
    
    private static class RenderShardRenderParameter extends RenderParameter
    {
        private final RenderShard renderShard;
        
        public RenderShardRenderParameter(final RenderShard renderShard) {
            this.renderShard = renderShard;
        }
        
        @Override
        public void apply() {
            this.renderShard.setupShared();
        }
        
        @Override
        public void clear() {
            this.renderShard.finishShared();
        }
    }
}
