// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import com.google.gson.JsonObject;
import net.labymod.api.event.Event;

public class ConfigurationLoadEvent implements Event
{
    private final Class<?> clazz;
    private final JsonObject jsonObject;
    
    public ConfigurationLoadEvent(final Class<?> clazz, final JsonObject jsonObject) {
        this.clazz = clazz;
        this.jsonObject = jsonObject;
    }
    
    public Class<?> clazz() {
        return this.clazz;
    }
    
    public JsonObject jsonObject() {
        return this.jsonObject;
    }
}
