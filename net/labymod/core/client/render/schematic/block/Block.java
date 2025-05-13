// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block;

import java.util.Iterator;
import java.util.Locale;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import java.util.HashMap;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.render.schematic.block.material.material.Material;

public class Block
{
    @NotNull
    private final Material material;
    private final Map<String, Object> parameters;
    private final String namespace;
    private final String rawId;
    private final boolean unknown;
    private LightSource lightSource;
    
    public Block(final String namespace, final String rawId) {
        this.parameters = new HashMap<String, Object>();
        this.namespace = namespace;
        this.rawId = rawId;
        String id = rawId;
        if (id.contains("[")) {
            final String[] split = id.split("\\[");
            id = split[0];
            final String parameters = split[1].replace("]", "");
            for (final String parameter : parameters.split(",")) {
                final String[] pair = parameter.split("=");
                final String key = pair[0];
                Object value = pair[1];
                try {
                    value = Integer.parseInt(pair[1]);
                }
                catch (final NumberFormatException ex) {}
                if (pair[1].equals("true") || pair[1].equals("false")) {
                    value = Boolean.parseBoolean(pair[1]);
                }
                this.parameters.put(key, value);
            }
        }
        final Material material = MaterialRegistry.getById(id);
        if (material == null) {
            this.material = MaterialRegistry.AIR;
            this.unknown = true;
        }
        else {
            this.material = material;
            this.unknown = false;
        }
    }
    
    public Block(final String namespace, final Material material) {
        this.parameters = new HashMap<String, Object>();
        this.namespace = namespace;
        this.rawId = material.getId();
        this.material = material;
        this.unknown = false;
    }
    
    public boolean hasParameter(final String type) {
        return this.parameters.containsKey(type);
    }
    
    public <T> T getParameter(final String type) {
        return (T)this.parameters.get(type);
    }
    
    public <T> T getParameter(final String type, final T defaultValue) {
        return (T)(this.hasParameter(type) ? this.parameters.get(type) : defaultValue);
    }
    
    public LightSource getLightSource() {
        return this.lightSource;
    }
    
    public void setLightSource(final LightSource lightSource) {
        this.lightSource = lightSource;
    }
    
    @NotNull
    public Material material() {
        return this.material;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    @Override
    public String toString() {
        if (this.unknown) {
            return String.format(Locale.ROOT, "%s:%s", this.namespace, this.rawId);
        }
        StringBuilder id = new StringBuilder(String.format(Locale.ROOT, "%s:%s", this.namespace, this.material.getId()));
        if (!this.parameters.isEmpty()) {
            id.append("[");
            for (final Map.Entry<String, Object> entry : this.parameters.entrySet()) {
                id.append(String.format(Locale.ROOT, "%s=%s,", entry.getKey(), entry.getValue()));
            }
            id = new StringBuilder(id.substring());
            id.append("]");
        }
        return id.toString();
    }
}
