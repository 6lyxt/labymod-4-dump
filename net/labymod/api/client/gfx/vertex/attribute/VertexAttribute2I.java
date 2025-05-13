// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.DataType;

public class VertexAttribute2I extends AbstractVertexAttribute
{
    public VertexAttribute2I(final boolean unsigned, final boolean normalized) {
        super(2, unsigned ? DataType.UNSIGNED_INT : DataType.INT, normalized);
    }
}
