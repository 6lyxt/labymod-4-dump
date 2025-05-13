// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ gro.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private gqk a;
    
    @Override
    public gqk getTextureAtlas() {
        return this.a;
    }
}
