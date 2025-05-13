// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.vertex;

public enum MappedVertexFormatElementUsage
{
    POSITION(cdy.m, VertexFormatElementUsage.POSITION), 
    COLOR(cdy.n, VertexFormatElementUsage.COLOR), 
    NORMAL(cdy.q, VertexFormatElementUsage.NORMAL), 
    UV_FLOAT(cdy.o, VertexFormatElementUsage.UV), 
    UV_SHORT(cdy.p, VertexFormatElementUsage.UV), 
    PADDING(cdy.r, VertexFormatElementUsage.PADDING);
    
    private final ceb element;
    private final VertexFormatElementUsage usage;
    
    private MappedVertexFormatElementUsage(final ceb element, final VertexFormatElementUsage usage) {
        this.element = element;
        this.usage = usage;
    }
    
    public ceb element() {
        return this.element;
    }
    
    public VertexFormatElementUsage usage() {
        return this.usage;
    }
    
    public static MappedVertexFormatElementUsage getMappedUsage(final ceb element) {
        for (final MappedVertexFormatElementUsage mappedValue : values()) {
            if (mappedValue.element().equals((Object)element)) {
                return mappedValue;
            }
        }
        return null;
    }
}
