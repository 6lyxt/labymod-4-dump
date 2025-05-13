// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.DataType;

public class VertexAttribute4I extends AbstractVertexAttribute
{
    public VertexAttribute4I(final boolean unsigned, final boolean normalized) {
        super(4, unsigned ? DataType.UNSIGNED_INT : DataType.INT, normalized);
    }
}
