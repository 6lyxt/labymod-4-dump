// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ fkw.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private fkd a;
    
    @Override
    public fkd getTextureAtlas() {
        return this.a;
    }
}
