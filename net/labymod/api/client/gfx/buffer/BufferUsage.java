// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.buffer;

public enum BufferUsage
{
    STREAM_DRAW(35040, false, true), 
    STREAM_READ(35041, true, false), 
    STREAM_COPY(35042, false, false), 
    STATIC_DRAW(35044, false, true), 
    STATIC_READ(35045, true, false), 
    STATIC_COPY(35046, false, false), 
    DYNAMIC_DRAW(35048, false, true), 
    DYNAMIC_READ(35049, true, false), 
    DYNAMIC_COPY(35050, false, false);
    
    private final int id;
    private final boolean readable;
    private final boolean writeable;
    
    private BufferUsage(final int id, final boolean readable, final boolean writeable) {
        this.id = id;
        this.readable = readable;
        this.writeable = writeable;
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean isReadable() {
        return this.readable;
    }
    
    public boolean isWriteable() {
        return this.writeable;
    }
}
