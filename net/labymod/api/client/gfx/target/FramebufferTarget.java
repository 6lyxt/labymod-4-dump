// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target;

public enum FramebufferTarget
{
    BOTH(36160), 
    DRAW(36009), 
    READ(36008);
    
    private final int id;
    
    private FramebufferTarget(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
