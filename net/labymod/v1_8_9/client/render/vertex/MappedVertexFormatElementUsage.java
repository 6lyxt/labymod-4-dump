// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render.vertex;

public enum MappedVertexFormatElementUsage
{
    POSITION(bms.m, VertexFormatElementUsage.POSITION), 
    COLOR(bms.n, VertexFormatElementUsage.COLOR), 
    NORMAL(bms.q, VertexFormatElementUsage.NORMAL), 
    UV_FLOAT(bms.o, VertexFormatElementUsage.UV), 
    UV_SHORT(bms.p, VertexFormatElementUsage.UV), 
    PADDING(bms.r, VertexFormatElementUsage.PADDING);
    
    private final bmv element;
    private final VertexFormatElementUsage usage;
    
    private MappedVertexFormatElementUsage(final bmv element, final VertexFormatElementUsage usage) {
        this.element = element;
        this.usage = usage;
    }
    
    public bmv element() {
        return this.element;
    }
    
    public VertexFormatElementUsage usage() {
        return this.usage;
    }
    
    public static MappedVertexFormatElementUsage getMappedUsage(final bmv element) {
        for (final MappedVertexFormatElementUsage mappedValue : values()) {
            if (mappedValue.element().equals((Object)element)) {
                return mappedValue;
            }
        }
        return null;
    }
}
