// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.nbt.NBTTag;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagList;

@Mixin({ du.class })
public abstract class MixinNBTTagList implements NBTTagList
{
    @Shadow
    @Final
    private static Logger b;
    @Shadow
    private List<eb> c;
    @Shadow
    private byte d;
    
    @Shadow
    public abstract void a(final eb p0);
    
    @Shadow
    public abstract void a(final int p0, final eb p1);
    
    @Shadow
    public abstract eb a(final int p0);
    
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
        this.a((eb)tag);
    }
    
    @Override
    public void add(final int i, final NBTTag tag) {
        final eb base = (eb)tag;
        if (base.a() == 0) {
            MixinNBTTagList.b.warn("Invalid TagEnd added to ListTag");
        }
        else if (i >= 0 && i <= this.c.size()) {
            if (this.d == 0) {
                this.d = base.a();
            }
            else if (this.d != base.a()) {
                MixinNBTTagList.b.warn("Adding mismatching tag types to tag list");
                return;
            }
            this.c.add(i, base);
        }
        else {
            MixinNBTTagList.b.warn("index out of bounds to add tag in tag list");
        }
    }
    
    @Override
    public void set(final int i, final NBTTag tag) {
        this.a(i, (eb)tag);
    }
    
    @Override
    public void remove(final NBTTag tag) {
        this.c.remove(tag);
    }
    
    @Override
    public void remove(final int i) {
        this.a(i);
    }
    
    @Override
    public void clear() {
        this.c.clear();
    }
    
    @Nullable
    @Override
    public NBTTagType contentType() {
        return (this.d != 0 || this.isEmpty()) ? NBTTagType.getById(this.d) : null;
    }
}
