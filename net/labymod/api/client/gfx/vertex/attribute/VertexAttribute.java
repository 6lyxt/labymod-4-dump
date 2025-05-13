// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.DataType;

public interface VertexAttribute
{
    VertexAttribute useIntAttributePointer();
    
    void apply(final int p0, final int p1, final long p2);
    
    void apply(final int p0, final int p1, final DataType p2, final boolean p3, final int p4, final long p5);
    
    void clear(final int p0);
    
    DataType getType();
    
    int getSize();
    
    int getByteSize();
}
