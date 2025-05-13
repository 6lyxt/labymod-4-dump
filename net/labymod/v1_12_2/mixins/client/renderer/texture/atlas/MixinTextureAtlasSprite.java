// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.texture.atlas;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;

@Mixin({ cdq.class })
public class MixinTextureAtlasSprite implements TextureSprite
{
    @Shadow
    private float l;
    @Shadow
    private float n;
    @Shadow
    private float m;
    @Shadow
    private float o;
    @Unique
    private TextureUV labyMod$textureUV;
    @Unique
    private SpriteScaling labyMod$spriteScaling;
    
    @Inject(method = { "initSprite" }, at = { @At("TAIL") })
    private void labyMod$initTextureUV(final int i, final int j, final int originX, final int originY, final boolean rotated, final CallbackInfo ci) {
        this.labyMod$textureUV = new DefaultTextureUV(this.l, this.n, this.m, this.o);
        this.labyMod$spriteScaling = new StretchScaling();
    }
    
    @Override
    public TextureUV uv() {
        return this.labyMod$textureUV;
    }
    
    @Override
    public SpriteScaling scaling() {
        return this.labyMod$spriteScaling;
    }
}
