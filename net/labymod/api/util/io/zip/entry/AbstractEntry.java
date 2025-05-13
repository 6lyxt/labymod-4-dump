// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry;

public abstract class AbstractEntry<T extends Entry<T>> implements Entry<T>
{
    private final String name;
    private final long time;
    private final byte[] data;
    
    public AbstractEntry(final String name, final long time, final byte[] data) {
        this.name = name;
        this.time = time;
        this.data = data;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public long getTime() {
        return this.time;
    }
    
    @Override
    public byte[] getData() {
        return this.data;
    }
}
