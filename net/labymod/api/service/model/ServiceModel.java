// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service.model;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.models.version.Version;
import java.util.HashMap;
import java.util.Map;

public class ServiceModel
{
    private String serviceClass;
    private double classVersion;
    private final Map<String, Object> meta;
    
    public ServiceModel() {
        this.meta = new HashMap<String, Object>();
    }
    
    public String getServiceClass() {
        return this.serviceClass;
    }
    
    public double getClassVersion() {
        return this.classVersion;
    }
    
    public Map<String, Object> getMeta() {
        return this.meta;
    }
    
    @Nullable
    public Version getVersion() {
        final Object minecraftVersion = this.meta.get("gameVersion");
        if (!(minecraftVersion instanceof String)) {
            return null;
        }
        return VersionDeserializer.from((String)minecraftVersion);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceModel{");
        sb.append("serviceClass='").append(this.serviceClass).append('\'');
        sb.append(", classVersion=").append(this.classVersion);
        sb.append(", meta=").append(this.meta);
        sb.append('}');
        return sb.toString();
    }
}
