// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.texture;

import java.io.IOException;
import net.labymod.core.client.resources.texture.concurrent.DefaultRefreshableTexture;
import net.labymod.api.client.resources.texture.InternalRefreshableTexture;

public class VersionedRefreshableTexture extends fug implements InternalRefreshableTexture
{
    private final DefaultRefreshableTexture texture;
    
    public VersionedRefreshableTexture(final DefaultRefreshableTexture texture) {
        this.texture = texture;
    }
    
    public int a() {
        return this.texture.getTextureId();
    }
    
    public void b() {
        if (this.texture.wasReleased()) {
            return;
        }
        this.texture.release();
    }
    
    public void a(final akx resourceManager) throws IOException {
    }
    
    public int getTextureId() {
        return this.a();
    }
    
    public void init() {
    }
}
