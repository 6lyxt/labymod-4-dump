// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.texture;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.textures.TextureFormat;
import java.util.function.IntSupplier;
import java.util.Objects;
import net.labymod.core.client.resources.texture.concurrent.DefaultRefreshableTexture;
import net.labymod.api.client.resources.texture.InternalRefreshableTexture;

public class VersionedRefreshableTexture extends hkb implements InternalRefreshableTexture
{
    private final DefaultRefreshableTexture refreshableTexture;
    
    public VersionedRefreshableTexture(final DefaultRefreshableTexture refreshableTexture) {
        this.refreshableTexture = refreshableTexture;
    }
    
    public int getTextureId() {
        return this.refreshableTexture.getTextureId();
    }
    
    public void init() {
        final int width = 1;
        final int height = 1;
        final int level = 1;
        final DefaultRefreshableTexture refreshableTexture = this.refreshableTexture;
        Objects.requireNonNull(refreshableTexture);
        this.a = (GpuTexture)new DynamicGlTexture(width, height, level, refreshableTexture::getTextureId);
    }
    
    private static class DynamicGlTexture extends fjl
    {
        private final IntSupplier id;
        
        public DynamicGlTexture(final int width, final int height, final int level, final IntSupplier id) {
            super("DynamicGlTexture(" + id.getAsInt(), TextureFormat.RGBA8, width, height, level, id.getAsInt());
            this.id = id;
        }
        
        public void close() {
            if (!this.b) {
                this.b = true;
                GlStateManager._deleteTexture(this.b());
            }
        }
        
        public int b() {
            return this.id.getAsInt();
        }
    }
}
