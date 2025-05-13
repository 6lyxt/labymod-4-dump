// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.texture.atlas;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;

@Mixin({ cdp.class })
public abstract class MixinTextureAtlas implements TextureAtlas
{
    @Unique
    private int labyMod$width;
    @Unique
    private int labyMod$height;
    
    @Shadow
    public abstract cdq a(final String p0);
    
    @Override
    public void register(final ResourceLocation id, final TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public TextureSprite findSprite(final ResourceLocation location) {
        final cdq sprite = this.a(location.getPath());
        return (sprite == null) ? null : ((TextureSprite)sprite);
    }
    
    @Override
    public ResourceLocation resource() {
        return (ResourceLocation)cdp.g;
    }
    
    @Override
    public int getAtlasWidth() {
        return this.labyMod$width;
    }
    
    @Override
    public int getAtlasHeight() {
        return this.labyMod$height;
    }
    
    @Redirect(method = { "loadTextureAtlas" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/Stitcher;doStitch()V"))
    private void labyMod$setAtlasDimension(final cdn stitcher) {
        stitcher.c();
        this.labyMod$width = stitcher.a();
        this.labyMod$height = stitcher.b();
    }
}
