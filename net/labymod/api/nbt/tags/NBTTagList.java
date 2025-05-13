// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.nbt.NBTTag;

public interface NBTTagList<I, T extends NBTTag<I>> extends NBTTag<List<I>>
{
    @NotNull
    List<T> tags();
    
    int size();
    
    boolean isEmpty();
    
    I getValue(final int p0);
    
    T get(final int p0);
    
    void add(final T p0);
    
    void add(final int p0, final T p1);
    
    void set(final int p0, final T p1);
    
    void remove(final int p0);
    
    void remove(final T p0);
    
    void clear();
    
    @Nullable
    NBTTagType contentType();
    
    @NotNull
    default NBTTagType type() {
        return NBTTagType.LIST;
    }
}
