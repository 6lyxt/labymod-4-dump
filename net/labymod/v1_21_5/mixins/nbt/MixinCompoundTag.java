// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import java.util.Collection;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.util.CastUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.nbt.NBTTag;
import java.util.Optional;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagCompound;

@Implements({ @Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ua.class })
public abstract class MixinCompoundTag implements NBTTagCompound
{
    @Final
    @Shadow
    private Map<String, va> x;
    
    @Shadow
    public abstract va a(final String p0, final va p1);
    
    @Shadow
    public abstract void r(final String p0);
    
    @Shadow
    public abstract va shadow$a(final String p0);
    
    @Shadow
    public abstract boolean b(final String p0);
    
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
    public abstract void a(final String p0, final long[] p1);
    
    @Shadow
    public abstract ua n(final String p0);
    
    @Shadow
    public abstract ug p(final String p0);
    
    @Shadow
    public abstract Set<String> shadow$e();
    
    @Shadow
    public abstract vc<ua> c();
    
    @Shadow
    public abstract boolean b(final String p0, final boolean p1);
    
    @Shadow
    public abstract String b(final String p0, final String p1);
    
    @Shadow
    public abstract Optional<long[]> shadow$l(final String p0);
    
    @Shadow
    public abstract Optional<int[]> shadow$k(final String p0);
    
    @Shadow
    public abstract Optional<byte[]> shadow$j(final String p0);
    
    @Shadow
    public abstract double b(final String p0, final double p1);
    
    @Shadow
    public abstract byte b(final String p0, final byte p1);
    
    @Shadow
    public abstract short b(final String p0, final short p1);
    
    @Shadow
    public abstract int b(final String p0, final int p1);
    
    @Shadow
    public abstract long b(final String p0, final long p1);
    
    @Shadow
    public abstract float b(final String p0, final float p1);
    
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
        this.a(key, CastUtil.cast(tag));
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
        return CastUtil.cast(this.shadow$a(key));
    }
    
    @Intrinsic
    public boolean labyMod$contains(@NotNull final String key) {
        return this.b(key);
    }
    
    @Intrinsic
    public boolean labyMod$contains(@NotNull final String key, @NotNull final NBTTagType type) {
        return this.b(key);
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
        return this.b(key, (byte)0);
    }
    
    @Intrinsic
    public short labyMod$getShort(@NotNull final String key) {
        return this.b(key, (short)0);
    }
    
    @Intrinsic
    public int labyMod$getInt(@NotNull final String key) {
        return this.b(key, 0);
    }
    
    @Intrinsic
    public long labyMod$getLong(@NotNull final String key) {
        return this.b(key, 0L);
    }
    
    @Intrinsic
    public float labyMod$getFloat(@NotNull final String key) {
        return this.b(key, 0.0f);
    }
    
    @Intrinsic
    public double labyMod$getDouble(@NotNull final String key) {
        return this.b(key, 0.0);
    }
    
    @Intrinsic
    public byte[] labyMod$getByteArray(@NotNull final String key) {
        return this.shadow$j(key).orElse(new byte[0]);
    }
    
    @Intrinsic
    public int[] labyMod$getIntArray(@NotNull final String key) {
        return this.shadow$k(key).orElse(new int[0]);
    }
    
    @Intrinsic
    public long[] labyMod$getLongArray(@NotNull final String key) {
        return this.shadow$l(key).orElse(new long[0]);
    }
    
    @Intrinsic
    public String labyMod$getString(@NotNull final String key) {
        return this.b(key, "");
    }
    
    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull final String key) {
        return this.b(key, false);
    }
    
    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull final String key) {
        return CastUtil.cast(this.n(key));
    }
    
    @Override
    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull final String key, @NotNull final NBTTagType contentType) {
        return CastUtil.cast((Collection<?>)this.p(key));
    }
    
    @Intrinsic
    public NBTTagType labyMod$getType(@NotNull final String key) {
        return null;
    }
    
    @Intrinsic
    @NotNull
    public Set<String> labyMod$keySet() {
        return this.shadow$e();
    }
}
