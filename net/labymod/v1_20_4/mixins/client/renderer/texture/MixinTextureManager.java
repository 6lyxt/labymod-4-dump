// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.resources.texture.GameTextureManager;

@Mixin({ geo.class })
public abstract class MixinTextureManager implements GameTextureManager
{
    @Shadow
    @Final
    @Mutable
    private Map<ahg, gdy> c;
    
    @Shadow
    public abstract void a(final ahg p0, final gdy p1);
    
    @Override
    public boolean hasResource(final ResourceLocation location) {
        return this.c.get(location) != null;
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Object texture) {
        if (texture instanceof final gdy textureObject) {
            final gdy currentTexture = this.c.get(location);
            this.a((ahg)location, textureObject);
            if (currentTexture != null) {
                TextureUtil.releaseTextureId(currentTexture.a());
            }
            return;
        }
        throw new IllegalArgumentException("Invalid texture object");
    }
}
