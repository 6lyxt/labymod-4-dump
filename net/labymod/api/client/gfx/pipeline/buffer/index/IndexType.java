// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.index;

import net.labymod.api.client.gfx.DataType;

public enum IndexType
{
    INT(DataType.UNSIGNED_INT), 
    SHORT(DataType.UNSIGNED_SHORT);
    
    private final DataType dataType;
    
    private IndexType(final DataType dataType) {
        this.dataType = dataType;
    }
    
    public DataType getDataType() {
        return this.dataType;
    }
    
    public int getSize() {
        return this.dataType.getSize();
    }
    
    public static IndexType select(final int indices) {
        return ((indices & 0xFFFF0000) != 0x0) ? IndexType.INT : IndexType.SHORT;
    }
}
