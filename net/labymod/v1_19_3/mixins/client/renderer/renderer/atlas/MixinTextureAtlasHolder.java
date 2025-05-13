// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ fpl.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private fok a;
    
    @Override
    public fok getTextureAtlas() {
        return this.a;
    }
}
