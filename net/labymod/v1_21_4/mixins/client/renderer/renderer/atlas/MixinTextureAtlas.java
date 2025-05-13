// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;

@Mixin({ hes.class })
public abstract class MixinTextureAtlas implements TextureAtlas
{
    @Shadow
    @Final
    private akv k;
    @Shadow
    private int m;
    @Shadow
    private int n;
    
    @Shadow
    public abstract het a(final akv p0);
    
    @Override
    public void register(final ResourceLocation id, final TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public TextureSprite findSprite(final ResourceLocation location) {
        final het sprite = this.a(location.getMinecraftLocation());
        return (sprite == null) ? null : ((TextureSprite)sprite);
    }
    
    @Override
    public ResourceLocation resource() {
        return (ResourceLocation)this.k;
    }
    
    @Override
    public int getAtlasWidth() {
        return this.m;
    }
    
    @Override
    public int getAtlasHeight() {
        return this.n;
    }
}
