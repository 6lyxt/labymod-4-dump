// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ fvw.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private fuu a;
    
    @Override
    public fuu getTextureAtlas() {
        return this.a;
    }
}
