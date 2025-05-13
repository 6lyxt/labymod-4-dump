// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.component;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(allowExceptions = { IllegalArgumentException.class })
public class ComponentSerializeEvent implements Event
{
    private final Component component;
    @Nullable
    @ApiStatus.Experimental
    private Component unwrappedComponent;
    private JsonObject json;
    
    public ComponentSerializeEvent(@NotNull final Component component) {
        this.component = component;
    }
    
    @NotNull
    public Component component() {
        return this.component;
    }
    
    @Nullable
    public JsonObject getJson() {
        return this.json;
    }
    
    public boolean wasSerialized() {
        return this.json != null;
    }
    
    public void serialize(@NotNull final Consumer<JsonObject> consumer) {
        if (this.json != null) {
            throw new IllegalStateException("This component has already been serialized");
        }
        consumer.accept(this.json = new JsonObject());
    }
    
    @Nullable
    @ApiStatus.Experimental
    public Component getUnwrappedComponent() {
        return this.unwrappedComponent;
    }
    
    @ApiStatus.Experimental
    public void setUnwrappedComponent(@Nullable final Component unwrappedComponent) {
        this.unwrappedComponent = unwrappedComponent;
    }
}
