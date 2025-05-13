// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.nbt;

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
import java.util.Collection;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.util.CastUtil;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.models.Implements;
import net.labymod.api.nbt.NBTFactory;

@Implements(NBTFactory.class)
public class VersionedNBTFactory implements NBTFactory
{
    @Override
    public NBTTagCompound compound() {
        return CastUtil.cast(new ua());
    }
    
    @Override
    public <I, T extends NBTTag<I>> NBTTagList<I, T> list() {
        return CastUtil.cast((Collection<?>)new ug());
    }
    
    @Override
    public NBTTagByte create(final boolean value) {
        return CastUtil.cast(ty.a(value));
    }
    
    @Override
    public NBTTagByte create(final byte value) {
        return CastUtil.cast(ty.a(value));
    }
    
    @Override
    public NBTTagShort create(final short value) {
        return CastUtil.cast(ut.a(value));
    }
    
    @Override
    public NBTTagInt create(final int value) {
        return CastUtil.cast(uf.a(value));
    }
    
    @Override
    public NBTTagLong create(final long value) {
        return CastUtil.cast(ui.a(value));
    }
    
    @Override
    public NBTTagDouble create(final double value) {
        return CastUtil.cast(ub.a(value));
    }
    
    @Override
    public NBTTagFloat create(final float value) {
        return CastUtil.cast(ud.a(value));
    }
    
    @Override
    public NBTTagByteArray create(final byte[] value) {
        return CastUtil.cast(new tx(value));
    }
    
    @Override
    public NBTTagIntArray create(final int[] value) {
        return CastUtil.cast(new ue(value));
    }
    
    @Override
    public NBTTagLongArray create(final long[] value) {
        return CastUtil.cast(new uh(value));
    }
    
    @Override
    public NBTTagString create(final String value) {
        return CastUtil.cast(uy.a(value));
    }
    
    @Override
    public void writeCompressed(final NBTTagCompound tag, final OutputStream outputStream) throws IOException {
        un.a((ua)CastUtil.cast(tag), outputStream);
    }
    
    @Override
    public NBTTagCompound readCompressed(final InputStream inputStream) throws IOException {
        return CastUtil.cast(un.a(inputStream, uj.a()));
    }
    
    @Override
    public boolean deepEquals(final NBTTag<?> a, final NBTTag<?> b, final boolean compareLists) {
        if (a == null || b == null) {
            return a == null && b == null;
        }
        return up.a((va)CastUtil.cast(a), (va)CastUtil.cast(b), compareLists);
    }
}
