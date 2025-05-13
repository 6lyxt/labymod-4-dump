// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.NBTTagType;

public interface NBTTagFloat extends NBTTagNumber<Float>
{
    @NotNull
    default NBTTagType type() {
        return NBTTagType.FLOAT;
    }
    
    @Nullable
    Float value();
}
