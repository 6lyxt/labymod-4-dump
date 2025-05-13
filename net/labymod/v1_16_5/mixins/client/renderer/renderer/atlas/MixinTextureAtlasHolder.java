// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ eku.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private ekb a;
    
    @Override
    public ekb getTextureAtlas() {
        return this.a;
    }
}
