// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas;

import net.labymod.api.Textures;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

public class DefaultParticleTextureAtlas extends AbstractTextureAtlas
{
    public DefaultParticleTextureAtlas() {
        super(Textures.Splash.PARTICLES, 128, 128);
        this.register8(this.createLabyMod("particle/flame"), 0, 3);
        this.register8(this.createLabyMod("particle/soul_fire_flame"), 0, 4);
        this.registerAnimatedHorizontal(this.createLabyMod("particle/generic"), 0, 0, 8, 8, 8);
    }
}
