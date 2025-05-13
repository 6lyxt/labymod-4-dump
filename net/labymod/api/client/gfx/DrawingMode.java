// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

import net.labymod.api.util.function.IntIntFunction;

public enum DrawingMode
{
    LINES(4, count -> count / 4 * 6), 
    TRIANGLES(4, count -> count), 
    QUADS(4, count -> count / 4 * 6);
    
    private final int id;
    private final IntIntFunction indexFunction;
    
    private DrawingMode(final int id, final IntIntFunction indexFunction) {
        this.id = id;
        this.indexFunction = indexFunction;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getIndexCount(final int vertexCount) {
        return this.indexFunction.apply(vertexCount);
    }
}
