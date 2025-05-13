// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.blockentity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.event.Event;

public class SignBlockEntityPostLoadEvent implements Event
{
    private final SignBlockEntity sign;
    private final NBTTagCompound tag;
    
    public SignBlockEntityPostLoadEvent(@NotNull final SignBlockEntity sign, @NotNull final NBTTagCompound tag) {
        this.sign = sign;
        this.tag = tag;
    }
    
    @NotNull
    public SignBlockEntity sign() {
        return this.sign;
    }
    
    @NotNull
    public NBTTagCompound tag() {
        return this.tag;
    }
}
