// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_4.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ fud.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private ftb a;
    
    @Override
    public ftb getTextureAtlas() {
        return this.a;
    }
}
