// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.labymod.api.nbt.tags.NBTTagString;
import net.labymod.api.nbt.tags.NBTTagLongArray;
import net.labymod.api.nbt.tags.NBTTagIntArray;
import net.labymod.api.nbt.tags.NBTTagByteArray;
import net.labymod.api.nbt.tags.NBTTagFloat;
import net.labymod.api.nbt.tags.NBTTagDouble;
import net.labymod.api.nbt.tags.NBTTagLong;
import net.labymod.api.nbt.tags.NBTTagInt;
import net.labymod.api.nbt.tags.NBTTagShort;
import net.labymod.api.nbt.tags.NBTTagByte;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface NBTFactory
{
    NBTTagCompound compound();
    
     <I, T extends NBTTag<I>> NBTTagList<I, T> list();
    
    NBTTagByte create(final boolean p0);
    
    NBTTagByte create(final byte p0);
    
    NBTTagShort create(final short p0);
    
    NBTTagInt create(final int p0);
    
    NBTTagLong create(final long p0);
    
    NBTTagDouble create(final double p0);
    
    NBTTagFloat create(final float p0);
    
    NBTTagByteArray create(final byte[] p0);
    
    NBTTagIntArray create(final int[] p0);
    
    NBTTagLongArray create(final long[] p0);
    
    NBTTagString create(final String p0);
    
    void writeCompressed(final NBTTagCompound p0, final OutputStream p1) throws IOException;
    
    NBTTagCompound readCompressed(final InputStream p0) throws IOException;
    
    boolean deepEquals(final NBTTag<?> p0, final NBTTag<?> p1, final boolean p2);
}
