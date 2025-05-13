// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.serializer.gson;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.component.ComponentService;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonElement;
import javax.inject.Inject;
import net.labymod.api.Laby;
import com.google.gson.Gson;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class GsonComponentSerializer
{
    private static final Gson GSON;
    
    public static GsonComponentSerializer gson() {
        return Laby.references().gsonComponentSerializer();
    }
    
    @Inject
    public GsonComponentSerializer() {
    }
    
    public Component deserializeFromTree(final JsonElement jsonElement) {
        return (Component)GsonComponentSerializer.GSON.fromJson(jsonElement, (Class)Component.class);
    }
    
    public JsonElement serializeToTree(final Component component) {
        return GsonComponentSerializer.GSON.toJsonTree((Object)component);
    }
    
    public Component deserialize(final String string) {
        return (Component)GsonComponentSerializer.GSON.fromJson(string, (Class)Component.class);
    }
    
    public String serialize(final Component component) {
        return GsonComponentSerializer.GSON.toJson((Object)component);
    }
    
    static {
        final GsonBuilder builder = new GsonBuilder().registerTypeHierarchyAdapter((Class)Component.class, (Object)ComponentService.getComponentSerializer());
        if (!MinecraftVersions.V23w40a.orNewer()) {
            builder.registerTypeHierarchyAdapter((Class)Style.class, (Object)ComponentService.getStyleSerializer());
        }
        ComponentService.applyTypeAdapters(builder);
        GSON = builder.create();
    }
}
