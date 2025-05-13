// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.block;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface BlockColorProvider
{
    int getColor(final BlockState p0);
    
    default int getColorMultiplier(final BlockState state) {
        return this.getColorMultiplier(state, -1, -1, 1, 1);
    }
    
    int getColorMultiplier(final BlockState p0, final int p1, final int p2, final int p3, final int p4);
}
