// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.template;

import java.util.Collections;
import java.util.HashMap;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.Map;

public class SignObjectMeta<T extends SignObjectTemplate>
{
    private final T template;
    private final String[] meta;
    private transient Map<String, String> parsedMeta;
    
    public SignObjectMeta(final T template, final String[] meta) {
        this.template = template;
        this.meta = meta;
    }
    
    public T template() {
        return this.template;
    }
    
    public String[] meta() {
        return this.meta;
    }
    
    public String meta(final String key) {
        return this.parseMeta().get(key);
    }
    
    public String meta(final String key, final String defaultValue) {
        return this.parseMeta().getOrDefault(key, defaultValue);
    }
    
    public <E extends Enum<?>> E meta(final String key, final Class<E> enumClass) {
        final String value = this.meta(key);
        if (value == null) {
            return null;
        }
        for (final E constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        return null;
    }
    
    public <E extends Enum<?>> E meta(final String key, final Class<E> enumClass, final E defaultValue) {
        final E value = this.meta(key, enumClass);
        return (value != null) ? value : defaultValue;
    }
    
    public FloatVector3 vector(final String key) {
        final String value = this.meta(key);
        if (value == null) {
            return null;
        }
        final String[] components = value.split(",");
        if (components.length != 3) {
            return null;
        }
        try {
            return new FloatVector3(Float.parseFloat(components[0]), Float.parseFloat(components[1]), Float.parseFloat(components[2]));
        }
        catch (final NumberFormatException ignored) {
            return null;
        }
    }
    
    public FloatVector3 vector(final String key, final FloatVector3 defaultValue) {
        final FloatVector3 value = this.vector(key);
        return (value != null) ? value : defaultValue;
    }
    
    public Map<String, String> parseMeta() {
        if (this.parsedMeta != null) {
            return this.parsedMeta;
        }
        if (this.meta.length == 0) {
            return Map.of();
        }
        final Map<String, String> meta = new HashMap<String, String>();
        for (final String line : this.meta) {
            final String[] components = line.split(":", 2);
            if (components.length == 2) {
                meta.put(components[0], components[1]);
            }
        }
        return this.parsedMeta = Collections.unmodifiableMap((Map<? extends String, ? extends String>)meta);
    }
}
