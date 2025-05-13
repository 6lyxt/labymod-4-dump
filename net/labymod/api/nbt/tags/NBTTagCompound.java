// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt.tags;

import java.util.Set;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import net.labymod.api.nbt.NBTTag;

public interface NBTTagCompound extends NBTTag<Map<String, NBTTag<?>>>
{
    void set(@NotNull final String p0, @NotNull final NBTTag<?> p1);
    
    void remove(@NotNull final String p0);
    
    void clear();
    
    int size();
    
    @Nullable
    NBTTag<?> get(@NotNull final String p0);
    
    boolean contains(@NotNull final String p0);
    
    boolean contains(@NotNull final String p0, @NotNull final NBTTagType p1);
    
    void setByte(@NotNull final String p0, final byte p1);
    
    void setShort(@NotNull final String p0, final short p1);
    
    void setInt(@NotNull final String p0, final int p1);
    
    void setLong(@NotNull final String p0, final long p1);
    
    void setFloat(@NotNull final String p0, final float p1);
    
    void setDouble(@NotNull final String p0, final double p1);
    
    void setByteArray(@NotNull final String p0, final byte[] p1);
    
    void setIntArray(@NotNull final String p0, final int[] p1);
    
    void setLongArray(@NotNull final String p0, final long[] p1);
    
    void setString(@NotNull final String p0, @NotNull final String p1);
    
    void setBoolean(@NotNull final String p0, final boolean p1);
    
    byte getByte(@NotNull final String p0);
    
    short getShort(@NotNull final String p0);
    
    int getInt(@NotNull final String p0);
    
    long getLong(@NotNull final String p0);
    
    float getFloat(@NotNull final String p0);
    
    double getDouble(@NotNull final String p0);
    
    byte[] getByteArray(@NotNull final String p0);
    
    int[] getIntArray(@NotNull final String p0);
    
    long[] getLongArray(@NotNull final String p0);
    
    String getString(@NotNull final String p0);
    
    boolean getBoolean(@NotNull final String p0);
    
    NBTTagCompound getCompound(@NotNull final String p0);
    
     <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull final String p0, @NotNull final NBTTagType p1);
    
    NBTTagType getType(@NotNull final String p0);
    
    @NotNull
    Set<String> keySet();
    
    @NotNull
    default NBTTagType type() {
        return NBTTagType.COMPOUND;
    }
}
