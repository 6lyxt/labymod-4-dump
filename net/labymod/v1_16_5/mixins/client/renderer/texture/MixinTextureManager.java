// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.texture;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.resources.texture.GameTextureManager;

@Mixin({ ekd.class })
public abstract class MixinTextureManager implements GameTextureManager
{
    @Shadow
    @Final
    @Mutable
    private Map<vk, ejq> c;
    
    @Shadow
    public abstract void a(final vk p0, final ejq p1);
    
    @Override
    public boolean hasResource(final ResourceLocation location) {
        return this.c.get(location) != null;
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Object texture) {
        if (texture instanceof final ejq textureObject) {
            final ejq currentTexture = this.c.get(location);
            this.a((vk)location, textureObject);
            if (currentTexture != null) {
                dex.a(currentTexture.b());
            }
            return;
        }
        throw new IllegalArgumentException("Invalid texture object");
    }
    
    @Insert(method = { "release(Lnet/minecraft/resources/ResourceLocation;)V" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/TextureUtil;releaseTextureId(I)V", shift = At.Shift.BEFORE, ordinal = 0))
    public void labyMod$deleteTexture(final vk location, final InsertInfo callback) {
        this.c.remove(location);
    }
}
