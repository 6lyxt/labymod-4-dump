// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.resources.texture;

import java.io.IOException;
import net.labymod.core.client.resources.texture.concurrent.DefaultRefreshableTexture;
import net.labymod.api.client.resources.texture.InternalRefreshableTexture;

public class VersionedRefreshableTexture extends exa implements InternalRefreshableTexture
{
    private final DefaultRefreshableTexture texture;
    
    public VersionedRefreshableTexture(final DefaultRefreshableTexture texture) {
        this.texture = texture;
    }
    
    public int b() {
        return this.texture.getTextureId();
    }
    
    public void c() {
        if (this.texture.wasReleased()) {
            return;
        }
        this.texture.release();
    }
    
    public void a(final adt resourceManager) throws IOException {
    }
    
    public int getTextureId() {
        return this.b();
    }
    
    public void init() {
    }
}
