// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import net.labymod.api.client.resources.texture.TextureLoader;

public class LoaderEntry
{
    private final TextureLoader textureLoader;
    private final int priority;
    
    public LoaderEntry(final TextureLoader textureLoader, final int priority) {
        this.textureLoader = textureLoader;
        this.priority = priority;
    }
    
    public TextureLoader getTextureLoader() {
        return this.textureLoader;
    }
    
    public int getPriority() {
        return this.priority;
    }
}
