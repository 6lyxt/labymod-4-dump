// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt;

import java.io.DataOutput;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public interface NBTTag<T>
{
    @NotNull
    NBTTagType type();
    
    @Nullable
    T value();
    
    void write(@NotNull final DataOutput p0);
}
