// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.component;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(allowExceptions = { JsonParseException.class })
public class ComponentDeserializeEvent implements Event
{
    private final JsonObject json;
    private Component component;
    
    public ComponentDeserializeEvent(@NotNull final JsonObject json) {
        this.json = json;
    }
    
    @NotNull
    public JsonObject json() {
        return this.json;
    }
    
    @Nullable
    public Component getComponent() {
        return this.component;
    }
    
    public void setComponent(@NotNull final Component component) {
        this.component = component;
    }
    
    public boolean wasDeserialized() {
        return this.component != null;
    }
}
