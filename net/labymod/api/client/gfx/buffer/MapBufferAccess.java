// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.buffer;

public enum MapBufferAccess
{
    READ_ONLY(35000), 
    WRITE_ONLY(35001), 
    READ_WRITE(35002);
    
    private final int id;
    
    private MapBufferAccess(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
