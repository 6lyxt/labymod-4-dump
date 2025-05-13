// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.resources.texture;

import java.io.IOException;
import net.labymod.core.client.resources.texture.concurrent.DefaultRefreshableTexture;
import net.labymod.api.client.resources.texture.InternalRefreshableTexture;

public class VersionedRefreshableTexture extends gdy implements InternalRefreshableTexture
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
    
    public void a(final aqi resourceManager) throws IOException {
    }
    
    public int getTextureId() {
        return this.a();
    }
    
    public void init() {
    }
}
