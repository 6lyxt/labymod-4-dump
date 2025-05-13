// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

public class DefaultWidgetTextureAtlas extends AbstractTextureAtlas
{
    public DefaultWidgetTextureAtlas() {
        super(Atlases.WIDGET_ATLAS, 256, 256);
        this.register(this.createMinecraft("widget/button_disabled"), 0, 46, 200, 20, (width, height) -> this.createNineSpliceScaling(width, height, 1));
        this.register(this.createMinecraft("widget/button"), 0, 66, 200, 20, (width, height) -> this.createNineSpliceScaling(width, height, 3));
        this.register(this.createMinecraft("widget/button_highlighted"), 0, 86, 200, 20, (width, height) -> this.createNineSpliceScaling(width, height, 3));
        this.register(this.createMinecraft("hud/hotbar"), 0, 0, 182, 22, (width, height) -> new StretchScaling());
        this.register(this.createMinecraft("hud/hotbar_selection"), 0, 22, 24, 24, (width, height) -> new StretchScaling());
        AtlasUtil.buildIcons((x$0, x$1, x$2, x$3, x$4, x$5) -> rec$.register(x$0, x$1, x$2, x$3, x$4, x$5));
    }
    
    private SpriteScaling createNineSpliceScaling(final float width, final float height, final int borderSize) {
        final int w = MathHelper.ceil(width);
        final int h = MathHelper.ceil(height);
        return new NineSpliceScaling(w, h, new NineSpliceScaling.Border(borderSize, borderSize, borderSize, borderSize));
    }
}
