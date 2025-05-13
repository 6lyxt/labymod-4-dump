// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.blockentity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.block.BlockPosition;

public interface BlockEntity
{
    @NotNull
    BlockPosition position();
    
    boolean isRemoved();
}
