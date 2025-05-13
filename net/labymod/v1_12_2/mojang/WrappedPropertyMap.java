// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mojang;

import java.util.ArrayList;
import com.google.common.collect.Multimap;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import net.labymod.api.mojang.Property;
import java.util.Collection;
import java.util.Map;
import com.mojang.authlib.properties.PropertyMap;

public class WrappedPropertyMap extends PropertyMap
{
    private final Map<String, Collection<Property>> properties;
    
    public WrappedPropertyMap() {
        this(new HashMap<String, Collection<Property>>());
    }
    
    public WrappedPropertyMap(final Map<String, Collection<Property>> properties) {
        this.properties = properties;
    }
    
    public boolean put(@NotNull final String key, @NotNull final com.mojang.authlib.properties.Property value) {
        final boolean putSuccess = super.put((Object)key, (Object)value);
        final Collection<Property> properties = this.getProperties(key);
        properties.add(this.createProperty(value));
        return putSuccess;
    }
    
    public boolean putAll(@NotNull final String key, @NotNull final Iterable<? extends com.mojang.authlib.properties.Property> values) {
        final boolean putAllSuccess = super.putAll((Object)key, (Iterable)values);
        final Collection<Property> properties = this.getProperties(key);
        for (final com.mojang.authlib.properties.Property property : values) {
            properties.add(this.createProperty(property));
        }
        return putAllSuccess;
    }
    
    public boolean putAll(@NotNull final Multimap<? extends String, ? extends com.mojang.authlib.properties.Property> multimap) {
        final boolean putAllSuccess = super.putAll((Multimap)multimap);
        multimap.forEach((key, property) -> {
            final Collection<Property> properties = this.getProperties(key);
            properties.add(this.createProperty(property));
            return;
        });
        return putAllSuccess;
    }
    
    public void clear() {
        super.clear();
        this.properties.clear();
    }
    
    public Map<String, Collection<Property>> getProperties() {
        return this.properties;
    }
    
    private Collection<Property> getProperties(final String key) {
        return this.properties.computeIfAbsent(key, k -> new ArrayList());
    }
    
    private Property createProperty(final com.mojang.authlib.properties.Property property) {
        return new Property(property.getName(), property.getValue(), property.getSignature());
    }
}
