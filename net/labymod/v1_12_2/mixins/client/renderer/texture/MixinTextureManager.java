// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.texture;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.resources.texture.GameTextureManager;

@Mixin({ cdr.class })
public abstract class MixinTextureManager implements GameTextureManager
{
    @Shadow
    @Final
    private Map<nf, cds> c;
    
    @Shadow
    public abstract boolean a(final nf p0, final cds p1);
    
    @Override
    public boolean hasResource(final ResourceLocation location) {
        return this.c.get(location) != null;
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Object texture) {
        if (texture instanceof final cds textureObject) {
            final cds currentTexture = this.c.get(location);
            this.a((nf)location, textureObject);
            if (currentTexture != null) {
                cdt.a(currentTexture.b());
            }
            return;
        }
        throw new IllegalArgumentException("Invalid texture object");
    }
    
    @Inject(method = { "deleteTexture" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureUtil;deleteTexture(I)V", shift = At.Shift.BEFORE) })
    private void labyMod$deleteTexture(final nf location, final CallbackInfo ci) {
        this.c.remove(location);
    }
}
