// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer.renderer.atlas;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_18_2.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;

@Mixin({ fbq.class })
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor
{
    @Shadow
    @Final
    private fax a;
    
    @Override
    public fax getTextureAtlas() {
        return this.a;
    }
}
