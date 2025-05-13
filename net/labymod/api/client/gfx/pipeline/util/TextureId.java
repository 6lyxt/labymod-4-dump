// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.util;

import org.jetbrains.annotations.Nullable;

public final class TextureId
{
    public static final TextureId ZERO;
    private final int id;
    @Nullable
    private final Object data;
    
    private TextureId(final int id) {
        this(id, null);
    }
    
    private TextureId(final int id, @Nullable final Object data) {
        this.id = id;
        this.data = data;
    }
    
    public static TextureId of(final int id) {
        return new TextureId(id);
    }
    
    public static TextureId of(final int id, final Object data) {
        return new TextureId(id, data);
    }
    
    public int getId() {
        return this.id;
    }
    
    @Nullable
    public Object getData() {
        return this.data;
    }
    
    static {
        ZERO = of(0);
    }
}
