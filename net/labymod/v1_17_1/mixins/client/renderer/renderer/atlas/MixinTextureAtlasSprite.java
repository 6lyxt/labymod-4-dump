// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;

@Mixin({ exm.class })
public class MixinTextureAtlasSprite implements TextureSprite
{
    @Shadow
    @Final
    private float j;
    @Shadow
    @Final
    private float l;
    @Shadow
    @Final
    private float k;
    @Shadow
    @Final
    private float m;
    @Unique
    private TextureUV labyMod$textureUV;
    @Unique
    private SpriteScaling labyMod$spriteScaling;
    
    @Inject(method = { "<init>*" }, at = { @At("TAIL") })
    private void labyMod$initTextureUV(final CallbackInfo ci) {
        this.labyMod$textureUV = new DefaultTextureUV(this.j, this.l, this.k, this.m);
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
