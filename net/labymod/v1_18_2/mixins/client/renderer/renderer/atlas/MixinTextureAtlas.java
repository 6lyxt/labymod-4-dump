// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;

@Mixin({ fax.class })
public abstract class MixinTextureAtlas implements TextureAtlas
{
    @Shadow
    @Final
    private yt l;
    @Unique
    private int labyMod$width;
    @Unique
    private int labyMod$height;
    
    @Shadow
    public abstract fay a(final yt p0);
    
    @Override
    public void register(final ResourceLocation id, final TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public TextureSprite findSprite(final ResourceLocation location) {
        final fay sprite = this.a(location.getMinecraftLocation());
        return (sprite == null) ? null : ((TextureSprite)sprite);
    }
    
    @Override
    public ResourceLocation resource() {
        return (ResourceLocation)this.l;
    }
    
    @Override
    public int getAtlasWidth() {
        return this.labyMod$width;
    }
    
    @Override
    public int getAtlasHeight() {
        return this.labyMod$height;
    }
    
    @Inject(method = { "reload" }, at = { @At("HEAD") })
    private void labyMod$setAtlasDimension(final fax.a preparations, final CallbackInfo ci) {
        this.labyMod$width = preparations.b;
        this.labyMod$height = preparations.c;
    }
}
