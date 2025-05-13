// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.NBTTag;

public interface NBTTagLongArray extends NBTTag<long[]>
{
    @NotNull
    default NBTTagType type() {
        return NBTTagType.LONG_ARRAY;
    }
}
