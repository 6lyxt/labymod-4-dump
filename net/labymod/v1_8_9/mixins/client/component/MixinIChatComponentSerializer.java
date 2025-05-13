// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.component;

import com.google.gson.JsonParseException;
import net.labymod.api.Laby;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import com.google.gson.JsonDeserializationContext;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.Iterator;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import net.labymod.core.event.client.component.ComponentSerializeEventCaller;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import org.spongepowered.asm.mixin.Shadow;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.serializer.Serializer;

@Mixin({ eu.a.class })
public abstract class MixinIChatComponentSerializer implements Serializer
{
    @Shadow
    protected abstract void a(final ez p0, final JsonObject p1, final JsonSerializationContext p2);
    
    @Overwrite
    public JsonElement a(eu value, final Type type, final JsonSerializationContext context) {
        final ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component)value);
        final Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (eu)unwrappedComponent;
        }
        if (!event.wasSerialized() && value instanceof fa && value.b().g() && value.a().isEmpty()) {
            return (JsonElement)new JsonPrimitive(((fa)value).g());
        }
        final JsonObject json = (event.getJson() != null) ? event.getJson() : new JsonObject();
        if (!value.b().g()) {
            this.a(value.b(), json, context);
        }
        if (!value.a().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (final eu sibling : value.a()) {
                extra.add(this.a(sibling, sibling.getClass(), context));
            }
            json.add("extra", (JsonElement)extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof final fa fa) {
                json.addProperty("text", fa.g());
            }
            else if (value instanceof final fb translateComponent) {
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j() != null && translateComponent.j().length > 0) {
                    final JsonArray args = new JsonArray();
                    for (final Object arg : translateComponent.j()) {
                        if (arg instanceof final eu eu) {
                            args.add(this.a(eu, arg.getClass(), context));
                        }
                        else {
                            args.add((JsonElement)new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", (JsonElement)args);
                }
            }
            else if (value instanceof final ex scoreComponent) {
                final JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.g());
                score.addProperty("objective", scoreComponent.h());
                score.addProperty("value", scoreComponent.e());
                json.add("score", (JsonElement)score);
            }
            else {
                if (!(value instanceof ey)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                }
                final ey selectorComponent = (ey)value;
                json.addProperty("selector", selectorComponent.g());
            }
        }
        return (JsonElement)json;
    }
    
    @Overwrite
    public eu a(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return (eu)new fa(json.getAsString());
        }
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            final ComponentDeserializeEvent event = Laby.fireEvent(new ComponentDeserializeEvent(object));
            eu component;
            if (event.getComponent() != null) {
                component = (eu)event.getComponent();
            }
            else if (object.has("text")) {
                component = (eu)new fa(object.get("text").getAsString());
            }
            else if (object.has("translate")) {
                final String translationKey = object.get("translate").getAsString();
                if (object.has("with")) {
                    final JsonArray jsonArgs = object.getAsJsonArray("with");
                    final Object[] args = new Object[jsonArgs.size()];
                    for (int i = 0; i < args.length; ++i) {
                        args[i] = this.a(jsonArgs.get(i), type, context);
                        if (args[i] instanceof final fa textArg) {
                            if (textArg.b().g() && textArg.a().isEmpty()) {
                                args[i] = textArg.g();
                            }
                        }
                    }
                    component = (eu)new fb(translationKey, args);
                }
                else {
                    component = (eu)new fb(translationKey, new Object[0]);
                }
            }
            else if (object.has("score")) {
                final JsonObject score = object.getAsJsonObject("score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                component = (eu)new ex(ni.h(score, "name"), ni.h(score, "objective"));
                if (score.has("value")) {
                    ((ex)component).b(ni.h(score, "value"));
                }
            }
            else {
                if (!object.has("selector")) {
                    throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                }
                component = (eu)new ey(ni.h(object, "selector"));
            }
            if (object.has("extra")) {
                final JsonArray extra = object.getAsJsonArray("extra");
                if (extra.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (final JsonElement element : extra) {
                    component.a(this.a(element, type, context));
                }
            }
            component.a((ez)context.deserialize(json, (Type)ez.class));
            return component;
        }
        if (json.isJsonArray()) {
            eu component2 = null;
            for (final JsonElement jsonElement : json.getAsJsonArray()) {
                final eu element2 = this.a(jsonElement, jsonElement.getClass(), context);
                if (component2 == null) {
                    component2 = element2;
                }
                else {
                    component2.a(element2);
                }
            }
            return component2;
        }
        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
    }
}
