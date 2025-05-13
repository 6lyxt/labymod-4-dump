// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.v1_8_9.nbt.VersionedNBTTagLongArray;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.nbt.NBTTag;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagCompound;

@Implements({ @Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ dn.class })
public abstract class MixinNBTTagCompound implements NBTTagCompound
{
    @Shadow
    private Map<String, eb> b;
    
    @Shadow
    public abstract void a(final String p0, final eb p1);
    
    @Shadow
    public abstract void o(final String p0);
    
    @Shadow
    public abstract eb a(final String p0);
    
    @Shadow
    public abstract boolean c(final String p0);
    
    @Shadow
    public abstract boolean b(final String p0, final int p1);
    
    @Shadow
    public abstract void a(final String p0, final byte p1);
    
    @Shadow
    public abstract void a(final String p0, final short p1);
    
    @Shadow
    public abstract void a(final String p0, final int p1);
    
    @Shadow
    public abstract void a(final String p0, final long p1);
    
    @Shadow
    public abstract void a(final String p0, final float p1);
    
    @Shadow
    public abstract void a(final String p0, final double p1);
    
    @Shadow
    public abstract void a(final String p0, final String p1);
    
    @Shadow
    public abstract void a(final String p0, final byte[] p1);
    
    @Shadow
    public abstract void a(final String p0, final int[] p1);
    
    @Shadow
    public abstract void a(final String p0, final boolean p1);
    
    @Shadow
    public abstract byte d(final String p0);
    
    @Shadow
    public abstract short e(final String p0);
    
    @Shadow
    public abstract int f(final String p0);
    
    @Shadow
    public abstract long g(final String p0);
    
    @Shadow
    public abstract float h(final String p0);
    
    @Shadow
    public abstract double i(final String p0);
    
    @Shadow
    public abstract String j(final String p0);
    
    @Shadow
    public abstract byte[] k(final String p0);
    
    @Shadow
    public abstract int[] l(final String p0);
    
    @Shadow
    public abstract dn m(final String p0);
    
    @Shadow
    public abstract du c(final String p0, final int p1);
    
    @Shadow
    public abstract boolean n(final String p0);
    
    @Shadow
    public abstract Set<String> c();
    
    @Shadow
    public abstract byte b(final String p0);
    
    @Shadow
    protected abstract b a(final String p0, final int p1, final ClassCastException p2);
    
    @Nullable
    @Override
    public Map<String, NBTTag<?>> value() {
        return (Map<String, NBTTag<?>>)this.b;
    }
    
    @Intrinsic
    public int labyMod$size() {
        return this.b.size();
    }
    
    @Intrinsic
    public void labyMod$set(@NotNull final String key, @NotNull final NBTTag<?> tag) {
        this.a(key, (eb)tag);
    }
    
    @Intrinsic
    public void labyMod$remove(@NotNull final String key) {
        this.o(key);
    }
    
    @Intrinsic
    public void labyMod$clear() {
        this.b.clear();
    }
    
    @Nullable
    @Override
    public NBTTag<?> get(@NotNull final String key) {
        return (NBTTag)this.a(key);
    }
    
    @Intrinsic
    public boolean labyMod$contains(@NotNull final String key) {
        return this.c(key);
    }
    
    @Intrinsic
    public boolean labyMod$contains(@NotNull final String key, @NotNull final NBTTagType type) {
        return this.b(key, type.getId());
    }
    
    @Intrinsic
    public void labyMod$setByte(@NotNull final String key, final byte value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setShort(@NotNull final String key, final short value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setInt(@NotNull final String key, final int value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setLong(@NotNull final String key, final long value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setFloat(@NotNull final String key, final float value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setDouble(@NotNull final String key, final double value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setByteArray(@NotNull final String key, final byte[] value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setIntArray(@NotNull final String key, final int[] value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setLongArray(@NotNull final String key, final long[] value) {
        this.a(key, new VersionedNBTTagLongArray(value));
    }
    
    @Intrinsic
    public void labyMod$setString(@NotNull final String key, @NotNull final String value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public void labyMod$setBoolean(@NotNull final String key, final boolean value) {
        this.a(key, value);
    }
    
    @Intrinsic
    public byte labyMod$getByte(@NotNull final String key) {
        return this.d(key);
    }
    
    @Intrinsic
    public short labyMod$getShort(@NotNull final String key) {
        return this.e(key);
    }
    
    @Intrinsic
    public int labyMod$getInt(@NotNull final String key) {
        return this.f(key);
    }
    
    @Intrinsic
    public long labyMod$getLong(@NotNull final String key) {
        return this.g(key);
    }
    
    @Intrinsic
    public float labyMod$getFloat(@NotNull final String key) {
        return this.h(key);
    }
    
    @Intrinsic
    public double labyMod$getDouble(@NotNull final String key) {
        return this.i(key);
    }
    
    @Intrinsic
    public byte[] labyMod$getByteArray(@NotNull final String key) {
        return this.k(key);
    }
    
    @Intrinsic
    public int[] labyMod$getIntArray(@NotNull final String key) {
        return this.l(key);
    }
    
    @Intrinsic
    public long[] labyMod$getLongArray(@NotNull final String key) {
        try {
            return this.b(key, NBTTagType.LONG_ARRAY.getId()) ? this.b.get(key).getLongArray() : new long[0];
        }
        catch (final ClassCastException e) {
            throw new e(this.a(key, NBTTagType.LONG_ARRAY.getId(), e));
        }
    }
    
    @Intrinsic
    public String labyMod$getString(@NotNull final String key) {
        return this.j(key);
    }
    
    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull final String key) {
        return this.n(key);
    }
    
    @Intrinsic
    public NBTTagType labyMod$getType(@NotNull final String key) {
        return NBTTagType.getById(this.b(key));
    }
    
    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull final String key) {
        return (NBTTagCompound)this.m(key);
    }
    
    @Override
    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull final String key, @NotNull final NBTTagType contentType) {
        return (NBTTagList)this.c(key, contentType.getId());
    }
    
    @NotNull
    @Override
    public Set<String> keySet() {
        return this.c();
    }
}
