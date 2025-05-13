// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.DataType;
import net.labymod.api.client.gfx.GFXBridge;

public abstract class AbstractVertexAttribute implements VertexAttribute
{
    protected final GFXBridge bridge;
    private final int size;
    private final DataType type;
    private final boolean normalized;
    private final int byteSize;
    private boolean useIntAttributePointer;
    
    protected AbstractVertexAttribute(final int size, final DataType type, final boolean normalized) {
        this.useIntAttributePointer = false;
        this.bridge = Laby.labyAPI().gfxRenderPipeline().gfx();
        this.size = size;
        this.type = type;
        this.normalized = normalized;
        this.byteSize = this.size * type.getSize();
    }
    
    @Override
    public VertexAttribute useIntAttributePointer() {
        this.useIntAttributePointer = true;
        return this;
    }
    
    @Override
    public void apply(final int index, final int stride, final long pointer) {
        this.apply(index, this.size, this.type, this.normalized, stride, pointer);
    }
    
    @Override
    public void apply(final int index, final int size, final DataType type, final boolean normalized, final int stride, final long pointer) {
        this.bridge.enableVertexAttributeArray(index);
        if (this.useIntAttributePointer && type != DataType.FLOAT) {
            this.bridge.vertexAttributeIntPointer(index, size, type, stride, pointer);
        }
        else {
            this.bridge.vertexAttributePointer(index, size, type, normalized, stride, pointer);
        }
    }
    
    @Override
    public void clear(final int index) {
        this.bridge.disableVertexAttributeArray(index);
    }
    
    @Override
    public DataType getType() {
        return this.type;
    }
    
    @Override
    public int getSize() {
        return this.size;
    }
    
    @Override
    public int getByteSize() {
        return this.byteSize;
    }
}
