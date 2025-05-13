// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

public enum DataType
{
    BYTE(5120, 1), 
    UNSIGNED_BYTE(5121, 1), 
    SHORT(5122, 2), 
    UNSIGNED_SHORT(5123, 2), 
    INT(5124, 4), 
    UNSIGNED_INT(5125, 4), 
    FLOAT(5126, 4);
    
    private final int id;
    private final int size;
    
    private DataType(final int id, final int size) {
        this.id = id;
        this.size = size;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getSize() {
        return this.size;
    }
}
