// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata;

import java.util.Objects;
import net.labymod.core.main.debug.InvalidCosmeticDataDebugger;
import net.labymod.core.main.user.shop.item.ItemDetails;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractMetadata<T>
{
    private final String[] keys;
    protected Supplier<T> defaultValueSupplier;
    protected Consumer<String> metadataInvalidator;
    protected T value;
    protected MetadataWatchable watchable;
    private String currentKey;
    
    public AbstractMetadata(final String... keys) {
        this.defaultValueSupplier = (Supplier<T>)(() -> null);
        this.metadataInvalidator = (key -> {});
        this.keys = keys;
    }
    
    public AbstractMetadata<T> withWatchable(@Nullable final MetadataWatchable watchable) {
        this.watchable = watchable;
        return this;
    }
    
    public AbstractMetadata<T> withMetadataInvalidator(@Nullable final Consumer<String> metadataInvalidator) {
        this.metadataInvalidator = metadataInvalidator;
        return this;
    }
    
    public AbstractMetadata<T> defaultValue(final Supplier<T> supplier) {
        if (supplier == null) {
            return this;
        }
        this.defaultValueSupplier = supplier;
        this.value = supplier.get();
        return this;
    }
    
    public final void read(final ItemDetails details, final String key, final String value) {
        try {
            this.currentKey = key;
            if (this.metadataInvalidator != null) {
                this.metadataInvalidator.accept(key);
            }
            this.read(value);
            if (this.watchable != null) {
                this.watchable.onUpdate();
            }
        }
        catch (final MetadataException exception) {
            InvalidCosmeticDataDebugger.log("({}) Key: {}, Message: {}", details.getName() + "/" + details.getIdentifier(), key, exception.getMessage());
        }
        finally {
            this.currentKey = null;
        }
    }
    
    public abstract void read(final String p0) throws MetadataException;
    
    public abstract Object write() throws MetadataException;
    
    public void invalidate() {
        this.value = this.defaultValueSupplier.get();
    }
    
    public T getValue() {
        return this.value;
    }
    
    public boolean hasKey(final String name) {
        for (final String dataName : this.keys) {
            if (dataName.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean is(final String key) {
        return Objects.equals(this.currentKey, key);
    }
    
    public String[] getKeys() {
        return this.keys;
    }
}
