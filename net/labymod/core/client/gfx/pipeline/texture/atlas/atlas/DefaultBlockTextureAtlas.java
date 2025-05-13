// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas;

import net.labymod.api.Textures;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

public class DefaultBlockTextureAtlas extends AbstractTextureAtlas
{
    public DefaultBlockTextureAtlas() {
        super(Textures.Splash.BLOCKS, 256, 256);
        this.register16(this.createLabyMod("block/grass_block_top"), 0, 0);
        this.register16(this.createLabyMod("block/stone"), 1, 0);
        this.register16(this.createLabyMod("block/dirt"), 2, 0);
        this.register16(this.createLabyMod("block/grass_block_side"), 3, 0);
        this.register16(this.createLabyMod("block/oak_planks"), 4, 0);
        this.register16(this.createLabyMod("block/cobblestone"), 0, 1);
        this.register16(this.createLabyMod("block/gravel"), 3, 1);
        this.register16(this.createLabyMod("block/gold_ore"), 0, 2);
        this.register16(this.createLabyMod("block/iron_ore"), 1, 2);
        this.register16(this.createLabyMod("block/coal_ore"), 2, 2);
        this.register16(this.createLabyMod("block/diamond_ore"), 2, 3);
        this.register16(this.createLabyMod("block/redstone_ore"), 3, 3);
        this.register16(this.createLabyMod("block/glowstone"), 9, 6);
        this.register16(this.createLabyMod("block/furnace_top"), 0, 1);
        this.register16(this.createLabyMod("block/furnace_bottom"), 1, 1);
        this.register16(this.createLabyMod("block/furnace_front"), 12, 2);
        this.register16(this.createLabyMod("block/furnace_side"), 13, 2);
        this.register16(this.createLabyMod("block/furnace_front_lit"), 13, 3);
        this.register16(this.createLabyMod("block/pumpkin_top"), 6, 6);
        this.register16(this.createLabyMod("block/pumpkin"), 6, 7);
        this.register16(this.createLabyMod("block/carved_pumpkin"), 7, 7);
        this.register16(this.createLabyMod("block/carved_pumpkin_lit"), 8, 7);
        this.register16(this.createLabyMod("block/crafting_table_top"), 11, 2);
        this.register16(this.createLabyMod("block/crafting_table_bottom"), 4, 0);
        this.register16(this.createLabyMod("block/crafting_table_side"), 11, 3);
        this.register16(this.createLabyMod("block/cobweb"), 11, 0);
        this.register16(this.createLabyMod("block/poppy"), 12, 0);
        this.register16(this.createLabyMod("block/dandelion"), 13, 0);
        this.register16(this.createLabyMod("block/torch"), 0, 5);
        this.register16(this.createLabyMod("block/soul_torch"), 3, 8);
        this.register16(this.createLabyMod("block/lever"), 0, 6);
        this.register16(this.createLabyMod("block/rail_corner"), 0, 7);
        this.register16(this.createLabyMod("block/rail"), 0, 8);
        this.register16(this.createLabyMod("block/snow"), 2, 4);
        this.register16(this.createLabyMod("block/ice"), 3, 4);
        this.register16(this.createLabyMod("block/grass_block_snow"), 4, 4);
    }
}
