// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;

@Mixin({ hkq.class })
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
    @Shadow
    @Final
    private hkk b;
    @Unique
    private TextureUV labyMod$textureUV;
    @Unique
    private SpriteScaling labyMod$spriteScaling;
    
    @Inject(method = { "<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/SpriteContents;IIII)V" }, at = { @At("TAIL") })
    private void labyMod$initTextureVanilla(final CallbackInfo ci) {
        this.labyMod$initialize();
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V" }, at = { @At("TAIL") })
    @Dynamic
    private void labyMod$optifine$initTextureUV0(final CallbackInfo ci) {
        this.labyMod$initialize();
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V" }, at = { @At("TAIL") })
    @Dynamic
    private void labyMod$optifine$initTextureUV1(final CallbackInfo ci) {
        this.labyMod$initialize();
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIILnet/minecraft/client/renderer/texture/TextureAtlas;Lnet/optifine/shaders/ShadersTextureType;)V" }, at = { @At("TAIL") })
    @Dynamic
    private void labyMod$optifine$initTextureUV2(final CallbackInfo ci) {
        this.labyMod$initialize();
    }
    
    @Unique
    private void labyMod$initialize() {
        if (this.b != null) {
            this.labyMod$initializeApi();
        }
    }
    
    @Inject(method = { "init" }, at = { @At("TAIL") }, require = 0, expect = 0)
    @Dynamic
    private void labyMod$init(final alr location, final hkk contents, final int atlasWidth, final int atlasHeight, final int x, final int y, final CallbackInfo ci) {
        this.labyMod$initializeApi();
    }
    
    @Unique
    private void labyMod$initializeApi() {
        this.labyMod$textureUV = new DefaultTextureUV(this.e, this.g, this.f, this.h);
        final hmh metadataSection = this.b.f().a(hmh.c).orElse(hmh.a);
        final hmi scaling = metadataSection.a();
        if (scaling instanceof final hmi.c tile) {
            this.labyMod$spriteScaling = new TileScaling(tile.b(), tile.c());
        }
        else if (scaling instanceof final hmi.a nineSlice) {
            final hmi.a.a border = nineSlice.d();
            this.labyMod$spriteScaling = new NineSpliceScaling(nineSlice.b(), nineSlice.c(), new NineSpliceScaling.Border(border.a(), border.b(), border.c(), border.d()));
        }
        else {
            this.labyMod$spriteScaling = new StretchScaling();
        }
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
