// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.nbt;

import java.util.Arrays;
import net.labymod.api.nbt.NBTTagType;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class VersionedNBTTagLongArray extends gn
{
    private long[] longArray;
    
    public VersionedNBTTagLongArray() {
    }
    
    public VersionedNBTTagLongArray(final long[] longArray) {
        this.longArray = longArray;
    }
    
    protected void a(final DataOutput output) throws IOException {
        output.writeInt(this.longArray.length);
        for (final long l : this.longArray) {
            output.writeLong(l);
        }
    }
    
    protected void a(final DataInput input, final int depth, final gh sizeTracker) throws IOException {
        sizeTracker.a(192L);
        final int length = input.readInt();
        sizeTracker.a(64L * length);
        this.longArray = new long[length];
        for (int i = 0; i < length; ++i) {
            this.longArray[i] = input.readLong();
        }
    }
    
    public byte a() {
        return NBTTagType.LONG_ARRAY.getId();
    }
    
    public String toString() {
        final StringBuilder builder = new StringBuilder("[");
        for (final long l : this.longArray) {
            builder.append(l).append(",");
        }
        return String.valueOf(builder);
    }
    
    public gn b() {
        final long[] arrayCopy = new long[this.longArray.length];
        System.arraycopy(this.longArray, 0, arrayCopy, 0, this.longArray.length);
        return new VersionedNBTTagLongArray(arrayCopy);
    }
    
    public boolean equals(final Object other) {
        return super.equals(other) && Arrays.equals(this.longArray, ((VersionedNBTTagLongArray)other).longArray);
    }
    
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.longArray);
    }
    
    public long[] getLongArray() {
        return this.longArray;
    }
}
