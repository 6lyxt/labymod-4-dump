// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.index;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.gfx.DataType;
import org.jetbrains.annotations.Nullable;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.buffer.BufferObject;
import java.nio.Buffer;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.Disposable;

public interface ElementBuffer extends Disposable
{
    public static final GFXBridge GFX = Laby.gfx();
    
    @Nullable
    default ElementBuffer selectBuffer(@NotNull final BufferState state, @NotNull final BufferUsage usage) {
        final IndexBuffer buffer = state.getIndexBuffer();
        if (buffer == null) {
            final GlobalIndexBuffer currentBuffer = GlobalIndexBuffer.select(state.renderProgram().mode());
            currentBuffer.bind(state.getIndices());
            if (currentBuffer.isInvalid()) {
                return null;
            }
            return new GlobalElementBuffer(currentBuffer, state.getIndices());
        }
        else {
            final BufferObject ebo = ElementBuffer.GFX.createBuffer(BufferTarget.ELEMENT_ARRAY, usage);
            if (ebo == null) {
                return null;
            }
            final ByteBuffer binaryBuffer = buffer.buffer();
            binaryBuffer.position(0);
            binaryBuffer.rewind();
            ebo.bind();
            ebo.upload(binaryBuffer);
            return new DefaultElementBuffer(ebo, buffer.type(), buffer.indices());
        }
    }
    
    void bind();
    
    void unbind();
    
    int getIndices();
    
    DataType getIndexType();
    
    default void draw(final DrawingMode mode) {
        ElementBuffer.GFX.drawElements(mode, this.getIndices(), this.getIndexType());
    }
    
    default void drawInstanced(final DrawingMode mode, final int primcount) {
        ElementBuffer.GFX.drawElementsInstanced(mode, this.getIndices(), this.getIndexType(), primcount);
    }
    
    public static class DefaultElementBuffer implements ElementBuffer
    {
        private final BufferObject ebo;
        private final DataType indexType;
        private final int indices;
        
        public DefaultElementBuffer(final BufferObject ebo, final DataType indexType, final int indices) {
            this.ebo = ebo;
            this.indexType = indexType;
            this.indices = indices;
        }
        
        @Override
        public void bind() {
            this.ebo.bind();
        }
        
        @Override
        public void unbind() {
            this.ebo.unbind();
        }
        
        @Override
        public int getIndices() {
            return this.indices;
        }
        
        @Override
        public DataType getIndexType() {
            return this.indexType;
        }
        
        @Override
        public void dispose() {
            this.ebo.dispose();
        }
    }
    
    public static class GlobalElementBuffer implements ElementBuffer
    {
        private final GlobalIndexBuffer buffer;
        private final int indices;
        
        public GlobalElementBuffer(final GlobalIndexBuffer buffer, final int indices) {
            this.buffer = buffer;
            this.indices = indices;
        }
        
        @Override
        public void bind() {
            this.buffer.bind();
        }
        
        @Override
        public void unbind() {
            this.buffer.unbind();
        }
        
        @Override
        public int getIndices() {
            return this.indices;
        }
        
        @Override
        public DataType getIndexType() {
            return this.buffer.getIndexType().getDataType();
        }
        
        @Override
        public void dispose() {
        }
    }
}
