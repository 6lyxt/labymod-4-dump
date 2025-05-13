// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.blockentity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.Event;

public class BlockEntityPreLoadEvent implements Event
{
    private final BlockEntity blockEntity;
    private final NBTTagCompound tag;
    
    public BlockEntityPreLoadEvent(@NotNull final BlockEntity blockEntity, @NotNull final NBTTagCompound tag) {
        this.blockEntity = blockEntity;
        this.tag = tag;
    }
    
    @NotNull
    public BlockEntity blockEntity() {
        return this.blockEntity;
    }
    
    @NotNull
    public NBTTagCompound tag() {
        return this.tag;
    }
}
