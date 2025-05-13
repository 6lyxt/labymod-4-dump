// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target.stencil;

public enum StencilOperation
{
    KEEP(7680), 
    ZERO(0), 
    REPLACE(7681), 
    INCR(7682), 
    DECR(7683), 
    INVERT(5386), 
    INCR_WRAP(34055), 
    DECR_WRAP(34056);
    
    private final int id;
    
    private StencilOperation(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
