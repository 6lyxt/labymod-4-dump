// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.buffer;

import net.labymod.api.client.gfx.vertex.attribute.DefaultVertexAttributes;
import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.writer.BufferResource;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.buffer.writer.NativeBufferWriter;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import java.util.function.Supplier;
import net.labymod.api.client.gfx.pipeline.buffer.writer.BufferWriter;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;

public class DefaultBufferBuilder implements BufferBuilder
{
    private static final Logging LOGGER;
    private final BufferWriter bufferWriter;
    private Supplier<String> nameSupplier;
    private RenderProgram currentProgram;
    private VertexFormat currentFormat;
    private AttributeState attributeState;
    private int vertices;
    private boolean building;
    
    public DefaultBufferBuilder(final int capacity) {
        this.bufferWriter = new NativeBufferWriter(capacity * 6);
    }
    
    @Override
    public void begin(final RenderProgram renderProgram, final Supplier<String> name) {
        if (this.building) {
            throw new IllegalStateException("Already building (BufferBuilder#end was not called for buffer builder '" + ((this.nameSupplier == null) ? "Unknown Builder" : ((String)this.nameSupplier.get() + "'.")));
        }
        this.nameSupplier = name;
        this.building = true;
        this.currentProgram = renderProgram;
        this.currentFormat = renderProgram.vertexFormat();
        this.attributeState = new AttributeState(this.currentFormat);
        this.bufferWriter.ensureCapacity(this.currentFormat.getStride());
    }
    
    @Nullable
    @Override
    public BufferState end() {
        this.building = false;
        if (this.vertices == 0) {
            return null;
        }
        final BufferResource resource = this.bufferWriter.build();
        return this.createBufferState(resource);
    }
    
    @Override
    public boolean building() {
        return this.building;
    }
    
    @Nullable
    private BufferState createBufferState(final BufferResource resource) {
        final int vertices = this.vertices;
        this.vertices = 0;
        return (resource == null) ? null : new BufferState(resource, this.currentProgram, vertices);
    }
    
    @Override
    public BufferConsumer putFloat(final float v0) {
        this.bufferWriter.write(v0);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putFloat(final float v0, final float v1) {
        this.bufferWriter.write(v0, v1);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putFloat(final float v0, final float v1, final float v2) {
        this.bufferWriter.write(v0, v1, v2);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putFloat(final float v0, final float v1, final float v2, final float v3) {
        this.bufferWriter.write(v0, v1, v2, v3);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putShort(final short v0) {
        this.bufferWriter.write(v0);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putShort(final short v0, final short v1) {
        this.bufferWriter.write(v0, v1);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putShort(final short v0, final short v1, final short v2) {
        this.bufferWriter.write(v0, v1, v2);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putShort(final short v0, final short v1, final short v2, final short v3) {
        this.bufferWriter.write(v0, v1, v2, v3);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putInt(final int v0) {
        this.bufferWriter.write(v0);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putInt(final int v0, final int v1) {
        this.bufferWriter.write(v0, v1);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putInt(final int v0, final int v1, final int v2) {
        this.bufferWriter.write(v0, v1, v2);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putInt(final int v0, final int v1, final int v2, final int v3) {
        this.bufferWriter.write(v0, v1, v2, v3);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putByte(final byte v0) {
        this.bufferWriter.write(v0);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putByte(final byte v0, final byte v1) {
        this.bufferWriter.write(v0, v1);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putByte(final byte v0, final byte v1, final byte v2) {
        this.bufferWriter.write(v0, v1, v2);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer putByte(final byte v0, final byte v1, final byte v2, final byte v3) {
        this.bufferWriter.write(v0, v1, v2, v3);
        this.nextElement();
        return this;
    }
    
    @Override
    public BufferConsumer endVertex() {
        ++this.vertices;
        this.bufferWriter.ensureCapacity(this.currentFormat.getStride());
        return this;
    }
    
    private void nextElement() {
        this.attributeState.next(this.bufferWriter);
    }
    
    @Override
    public int getVertices() {
        return this.vertices;
    }
    
    @Override
    public void close() {
        try {
            this.bufferWriter.close();
        }
        catch (final Exception exception) {
            DefaultBufferBuilder.LOGGER.error("An error occurred while closing the buffer builder.", exception);
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    private static class AttributeState
    {
        private final VertexFormat format;
        private VertexAttribute attribute;
        private int index;
        
        private AttributeState(final VertexFormat format) {
            this.format = format;
            this.index = 0;
            this.attribute = format.getAttributes()[this.index];
        }
        
        public void next(final BufferWriter writer) {
            final VertexAttribute[] attributes = this.format.getAttributes();
            this.index = ++this.index % attributes.length;
            this.attribute = attributes[this.index];
            if (DefaultVertexAttributes.isPadding(this.attribute)) {
                writer.addWriteOffset(this.attribute.getByteSize());
                this.next(writer);
            }
        }
    }
}
