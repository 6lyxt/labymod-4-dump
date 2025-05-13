// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.DataType;

public class VertexAttribute2B extends AbstractVertexAttribute
{
    public VertexAttribute2B(final boolean unsigned, final boolean normalized) {
        super(2, unsigned ? DataType.UNSIGNED_BYTE : DataType.BYTE, normalized);
    }
}
