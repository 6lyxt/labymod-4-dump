// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

import com.google.gson.JsonParseException;
import net.labymod.api.Laby;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import com.google.gson.JsonDeserializationContext;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.Iterator;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import net.labymod.api.client.component.KeybindComponent;
import com.google.gson.JsonPrimitive;
import net.labymod.api.client.component.TextComponent;
import com.google.gson.JsonArray;
import net.labymod.core.event.client.component.ComponentSerializeEventCaller;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import org.spongepowered.asm.mixin.Shadow;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.serializer.Serializer;

@Mixin({ nr.a.class })
public abstract class MixinComponentSerializer implements Serializer
{
    @Shadow
    protected abstract void a(final ob p0, final JsonObject p1, final JsonSerializationContext p2);
    
    @Overwrite
    public JsonElement a(nr value, final Type type, final JsonSerializationContext context) {
        final ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component)value);
        final Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (nr)unwrappedComponent;
        }
        final JsonObject json = (event.getJson() != null) ? event.getJson() : new JsonObject();
        if (!value.c().g()) {
            this.a(value.c(), json, context);
        }
        if (!value.b().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (final nr sibling : value.b()) {
                extra.add(this.a(sibling, sibling.getClass(), context));
            }
            json.add("extra", (JsonElement)extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof final TextComponent textComponent) {
                json.addProperty("text", textComponent.getText());
            }
            else if (value instanceof final of translateComponent) {
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j().length > 0) {
                    final JsonArray args = new JsonArray();
                    for (final Object arg : translateComponent.j()) {
                        if (arg instanceof final nr nr) {
                            args.add(this.a(nr, arg.getClass(), context));
                        }
                        else {
                            args.add((JsonElement)new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", (JsonElement)args);
                }
            }
            else if (value instanceof final nz scoreComponent) {
                final JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.h());
                score.addProperty("objective", scoreComponent.j());
                json.add("score", (JsonElement)score);
            }
            else if (value instanceof final oa selectorComponent) {
                json.addProperty("selector", selectorComponent.h());
            }
            else if (value instanceof nw) {
                final KeybindComponent keybindComponent = (KeybindComponent)value;
                json.addProperty("keybind", keybindComponent.getKeybind());
            }
            else {
                if (!(value instanceof ny)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                }
                final ny nbtComponent = (ny)value;
                json.addProperty("nbt", nbtComponent.h());
                json.addProperty("interpret", Boolean.valueOf(nbtComponent.i()));
                if (value instanceof final ny.a blockNbtComponent) {
                    json.addProperty("block", blockNbtComponent.j());
                }
                else if (value instanceof final ny.b entityNbtComponent) {
                    json.addProperty("entity", entityNbtComponent.j());
                }
                else {
                    if (!(value instanceof ny.c)) {
                        throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                    }
                    final ny.c storageNbtComponent = (ny.c)value;
                    json.addProperty("storage", storageNbtComponent.j().toString());
                }
            }
        }
        return (JsonElement)json;
    }
    
    @Overwrite
    public nx a(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return (nx)new oe(json.getAsString());
        }
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            final ComponentDeserializeEvent event = Laby.fireEvent(new ComponentDeserializeEvent(object));
            nx component;
            if (event.getComponent() != null) {
                component = (nx)event.getComponent();
            }
            else if (object.has("text")) {
                component = (nx)new oe(afd.h(object, "text"));
            }
            else if (object.has("translate")) {
                final String translationKey = afd.h(object, "translate");
                if (object.has("with")) {
                    final JsonArray jsonArgs = afd.u(object, "with");
                    final Object[] args = new Object[jsonArgs.size()];
                    for (int i = 0; i < args.length; ++i) {
                        args[i] = this.a(jsonArgs.get(i), type, context);
                        if (args[i] instanceof final oe textArg) {
                            if (textArg.c().g() && textArg.b().isEmpty()) {
                                args[i] = textArg.h();
                            }
                        }
                    }
                    component = (nx)new of(translationKey, args);
                }
                else {
                    component = (nx)new of(translationKey);
                }
            }
            else if (object.has("score")) {
                final JsonObject score = afd.t(object, "score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                component = (nx)new nz(afd.h(score, "name"), afd.h(score, "objective"));
            }
            else if (object.has("selector")) {
                component = (nx)new oa(afd.h(object, "selector"));
            }
            else if (object.has("keybind")) {
                component = (nx)new nw(afd.h(object, "keybind"));
            }
            else {
                if (!object.has("nbt")) {
                    throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                }
                final String nbtPath = afd.h(object, "nbt");
                final boolean interpreting = afd.a(object, "interpret", false);
                if (object.has("block")) {
                    component = (nx)new ny.a(nbtPath, interpreting, afd.h(object, "block"));
                }
                else if (object.has("entity")) {
                    component = (nx)new ny.b(nbtPath, interpreting, afd.h(object, "entity"));
                }
                else {
                    if (!object.has("storage")) {
                        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                    }
                    component = (nx)new ny.c(nbtPath, interpreting, new vk(afd.h(object, "storage")));
                }
            }
            if (object.has("extra")) {
                final JsonArray extra = object.getAsJsonArray("extra");
                if (extra.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (final JsonElement element : extra) {
                    component.a((nr)this.a(element, type, context));
                }
            }
            component.a((ob)context.deserialize(json, (Type)ob.class));
            return component;
        }
        if (json.isJsonArray()) {
            nx component2 = null;
            for (final JsonElement jsonElement : json.getAsJsonArray()) {
                final nx element2 = this.a(jsonElement, jsonElement.getClass(), context);
                if (component2 == null) {
                    component2 = element2;
                }
                else {
                    component2.a((nr)element2);
                }
            }
            return component2;
        }
        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
    }
}
