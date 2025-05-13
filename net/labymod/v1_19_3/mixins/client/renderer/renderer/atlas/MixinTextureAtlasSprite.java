// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.renderer.renderer.atlas;

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

@Mixin({ fol.class })
public class MixinTextureAtlasSprite implements TextureSprite
{
    @Shadow
    @Final
    private float e;
    @Shadow
    @Final
    private float g;
    @Shadow
    @Final
    private float f;
    @Shadow
    @Final
    private float h;
    @Unique
    private TextureUV labyMod$textureUV;
    @Unique
    private SpriteScaling labyMod$spriteScaling;
    
    @Inject(method = { "<init>*" }, at = { @At("TAIL") })
    private void labyMod$initTextureUV(final CallbackInfo ci) {
        this.labyMod$textureUV = new DefaultTextureUV(this.e, this.g, this.f, this.h);
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
