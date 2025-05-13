// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.icon.ping;

import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;

public abstract class PingIcon
{
    private static final ResourceLocation ICONS;
    
    public abstract Icon get(final int p0);
    
    protected Icon getIcon(final ResourceLocation location) {
        final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(PingIcon.ICONS);
        return Icon.sprite(atlas, atlas.findSprite(location));
    }
    
    static {
        ICONS = Atlases.ICON_ATLAS;
    }
}
