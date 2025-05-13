// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.config;

import com.google.gson.GsonBuilder;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true)
public class JsonConfigLoaderInitializeEvent implements Event
{
    private final GsonBuilder gsonBuilder;
    
    public JsonConfigLoaderInitializeEvent(final GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
    }
    
    public GsonBuilder getGsonBuilder() {
        return this.gsonBuilder;
    }
}
