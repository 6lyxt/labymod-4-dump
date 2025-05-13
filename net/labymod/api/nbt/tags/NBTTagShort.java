// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.NBTTagType;

public interface NBTTagShort extends NBTTagNumber<Short>
{
    @NotNull
    default NBTTagType type() {
        return NBTTagType.SHORT;
    }
    
    @Nullable
    Short value();
}
