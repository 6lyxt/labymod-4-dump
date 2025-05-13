// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.DataType;

public class VertexAttribute3S extends AbstractVertexAttribute
{
    public VertexAttribute3S(final boolean unsigned, final boolean normalized) {
        super(3, unsigned ? DataType.UNSIGNED_SHORT : DataType.SHORT, normalized);
    }
}
