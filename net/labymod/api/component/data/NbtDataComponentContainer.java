// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.component.data;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.ArrayList;
import net.labymod.api.nbt.NBTTag;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.nbt.tags.NBTTagCompound;
import java.util.function.Supplier;

public class NbtDataComponentContainer implements DataComponentContainer
{
    private final Supplier<NBTTagCompound> compound;
    
    public NbtDataComponentContainer(final NBTTagCompound compound) {
        this(() -> compound);
    }
    
    public NbtDataComponentContainer(final Supplier<NBTTagCompound> compound) {
        this.compound = compound;
    }
    
    @Override
    public Set<DataComponentKey> keySet() {
        final Set<DataComponentKey> componentTypes = new HashSet<DataComponentKey>();
        final NBTTagCompound wrapped = this.getWrapped();
        for (final String name : wrapped.keySet()) {
            final DataComponentKey objectDataComponentKey = DataComponentKey.simple(name);
            componentTypes.add(objectDataComponentKey);
        }
        return componentTypes;
    }
    
    @Override
    public boolean has(final DataComponentKey key) {
        return this.get(key) != null;
    }
    
    @Override
    public <T> T get(final DataComponentKey key) {
        return (T)this.getWrapped().get(key.name());
    }
    
    @Override
    public <T> T getOrDefault(final DataComponentKey key, final T defaultValue) {
        final T value = this.get(key);
        return (value == null) ? defaultValue : value;
    }
    
    @Override
    public <T> void set(final DataComponentKey key, final T value) {
        if (value instanceof NBTTag) {
            final NBTTag<?> nbtTag = (NBTTag<?>)value;
            this.getWrapped().set(key.name(), nbtTag);
            return;
        }
        throw new IllegalArgumentException("Value is not a NBTTag");
    }
    
    @Override
    public void apply(final DataComponentPatch patch) {
        final NBTTagCompound wrapped = this.getWrapped();
        wrapped.clear();
        for (final TypedDataComponent<?> component : patch.components()) {
            final DataComponentKey key = component.key();
            final Object value = component.value();
            if (value == null) {
                continue;
            }
            this.set(key, value);
        }
    }
    
    @NotNull
    @Override
    public Iterator<TypedDataComponent<?>> iterator() {
        final List<TypedDataComponent<?>> components = new ArrayList<TypedDataComponent<?>>();
        final NBTTagCompound compound = this.getWrapped();
        for (final String name : compound.keySet()) {
            final NBTTag<?> nbtTag = compound.get(name);
            if (nbtTag == null) {
                continue;
            }
            components.add(new TypedDataComponent<Object>(DataComponentKey.simple(name), nbtTag));
        }
        return components.iterator();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof final NbtDataComponentContainer nbtDataComponentContainer) {
            final NBTTagCompound current = this.getWrapped();
            final NBTTagCompound other = nbtDataComponentContainer.getWrapped();
            return Objects.equals(current, other);
        }
        return false;
    }
    
    @Override
    public String toString() {
        final NBTTagCompound wrapped = this.getWrapped();
        return (wrapped == null) ? null : wrapped.toString();
    }
    
    @Override
    public int hashCode() {
        return this.getWrapped().hashCode();
    }
    
    public NBTTagCompound getWrapped() {
        return this.compound.get();
    }
}
