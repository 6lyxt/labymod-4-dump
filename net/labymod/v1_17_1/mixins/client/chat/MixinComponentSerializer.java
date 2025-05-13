// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.chat;

import com.google.gson.JsonParseException;
import net.labymod.api.Laby;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.Iterator;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import com.google.gson.JsonPrimitive;
import net.labymod.api.client.component.TextComponent;
import com.google.gson.JsonArray;
import net.labymod.core.event.client.component.ComponentSerializeEventCaller;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import java.util.Optional;
import org.spongepowered.asm.mixin.Shadow;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.serializer.Serializer;

@Mixin({ os.a.class })
public abstract class MixinComponentSerializer implements Serializer
{
    @Shadow
    protected abstract void a(final pc p0, final JsonObject p1, final JsonSerializationContext p2);
    
    @Shadow
    protected abstract void a(final JsonSerializationContext p0, final JsonObject p1, final Optional<os> p2);
    
    @Shadow
    protected abstract Optional<os> a(final Type p0, final JsonDeserializationContext p1, final JsonObject p2);
    
    @Overwrite
    public JsonElement a(os value, final Type type, final JsonSerializationContext context) {
        final ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component)value);
        final Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (os)unwrappedComponent;
        }
        final JsonObject json = (event.getJson() != null) ? event.getJson() : new JsonObject();
        if (!value.c().g()) {
            this.a(value.c(), json, context);
        }
        if (!value.b().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (final os sibling : value.b()) {
                extra.add(this.a(sibling, sibling.getClass(), context));
            }
            json.add("extra", (JsonElement)extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof final TextComponent textComponent) {
                json.addProperty("text", textComponent.getText());
            }
            else if (value instanceof final pg translateComponent) {
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j().length > 0) {
                    final JsonArray args = new JsonArray();
                    for (final Object arg : translateComponent.j()) {
                        if (arg instanceof final os os) {
                            args.add(this.a(os, arg.getClass(), context));
                        }
                        else {
                            args.add((JsonElement)new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", (JsonElement)args);
                }
            }
            else if (value instanceof final pa scoreComponent) {
                final JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.h());
                score.addProperty("objective", scoreComponent.j());
                json.add("score", (JsonElement)score);
            }
            else if (value instanceof final pb selectorComponent) {
                json.addProperty("selector", selectorComponent.h());
                this.a(context, json, selectorComponent.j());
            }
            else if (value instanceof final ox keybindComponent) {
                json.addProperty("keybind", keybindComponent.i());
            }
            else {
                if (!(value instanceof oz)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                }
                final oz nbtComponent = (oz)value;
                json.addProperty("nbt", nbtComponent.h());
                json.addProperty("interpret", Boolean.valueOf(nbtComponent.i()));
                this.a(context, json, nbtComponent.e);
                if (value instanceof final oz.a blockNbtComponent) {
                    json.addProperty("block", blockNbtComponent.j());
                }
                else if (value instanceof final oz.b entityNbtComponent) {
                    json.addProperty("entity", entityNbtComponent.j());
                }
                else {
                    if (!(value instanceof oz.c)) {
                        throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                    }
                    final oz.c storageNbtComponent = (oz.c)value;
                    json.addProperty("storage", storageNbtComponent.j().toString());
                }
            }
        }
        return (JsonElement)json;
    }
    
    @Overwrite
    public oy a(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return (oy)new pf(json.getAsString());
        }
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            final ComponentDeserializeEvent event = Laby.fireEvent(new ComponentDeserializeEvent(object));
            oy component;
            if (event.getComponent() != null) {
                component = (oy)event.getComponent();
            }
            else if (object.has("text")) {
                component = (oy)new pf(agv.h(object, "text"));
            }
            else if (object.has("translate")) {
                final String translationKey = agv.h(object, "translate");
                if (object.has("with")) {
                    final JsonArray jsonArgs = agv.u(object, "with");
                    final Object[] args = new Object[jsonArgs.size()];
                    for (int i = 0; i < args.length; ++i) {
                        args[i] = this.a(jsonArgs.get(i), type, context);
                        if (args[i] instanceof final pf textArg) {
                            if (textArg.c().g() && textArg.b().isEmpty()) {
                                args[i] = textArg.h();
                            }
                        }
                    }
                    component = (oy)new pg(translationKey, args);
                }
                else {
                    component = (oy)new pg(translationKey);
                }
            }
            else if (object.has("score")) {
                final JsonObject score = agv.t(object, "score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                component = (oy)new pa(agv.h(score, "name"), agv.h(score, "objective"));
            }
            else if (object.has("selector")) {
                final Optional<os> separator = this.a(type, context, object);
                component = (oy)new pb(agv.h(object, "selector"), (Optional)separator);
            }
            else if (object.has("keybind")) {
                component = (oy)new ox(agv.h(object, "keybind"));
            }
            else {
                if (!object.has("nbt")) {
                    throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                }
                final String nbtPath = agv.h(object, "nbt");
                final Optional<os> separator2 = this.a(type, context, object);
                final boolean interpreting = agv.a(object, "interpret", false);
                if (object.has("block")) {
                    component = (oy)new oz.a(nbtPath, interpreting, agv.h(object, "block"), (Optional)separator2);
                }
                else if (object.has("entity")) {
                    component = (oy)new oz.b(nbtPath, interpreting, agv.h(object, "entity"), (Optional)separator2);
                }
                else {
                    if (!object.has("storage")) {
                        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                    }
                    component = (oy)new oz.c(nbtPath, interpreting, new ww(agv.h(object, "storage")), (Optional)separator2);
                }
            }
            if (object.has("extra")) {
                final JsonArray extra = object.getAsJsonArray("extra");
                if (extra.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (final JsonElement element : extra) {
                    component.a((os)this.a(element, type, context));
                }
            }
            component.a((pc)context.deserialize(json, (Type)pc.class));
            return component;
        }
        if (json.isJsonArray()) {
            oy component2 = null;
            for (final JsonElement jsonElement : json.getAsJsonArray()) {
                final oy element2 = this.a(jsonElement, jsonElement.getClass(), context);
                if (component2 == null) {
                    component2 = element2;
                }
                else {
                    component2.a((os)element2);
                }
            }
            return component2;
        }
        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
    }
}
