// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.immediate;

import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;

public final class ImmediateRenderer
{
    @Nullable
    private static RenderedBuffer lastImmediateBuffer;
    
    public static void reset() {
        if (ImmediateRenderer.lastImmediateBuffer == null) {
            return;
        }
        invalidate();
        Laby.gfx().invalidateBuffers();
    }
    
    public static void invalidate() {
        ImmediateRenderer.lastImmediateBuffer = null;
    }
    
    public static void drawWithProgram(final BufferState state) {
        drawWithProgram(state, ImmediateDrawPhase.DEFAULT_DRAW_PHASE);
    }
    
    public static void drawWithProgram(final BufferState state, final ImmediateDrawPhase immediateDrawPhase) {
        if (state == null) {
            return;
        }
        final RenderedBuffer buffer = upload(state);
        if (buffer == null) {
            return;
        }
        immediateDrawPhase.setup();
        buffer.drawWithProgram();
        immediateDrawPhase.end();
    }
    
    @Nullable
    private static RenderedBuffer upload(final BufferState state) {
        try {
            if (state.getVertices() <= 0) {
                return null;
            }
            final RenderedBuffer renderedBuffer = bindImmediateBuffer(state.renderProgram().vertexFormat());
            renderedBuffer.upload(state);
            return renderedBuffer;
        }
        finally {
            state.release();
        }
    }
    
    private static RenderedBuffer bindImmediateBuffer(final VertexFormat format) {
        final RenderedBuffer buffer = format.getImmediateDrawBuffer();
        bindImmediateBuffer(buffer);
        return buffer;
    }
    
    private static void bindImmediateBuffer(final RenderedBuffer buffer) {
        if (buffer == ImmediateRenderer.lastImmediateBuffer) {
            return;
        }
        buffer.bind();
        ImmediateRenderer.lastImmediateBuffer = buffer;
    }
}
