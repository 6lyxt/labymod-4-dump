// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_6.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ gqe.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private gpa a;
    
    @Override
    public gpa getTextureAtlas() {
        return this.a;
    }
}
