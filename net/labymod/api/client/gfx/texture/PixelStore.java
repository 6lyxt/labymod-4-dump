// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

public enum PixelStore
{
    UNPACK_SWAP_BYTES(3312), 
    UNPACK_LSB_FIRST(3313), 
    UNPACK_ROW_LENGTH(3314), 
    UNPACK_SKIP_ROWS(3315), 
    UNPACK_SKIP_PIXELS(3316), 
    UNPACK_ALIGNMENT(3317), 
    UNPACK_IMAGE_HEIGHT(32878), 
    UNPACK_SKIP_IMAGES(32877), 
    UNPACK_COMPRESSED_BLOCK_WIDTH(37159), 
    UNPACK_COMPRESSED_BLOCK_HEIGHT(37160), 
    UNPACK_COMPRESSED_BLOCK_DEPTH(37161), 
    UNPACK_COMPRESSED_BLOCK_SIZE(37162);
    
    private final int id;
    
    private PixelStore(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
