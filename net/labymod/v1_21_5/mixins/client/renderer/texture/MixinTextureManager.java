// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.texture;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.resources.texture.GameTextureManager;

@Mixin({ hks.class })
public abstract class MixinTextureManager implements GameTextureManager
{
    @Shadow
    @Final
    @Mutable
    private Map<alr, hkb> c;
    
    @Shadow
    public abstract void a(final alr p0, final hkb p1);
    
    @Shadow
    public abstract void a(final alr p0, final hkh p1);
    
    @Override
    public boolean hasResource(final ResourceLocation location) {
        return this.c.get(location) != null;
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Object texture) {
        if (texture instanceof final hkb textureObject) {
            final hkb currentTexture = this.c.get(location.getMinecraftLocation());
            if (texture instanceof final hkh reloadableTexture) {
                this.a(location.getMinecraftLocation(), reloadableTexture);
            }
            else {
                this.a(location.getMinecraftLocation(), textureObject);
            }
            if (currentTexture != null) {
                currentTexture.close();
            }
            return;
        }
        throw new IllegalArgumentException("Invalid texture object");
    }
}
