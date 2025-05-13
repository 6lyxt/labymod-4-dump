// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

public class DefaultBarsTextureAtlas extends AbstractTextureAtlas
{
    private static final String[] COLOR_NAMES;
    private static final String[] NOTCH_REPEAT;
    
    public DefaultBarsTextureAtlas() {
        super(Atlases.BARS_ATLAS, 256, 256);
        for (int index = 0; index < DefaultBarsTextureAtlas.COLOR_NAMES.length; ++index) {
            this.register(this.createMinecraft("boss_bar/" + DefaultBarsTextureAtlas.COLOR_NAMES[index] + "_background"), 0, index * 10, 182, 5, (width, height) -> new StretchScaling());
            this.register(this.createMinecraft("boss_bar/" + DefaultBarsTextureAtlas.COLOR_NAMES[index] + "_progress"), 0, 5 + index * 10, 182, 5, (width, height) -> new StretchScaling());
        }
        final int notchedY = DefaultBarsTextureAtlas.COLOR_NAMES.length * 10 + 10;
        for (int index2 = 0; index2 < DefaultBarsTextureAtlas.NOTCH_REPEAT.length; ++index2) {
            this.register(this.createMinecraft("boss_bar/notched_" + DefaultBarsTextureAtlas.NOTCH_REPEAT[index2] + "_background"), 0, notchedY + index2 * 10, 182, 5, (width, height) -> new StretchScaling());
            this.register(this.createMinecraft("boss_bar/notched_" + DefaultBarsTextureAtlas.NOTCH_REPEAT[index2] + "_progress"), 0, notchedY + 5 + index2 * 10, 182, 5, (width, height) -> new StretchScaling());
        }
    }
    
    static {
        COLOR_NAMES = new String[] { "pink", "blue", "red", "green", "yellow", "purple", "white" };
        NOTCH_REPEAT = new String[] { "6", "10", "12", "20" };
    }
}
