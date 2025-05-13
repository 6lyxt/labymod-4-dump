// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.textures;

import net.labymod.api.util.CollectionHelper;
import java.util.List;

public class TextureResult
{
    private final List<Skin> textures;
    
    public TextureResult(final List<Skin> textures) {
        this.textures = textures;
    }
    
    public List<Skin> getTextures() {
        return this.textures;
    }
    
    public enum Type
    {
        SKIN, 
        CAPE, 
        ITEM1, 
        ITEM22;
    }
    
    public enum Order
    {
        TRENDING, 
        MOST_USED, 
        LATEST;
        
        public static final List<Order> VALUES;
        
        static {
            VALUES = CollectionHelper.asUnmodifiableList(values());
        }
    }
}
