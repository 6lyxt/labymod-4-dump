// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.nbt.NBTTag;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagCompound;

@Implements({ @Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ us.class })
public abstract class MixinCompoundTag implements NBTTagCompound
{
    @Final
    @Shadow
    private Map<String, vp> x;
    
    @Shadow
    public abstract vp a(final String p0, final vp p1);
    
    @Shadow
    public abstract void r(final String p0);
    
    @Shadow
    public abstract vp shadow$c(final String p0);
    
    @Shadow
    public abstract boolean e(final String p0);
    
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
    public abstract byte f(final String p0);
    
    @Shadow
    public abstract short g(final String p0);
    
    @Shadow
    public abstract int h(final String p0);
    
    @Shadow
    public abstract long i(final String p0);
    
    @Shadow
    public abstract float j(final String p0);
    
    @Shadow
    public abstract double k(final String p0);
    
    @Shadow
    public abstract String l(final String p0);
    
    @Shadow
    public abstract byte[] m(final String p0);
    
    @Shadow
    public abstract int[] n(final String p0);
    
    @Shadow
    public abstract us shadow$p(final String p0);
    
    @Shadow
    public abstract uy c(final String p0, final int p1);
    
    @Shadow
    public abstract boolean q(final String p0);
    
    @Shadow
    public abstract Set<String> e();
    
    @Shadow
    public abstract byte d(final String p0);
    
    @Shadow
    public abstract void a(final String p0, final long[] p1);
    
    @Shadow
    public abstract long[] o(final String p0);
    
    @Nullable
    @Override
    public Map<String, NBTTag<?>> value() {
        return (Map<String, NBTTag<?>>)this.x;
    }
    
    @Intrinsic
    public int labyMod$size() {
        return this.x.size();
    }
    
    @Intrinsic
    public void labyMod$set(@NotNull final String key, @NotNull final NBTTag<?> tag) {
        this.a(key, (vp)tag);
    }
    
    @Intrinsic
    public void labyMod$remove(@NotNull final String key) {
        this.r(key);
    }
    
    @Intrinsic
    public void labyMod$clear() {
        this.x.clear();
    }
    
    @Intrinsic
    @Nullable
    public NBTTag<?> labyMod$get(@NotNull final String key) {
        return (NBTTag)this.shadow$c(key);
    }
    
    @Intrinsic
    public boolean labyMod$contains(@NotNull final String key) {
        return this.e(key);
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
        this.a(key, value);
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
        return this.f(key);
    }
    
    @Intrinsic
    public short labyMod$getShort(@NotNull final String key) {
        return this.g(key);
    }
    
    @Intrinsic
    public int labyMod$getInt(@NotNull final String key) {
        return this.h(key);
    }
    
    @Intrinsic
    public long labyMod$getLong(@NotNull final String key) {
        return this.i(key);
    }
    
    @Intrinsic
    public float labyMod$getFloat(@NotNull final String key) {
        return this.j(key);
    }
    
    @Intrinsic
    public double labyMod$getDouble(@NotNull final String key) {
        return this.k(key);
    }
    
    @Intrinsic
    public byte[] labyMod$getByteArray(@NotNull final String key) {
        return this.m(key);
    }
    
    @Intrinsic
    public int[] labyMod$getIntArray(@NotNull final String key) {
        return this.n(key);
    }
    
    @Intrinsic
    public long[] labyMod$getLongArray(@NotNull final String key) {
        return this.o(key);
    }
    
    @Intrinsic
    public String labyMod$getString(@NotNull final String key) {
        return this.l(key);
    }
    
    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull final String key) {
        return this.q(key);
    }
    
    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull final String key) {
        return (NBTTagCompound)this.shadow$p(key);
    }
    
    @Override
    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull final String key, @NotNull final NBTTagType contentType) {
        return (NBTTagList)this.c(key, contentType.getId());
    }
    
    @Intrinsic
    public NBTTagType labyMod$getType(@NotNull final String key) {
        return NBTTagType.getById(this.d(key));
    }
    
    @NotNull
    @Override
    public Set<String> keySet() {
        return this.e();
    }
}
