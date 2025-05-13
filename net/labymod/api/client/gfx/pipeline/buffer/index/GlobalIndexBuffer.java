// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.index;

import java.util.function.IntConsumer;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.buffer.MapBufferAccess;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.util.logging.Logging;

public class GlobalIndexBuffer
{
    public static final GlobalIndexBuffer SHARED_INDICES;
    public static final GlobalIndexBuffer SHARED_QUAD_INDICES;
    private static final Logging LOGGER;
    private final int vertexStride;
    private final int indexStride;
    private final IndexGenerator generator;
    private IndexType indexType;
    private int indexCount;
    private BufferObject elementBuffer;
    
    private GlobalIndexBuffer(final int vertexStride, final int indexStride, final IndexGenerator generator) {
        this.indexType = IndexType.SHORT;
        this.vertexStride = vertexStride;
        this.indexStride = indexStride;
        this.generator = generator;
    }
    
    public static GlobalIndexBuffer select(final DrawingMode mode) {
        if (mode == DrawingMode.QUADS) {
            return GlobalIndexBuffer.SHARED_QUAD_INDICES;
        }
        return GlobalIndexBuffer.SHARED_INDICES;
    }
    
    public void bind(final int indices) {
        if (this.elementBuffer == null) {
            this.elementBuffer = Laby.gfx().createBuffer(BufferTarget.ELEMENT_ARRAY, BufferUsage.DYNAMIC_DRAW);
        }
        this.elementBuffer.bind();
        this.ensureStorage(indices);
    }
    
    public void bind() {
        this.elementBuffer.bind();
    }
    
    public void unbind() {
        this.elementBuffer.unbind();
    }
    
    private void ensureStorage(final int indices) {
        if (this.hasStorage(indices)) {
            return;
        }
        GlobalIndexBuffer.LOGGER.debug("Global Index buffer has not enough storage. Old limit {}, new limit {}.", this.indexCount, indices);
        final int numElements = indices / this.indexStride;
        final int numIndicesNeeded = numElements * this.vertexStride;
        final IndexType selected = IndexType.select(numIndicesNeeded);
        this.elementBuffer.upload(indices * (long)selected.getSize());
        final ByteBuffer buffer = this.elementBuffer.map(MapBufferAccess.WRITE_ONLY);
        if (buffer == null) {
            throw new IllegalStateException("Failed to map global index buffer");
        }
        this.indexType = selected;
        final IntConsumer consumer = this.bufferConsumer(buffer);
        for (int offset = 0; offset < indices; offset += this.indexStride) {
            this.generator.accept(consumer, offset * this.vertexStride / this.indexStride);
        }
        this.elementBuffer.unmap();
        this.indexCount = indices;
    }
    
    public int getIndexCount() {
        return this.indexCount;
    }
    
    public IndexType getIndexType() {
        return this.indexType;
    }
    
    boolean isInvalid() {
        return this.elementBuffer == null;
    }
    
    private IntConsumer bufferConsumer(final ByteBuffer buffer) {
        return value -> {
            switch (this.indexType) {
                case INT: {
                    buffer.putInt(value);
                    break;
                }
                case SHORT: {
                    buffer.putShort((short)value);
                    break;
                }
            }
        };
    }
    
    private boolean hasStorage(final int indices) {
        return indices <= this.indexCount;
    }
    
    static {
        SHARED_INDICES = new GlobalIndexBuffer(1, 1, IntConsumer::accept);
        SHARED_QUAD_INDICES = new GlobalIndexBuffer(4, 6, (consumer, offset) -> {
            consumer.accept(offset + 0);
            consumer.accept(offset + 1);
            consumer.accept(offset + 2);
            consumer.accept(offset + 2);
            consumer.accept(offset + 3);
            consumer.accept(offset + 0);
            return;
        });
        LOGGER = Logging.getLogger();
    }
}
