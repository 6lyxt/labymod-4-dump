// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex;

public enum PolygonMode
{
    POINT(6912), 
    LINE(6913), 
    FILL(6914);
    
    private final int type;
    
    private PolygonMode(final int type) {
        this.type = type;
    }
    
    public int type() {
        return this.type;
    }
}
