// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

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

@Mixin({ hh.a.class })
public abstract class MixinIChatComponentSerializer implements Serializer
{
    @Shadow
    protected abstract void a(final hn p0, final JsonObject p1, final JsonSerializationContext p2);
    
    @Overwrite
    public JsonElement a(hh value, final Type type, final JsonSerializationContext context) {
        final ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component)value);
        final Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (hh)unwrappedComponent;
        }
        if (!event.wasSerialized() && value instanceof ho && value.b().g() && value.a().isEmpty()) {
            return (JsonElement)new JsonPrimitive(value.e());
        }
        final JsonObject json = (event.getJson() != null) ? event.getJson() : new JsonObject();
        if (!value.b().g()) {
            this.a(value.b(), json, context);
        }
        if (!value.a().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (final hh sibling : value.a()) {
                extra.add(this.a(sibling, sibling.getClass(), context));
            }
            json.add("extra", (JsonElement)extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof ho) {
                json.addProperty("text", value.e());
            }
            else if (value instanceof final hp translateComponent) {
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j() != null && translateComponent.j().length > 0) {
                    final JsonArray args = new JsonArray();
                    for (final Object arg : translateComponent.j()) {
                        if (arg instanceof final hh hh) {
                            args.add(this.a(hh, arg.getClass(), context));
                        }
                        else {
                            args.add((JsonElement)new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", (JsonElement)args);
                }
            }
            else if (value instanceof final hl scoreComponent) {
                final JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.g());
                score.addProperty("objective", scoreComponent.h());
                score.addProperty("value", scoreComponent.e());
                json.add("score", (JsonElement)score);
            }
            else {
                if (!(value instanceof hm)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                }
                final hm selectorComponent = (hm)value;
                json.addProperty("selector", selectorComponent.g());
            }
        }
        return (JsonElement)json;
    }
    
    @Overwrite
    public hh a(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return (hh)new ho(json.getAsString());
        }
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            final ComponentDeserializeEvent event = Laby.fireEvent(new ComponentDeserializeEvent(object));
            hh component;
            if (event.getComponent() != null) {
                component = (hh)event.getComponent();
            }
            else if (object.has("text")) {
                component = (hh)new ho(object.get("text").getAsString());
            }
            else if (object.has("translate")) {
                final String translationKey = object.get("translate").getAsString();
                if (object.has("with")) {
                    final JsonArray jsonArgs = object.getAsJsonArray("with");
                    final Object[] args = new Object[jsonArgs.size()];
                    for (int i = 0; i < args.length; ++i) {
                        args[i] = this.a(jsonArgs.get(i), type, context);
                        if (args[i] instanceof final ho textArg) {
                            if (textArg.b().g() && textArg.a().isEmpty()) {
                                args[i] = textArg.e();
                            }
                        }
                    }
                    component = (hh)new hp(translationKey, args);
                }
                else {
                    component = (hh)new hp(translationKey, new Object[0]);
                }
            }
            else if (object.has("score")) {
                final JsonObject score = object.getAsJsonObject("score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                component = (hh)new hl(rc.h(score, "name"), rc.h(score, "objective"));
                if (score.has("value")) {
                    ((hl)component).b(rc.h(score, "value"));
                }
            }
            else {
                if (!object.has("selector")) {
                    throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                }
                component = (hh)new hm(rc.h(object, "selector"));
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
            component.a((hn)context.deserialize(json, (Type)hn.class));
            return component;
        }
        if (json.isJsonArray()) {
            hh component2 = null;
            for (final JsonElement jsonElement : json.getAsJsonArray()) {
                final hh element2 = this.a(jsonElement, jsonElement.getClass(), context);
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
