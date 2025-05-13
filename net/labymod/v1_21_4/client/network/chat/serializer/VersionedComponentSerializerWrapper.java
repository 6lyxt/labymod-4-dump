// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.network.chat.serializer;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import net.labymod.v1_21_4.client.network.chat.MutableComponentAccessor;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonSerializer;
import net.labymod.v1_21_4.client.network.chat.VersionedBaseComponent;
import com.google.gson.JsonDeserializer;
import net.labymod.api.client.component.serializer.Serializer;

public class VersionedComponentSerializerWrapper implements Serializer, JsonDeserializer<VersionedBaseComponent<?, ?>>, JsonSerializer<Component>
{
    private final wp.b serializerAdapter;
    
    public VersionedComponentSerializerWrapper() {
        this.serializerAdapter = new wp.b((jt.a)kf.b);
    }
    
    public VersionedBaseComponent<?, ?> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final MutableComponentAccessor deserialized = (MutableComponentAccessor)this.serializerAdapter.a(json, typeOfT, context);
        return deserialized.getLabyComponent();
    }
    
    public JsonElement serialize(final Component src, final Type typeOfSrc, final JsonSerializationContext context) {
        final xd holder = ((VersionedBaseComponent)src).getHolder();
        return this.serializerAdapter.a((wp)holder, typeOfSrc, context);
    }
}
