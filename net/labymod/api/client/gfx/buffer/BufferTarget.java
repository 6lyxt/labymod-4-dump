// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.buffer;

public enum BufferTarget
{
    ARRAY(34962), 
    ELEMENT_ARRAY(34963), 
    PIXEL_PACK(35051), 
    PIXEL_UNPACK(35052), 
    TRANSFORM_FEEDBACK(36386), 
    UNIFORM(35345), 
    TEXTURE(35882), 
    COPY_READ(36662), 
    COPY_WRITE(36663), 
    DRAW_INDIRECT(36671), 
    ATOMIC_COUNTER(37568), 
    DISPATCH_INDIRECT(37102), 
    SHADER_STORAGE(37074);
    
    private final int id;
    
    private BufferTarget(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
