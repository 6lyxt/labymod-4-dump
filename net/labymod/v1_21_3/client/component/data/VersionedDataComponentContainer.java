// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.component.data;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.ArrayList;
import net.labymod.api.component.data.TypedDataComponent;
import net.labymod.api.component.data.DataComponentPatch;
import java.util.Optional;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Iterator;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.HashSet;
import net.labymod.api.component.data.DataComponentKey;
import java.util.Set;
import java.util.function.Supplier;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.component.data.DataComponentContainer;

public class VersionedDataComponentContainer implements DataComponentContainer
{
    private static final Logging LOGGER;
    private final Supplier<kq> dataComponents;
    
    public VersionedDataComponentContainer(final kq dataComponents) {
        this(() -> dataComponents);
    }
    
    public VersionedDataComponentContainer(final Supplier<kq> dataComponents) {
        this.dataComponents = dataComponents;
    }
    
    @Override
    public Set<DataComponentKey> keySet() {
        final Set<DataComponentKey> keys = new HashSet<DataComponentKey>();
        final kq wrapped = this.getWrapped();
        for (final kt<?> dataComponentType : wrapped.b()) {
            final alz key = this.findId(dataComponentType);
            if (key == null) {
                VersionedDataComponentContainer.LOGGER.debug("Key {} is null", dataComponentType);
            }
            else {
                keys.add(DataComponentKey.fromId((ResourceLocation)key));
            }
        }
        return keys;
    }
    
    @Override
    public boolean has(final DataComponentKey key) {
        return this.get(key) != null;
    }
    
    @Override
    public <T> T get(final DataComponentKey key) {
        final alz resourceLocation = alz.c(key.name());
        if (resourceLocation == null) {
            return null;
        }
        final kt<?> dataComponentType = this.findType(resourceLocation);
        if (dataComponentType == null) {
            return null;
        }
        final kq wrapped = this.getWrapped();
        final kw<?> typedDataComponent = (kw<?>)wrapped.c((kt)dataComponentType);
        if (typedDataComponent == null) {
            return null;
        }
        final DataResult<vu> result = (DataResult<vu>)typedDataComponent.a((DynamicOps)vl.a);
        return result.result().orElse(null);
    }
    
    @Override
    public <T> T getOrDefault(final DataComponentKey key, final T defaultValue) {
        final T value = this.get(key);
        return (value == null) ? null : defaultValue;
    }
    
    @Override
    public <T> void set(final DataComponentKey key, final T value) {
        final kq wrapped = this.getWrapped();
        if (!(wrapped instanceof kv)) {
            VersionedDataComponentContainer.LOGGER.warn("Key {} is not a patched data component map", key);
            return;
        }
        final kv patchedDataComponentMap = (kv)wrapped;
        final alz resourceLocation = alz.c(key.name());
        if (resourceLocation == null) {
            return;
        }
        final kt type = this.findType(resourceLocation);
        if (type == null) {
            return;
        }
        final DataResult parsedResult = type.c().decode((DynamicOps)vl.a, (Object)value);
        final Optional result = parsedResult.result();
        if (result.isEmpty()) {
            return;
        }
        final Object object = result.get();
        if (object instanceof final Pair pair) {
            patchedDataComponentMap.b(type, pair.getFirst());
        }
    }
    
    @Override
    public void apply(final DataComponentPatch patch) {
        for (final TypedDataComponent<?> component : patch.components()) {
            this.set(component.key(), component.value());
        }
    }
    
    @NotNull
    @Override
    public Iterator<TypedDataComponent<?>> iterator() {
        final List<TypedDataComponent<?>> list = new ArrayList<TypedDataComponent<?>>();
        final kq wrapped = this.getWrapped();
        for (final kw<?> dataComponent : wrapped) {
            final alz id = this.findId((kt<?>)dataComponent.a());
            if (id == null) {
                VersionedDataComponentContainer.LOGGER.debug("Key {} is null", dataComponent);
            }
            else {
                list.add(new TypedDataComponent<Object>(DataComponentKey.fromId((ResourceLocation)id), dataComponent.b()));
            }
        }
        return list.iterator();
    }
    
    public kq getWrapped() {
        return this.dataComponents.get();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof final VersionedDataComponentContainer versionedDataComponentContainer) {
            final kq current = this.getWrapped();
            final kq other = versionedDataComponentContainer.getWrapped();
            return Objects.equals(current, other);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getWrapped().hashCode();
    }
    
    @Nullable
    private alz findId(final kt<?> type) {
        return ma.ao.b((Object)type);
    }
    
    @Nullable
    private kt<?> findType(final alz location) {
        return (kt<?>)ma.ao.a(location);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
