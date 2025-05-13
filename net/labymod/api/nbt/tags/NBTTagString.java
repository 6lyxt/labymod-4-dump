// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.NBTTag;

public interface NBTTagString extends NBTTag<String>
{
    @NotNull
    default NBTTagType type() {
        return NBTTagType.STRING;
    }
    
    @Nullable
    String value();
}
