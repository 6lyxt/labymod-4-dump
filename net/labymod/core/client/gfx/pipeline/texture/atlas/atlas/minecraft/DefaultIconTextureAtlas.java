// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

public class DefaultIconTextureAtlas extends AbstractTextureAtlas
{
    private static final boolean ICONS_ATLAS;
    
    public DefaultIconTextureAtlas() {
        super(Atlases.ICON_ATLAS, 256, 256);
        AtlasUtil.buildIcons((x$0, x$1, x$2, x$3, x$4, x$5) -> rec$.register(x$0, x$1, x$2, x$3, x$4, x$5));
        if (DefaultIconTextureAtlas.ICONS_ATLAS) {
            this.register(this.createMinecraft("boss_bar/pink_background"), 0, 74, 182, 5, (width, height) -> new StretchScaling());
            this.register(this.createMinecraft("boss_bar/pink_progress"), 0, 79, 182, 5, (width, height) -> new StretchScaling());
        }
    }
    
    static {
        ICONS_ATLAS = MinecraftVersions.V1_8_9.orOlder();
    }
}
