// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.NBTTagType;

public interface NBTTagDouble extends NBTTagNumber<Double>
{
    @NotNull
    default NBTTagType type() {
        return NBTTagType.DOUBLE;
    }
    
    @Nullable
    Double value();
}
