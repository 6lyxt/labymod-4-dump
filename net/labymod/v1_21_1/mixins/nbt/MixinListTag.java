// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.nbt;

import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
@Mixin({ uh.class })
public abstract class MixinListTag implements NBTTagList
{
    @Final
    @Shadow
    private List<uy> c;
    @Shadow
    private byte w;
    
    @Shadow
    public abstract boolean b(final int p0, final uy p1);
    
    @Shadow
    public abstract boolean a(final int p0, final uy p1);
    
    @Shadow
    public abstract void c(final int p0, final uy p1);
    
    @Shadow
    public abstract uy shadow$c(final int p0);
    
    @Nullable
    @Override
    public List value() {
        final List values = new ArrayList(this.c.size());
        for (int i = 0; i < this.c.size(); ++i) {
            values.add(i, ((NBTTag)this.c.get(i)).value());
        }
        return values;
    }
    
    @NotNull
    @Override
    public List tags() {
        return this.c;
    }
    
    @Override
    public int size() {
        return this.c.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.c.isEmpty();
    }
    
    @Override
    public Object getValue(final int i) {
        return this.get(i).value();
    }
    
    @Override
    public NBTTag get(final int i) {
        return (NBTTag)this.c.get(i);
    }
    
    @Override
    public void add(final NBTTag tag) {
        this.b(this.size(), (uy)tag);
    }
    
    @Override
    public void add(final int i, final NBTTag tag) {
        this.c(i, (uy)tag);
    }
    
    @Override
    public void set(final int i, final NBTTag tag) {
        this.a(i, (uy)tag);
    }
    
    @Override
    public void remove(final int i) {
        final uy tag = this.shadow$c(i);
    }
    
    @Override
    public void remove(final NBTTag tag) {
        this.c.remove(tag);
    }
    
    @Override
    public void clear() {
        this.c.clear();
    }
    
    @Nullable
    @Override
    public NBTTagType contentType() {
        return (this.w != 0 || this.isEmpty()) ? NBTTagType.getById(this.w) : null;
    }
}
