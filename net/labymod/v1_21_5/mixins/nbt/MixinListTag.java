// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.CastUtil;
import net.labymod.api.nbt.NBTTag;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagList;

@Implements({ @Interface(iface = NBTTagList.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ug.class })
public abstract class MixinListTag implements NBTTagList
{
    @Final
    @Shadow
    private List<va> v;
    
    @Shadow
    public abstract boolean b(final int p0, final va p1);
    
    @Shadow
    public abstract boolean a(final int p0, final va p1);
    
    @Shadow
    public abstract void d(final int p0, final va p1);
    
    @Shadow
    public abstract va shadow$d(final int p0);
    
    @Nullable
    @Override
    public List value() {
        final List values = new ArrayList(this.v.size());
        for (int i = 0; i < this.v.size(); ++i) {
            final NBTTag tag = CastUtil.cast(this.v.get(i));
            values.add(i, tag.value());
        }
        return values;
    }
    
    @NotNull
    @Override
    public List tags() {
        return this.v;
    }
    
    @Override
    public int size() {
        return this.v.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.v.isEmpty();
    }
    
    @Override
    public Object getValue(final int i) {
        return this.get(i).value();
    }
    
    @Override
    public NBTTag get(final int i) {
        return CastUtil.cast(this.v.get(i));
    }
    
    @Override
    public void add(final NBTTag tag) {
        this.b(this.size(), CastUtil.cast(tag));
    }
    
    @Override
    public void add(final int i, final NBTTag tag) {
        this.d(i, CastUtil.cast(tag));
    }
    
    @Override
    public void set(final int i, final NBTTag tag) {
        this.a(i, CastUtil.cast(tag));
    }
    
    @Override
    public void remove(final int i) {
        final va tag = this.shadow$d(i);
    }
    
    @Override
    public void remove(final NBTTag tag) {
        this.v.remove(CastUtil.cast(tag));
    }
    
    @Override
    public void clear() {
        this.v.clear();
    }
    
    @Nullable
    @Override
    public NBTTagType contentType() {
        return NBTTagType.LIST;
    }
}
