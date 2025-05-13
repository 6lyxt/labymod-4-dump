// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.world.levelgen;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.chunk.Heightmap;

@Mixin({ doq.class })
public abstract class MixinHeightmap implements Heightmap
{
    @Shadow
    public abstract int a(final int p0, final int p1);
    
    @Override
    public int getHeight(final int x, final int z) {
        return this.a(x, z);
    }
}
