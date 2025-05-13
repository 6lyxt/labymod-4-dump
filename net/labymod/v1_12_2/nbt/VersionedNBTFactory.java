// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.nbt;

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
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.models.Implements;
import net.labymod.api.nbt.NBTFactory;

@Implements(NBTFactory.class)
public class VersionedNBTFactory implements NBTFactory
{
    @Override
    public NBTTagCompound compound() {
        return (NBTTagCompound)new fy();
    }
    
    @Override
    public <I, T extends NBTTag<I>> NBTTagList<I, T> list() {
        return (NBTTagList<I, T>)new ge();
    }
    
    @Override
    public NBTTagByte create(final boolean value) {
        return this.create((byte)(value ? 1 : 0));
    }
    
    @Override
    public NBTTagByte create(final byte value) {
        return (NBTTagByte)new fx(value);
    }
    
    @Override
    public NBTTagShort create(final short value) {
        return (NBTTagShort)new gl(value);
    }
    
    @Override
    public NBTTagInt create(final int value) {
        return (NBTTagInt)new gd(value);
    }
    
    @Override
    public NBTTagLong create(final long value) {
        return (NBTTagLong)new gg(value);
    }
    
    @Override
    public NBTTagDouble create(final double value) {
        return (NBTTagDouble)new fz(value);
    }
    
    @Override
    public NBTTagFloat create(final float value) {
        return (NBTTagFloat)new gb(value);
    }
    
    @Override
    public NBTTagByteArray create(final byte[] value) {
        return (NBTTagByteArray)new fw(value);
    }
    
    @Override
    public NBTTagIntArray create(final int[] value) {
        return (NBTTagIntArray)new gc(value);
    }
    
    @Override
    public NBTTagLongArray create(final long[] value) {
        return (NBTTagLongArray)new VersionedNBTTagLongArray(value);
    }
    
    @Override
    public NBTTagString create(final String value) {
        return (NBTTagString)new gm(value);
    }
    
    @Override
    public void writeCompressed(final NBTTagCompound tag, final OutputStream outputStream) throws IOException {
        gi.a((fy)tag, outputStream);
    }
    
    @Override
    public NBTTagCompound readCompressed(final InputStream inputStream) throws IOException {
        return (NBTTagCompound)gi.a(inputStream);
    }
    
    @Override
    public boolean deepEquals(final NBTTag<?> a, final NBTTag<?> b, final boolean compareLists) {
        if (a == null || b == null) {
            return a == null && b == null;
        }
        return gj.a((gn)a, (gn)b, compareLists);
    }
}
