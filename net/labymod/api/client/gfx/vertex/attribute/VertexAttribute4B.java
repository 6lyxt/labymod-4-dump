// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.DataType;

public class VertexAttribute4B extends AbstractVertexAttribute
{
    public VertexAttribute4B(final boolean unsigned, final boolean normalized) {
        super(4, unsigned ? DataType.UNSIGNED_BYTE : DataType.BYTE, normalized);
    }
}
