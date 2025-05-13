// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata;

public class MetadataProperty<T>
{
    private final String key;
    private final T defaultValue;
    private final ItemMetadata metadata;
    private T value;
    
    public MetadataProperty(final String key, final ItemMetadata metadata) {
        this(key, metadata, null);
    }
    
    public MetadataProperty(final String key, final ItemMetadata metadata, final T value) {
        this.key = key;
        this.metadata = metadata;
        this.value = value;
        this.defaultValue = value;
    }
    
    public T get() {
        if (this.value == null) {
            this.value = this.metadata.readValue(this.key);
        }
        return this.value;
    }
    
    public void reset(final String key) {
        this.value = this.defaultValue;
    }
}
