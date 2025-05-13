// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ hlv.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private hkp a;
    
    @Override
    public hkp getTextureAtlas() {
        return this.a;
    }
}
