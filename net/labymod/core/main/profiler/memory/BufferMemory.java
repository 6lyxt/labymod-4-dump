// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.profiler.memory;

public class BufferMemory
{
    private final int id;
    private int type;
    private long size;
    
    public BufferMemory(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public void setSize(final long size) {
        this.size = size;
    }
}
