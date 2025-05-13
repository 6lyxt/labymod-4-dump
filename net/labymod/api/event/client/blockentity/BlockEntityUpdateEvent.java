// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.blockentity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.Event;

public class BlockEntityUpdateEvent implements Event
{
    private final BlockEntity blockEntity;
    
    public BlockEntityUpdateEvent(@NotNull final BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }
    
    @NotNull
    public BlockEntity blockEntity() {
        return this.blockEntity;
    }
}
