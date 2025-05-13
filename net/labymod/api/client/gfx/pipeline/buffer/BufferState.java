// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer;

import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import java.nio.ByteBuffer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.index.IndexBuffer;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.buffer.writer.BufferResource;
import net.labymod.api.util.logging.Logging;

public class BufferState
{
    private static final Logging LOGGER;
    private final BufferResource resource;
    private final RenderProgram renderProgram;
    private final int vertices;
    private final int indices;
    @Nullable
    private IndexBuffer indexBuffer;
    private boolean released;
    
    public BufferState(final BufferResource resource, final RenderProgram renderProgram, final int vertices) {
        this.resource = resource;
        this.renderProgram = renderProgram;
        this.vertices = vertices;
        this.indices = this.renderProgram.mode().getIndexCount(this.vertices);
    }
    
    public RenderProgram renderProgram() {
        return this.renderProgram;
    }
    
    @Nullable
    public IndexBuffer getIndexBuffer() {
        return this.indexBuffer;
    }
    
    public ByteBuffer getBuffer() {
        return this.resource.byteBuffer();
    }
    
    public int getVertices() {
        return this.vertices;
    }
    
    public int getIndices() {
        return this.indices;
    }
    
    public RenderedBuffer uploadStaticDraw() {
        return this.upload(BufferUsage.STATIC_DRAW);
    }
    
    public RenderedBuffer uploadDynamicDraw() {
        return this.upload(BufferUsage.DYNAMIC_DRAW);
    }
    
    public RenderedBuffer upload(final BufferUsage usage) {
        try {
            final RenderedBuffer buffer = new RenderedBuffer(usage);
            buffer.upload(this);
            return buffer;
        }
        finally {
            this.release();
        }
    }
    
    public void release() {
        if (this.released) {
            throw new IllegalStateException("Buffer has already been released.");
        }
        try {
            this.resource.close();
        }
        catch (final Exception exception) {
            BufferState.LOGGER.error("An error occurred while releasing the buffer.", exception);
        }
        this.released = true;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
