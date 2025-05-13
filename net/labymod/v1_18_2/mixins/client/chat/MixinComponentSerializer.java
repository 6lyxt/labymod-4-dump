// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.chat;

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

@Mixin({ qk.a.class })
public abstract class MixinComponentSerializer implements Serializer
{
    @Shadow
    protected abstract void a(final qu p0, final JsonObject p1, final JsonSerializationContext p2);
    
    @Shadow
    protected abstract void a(final JsonSerializationContext p0, final JsonObject p1, final Optional<qk> p2);
    
    @Shadow
    protected abstract Optional<qk> a(final Type p0, final JsonDeserializationContext p1, final JsonObject p2);
    
    @Overwrite
    public JsonElement a(qk value, final Type type, final JsonSerializationContext context) {
        final ComponentSerializeEvent event = ComponentSerializeEventCaller.call((Component)value);
        final Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            value = (qk)unwrappedComponent;
        }
        final JsonObject json = (event.getJson() != null) ? event.getJson() : new JsonObject();
        if (!value.c().g()) {
            this.a(value.c(), json, context);
        }
        if (!value.b().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (final qk sibling : value.b()) {
                extra.add(this.a(sibling, sibling.getClass(), context));
            }
            json.add("extra", (JsonElement)extra);
        }
        if (!event.wasSerialized()) {
            if (value instanceof final TextComponent textComponent) {
                json.addProperty("text", textComponent.getText());
            }
            else if (value instanceof final qy translateComponent) {
                json.addProperty("translate", translateComponent.i());
                if (translateComponent.j().length > 0) {
                    final JsonArray args = new JsonArray();
                    for (final Object arg : translateComponent.j()) {
                        if (arg instanceof final qk qk) {
                            args.add(this.a(qk, arg.getClass(), context));
                        }
                        else {
                            args.add((JsonElement)new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", (JsonElement)args);
                }
            }
            else if (value instanceof final qs scoreComponent) {
                final JsonObject score = new JsonObject();
                score.addProperty("name", scoreComponent.h());
                score.addProperty("objective", scoreComponent.j());
                json.add("score", (JsonElement)score);
            }
            else if (value instanceof final qt selectorComponent) {
                json.addProperty("selector", selectorComponent.h());
                this.a(context, json, selectorComponent.j());
            }
            else if (value instanceof final qp keybindComponent) {
                json.addProperty("keybind", keybindComponent.i());
            }
            else {
                if (!(value instanceof qr)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                }
                final qr nbtComponent = (qr)value;
                json.addProperty("nbt", nbtComponent.h());
                json.addProperty("interpret", Boolean.valueOf(nbtComponent.i()));
                this.a(context, json, nbtComponent.e);
                if (value instanceof final qr.a blockNbtComponent) {
                    json.addProperty("block", blockNbtComponent.j());
                }
                else if (value instanceof final qr.b entityNbtComponent) {
                    json.addProperty("entity", entityNbtComponent.j());
                }
                else {
                    if (!(value instanceof qr.c)) {
                        throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(value) + " as a Component");
                    }
                    final qr.c storageNbtComponent = (qr.c)value;
                    json.addProperty("storage", storageNbtComponent.j().toString());
                }
            }
        }
        return (JsonElement)json;
    }
    
    @Overwrite
    public qq a(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return (qq)new qx(json.getAsString());
        }
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            final ComponentDeserializeEvent event = Laby.fireEvent(new ComponentDeserializeEvent(object));
            qq component;
            if (event.getComponent() != null) {
                component = (qq)event.getComponent();
            }
            else if (object.has("text")) {
                component = (qq)new qx(ajd.h(object, "text"));
            }
            else if (object.has("translate")) {
                final String translationKey = ajd.h(object, "translate");
                if (object.has("with")) {
                    final JsonArray jsonArgs = ajd.u(object, "with");
                    final Object[] args = new Object[jsonArgs.size()];
                    for (int i = 0; i < args.length; ++i) {
                        args[i] = this.a(jsonArgs.get(i), type, context);
                        if (args[i] instanceof final qx textArg) {
                            if (textArg.c().g() && textArg.b().isEmpty()) {
                                args[i] = textArg.h();
                            }
                        }
                    }
                    component = (qq)new qy(translationKey, args);
                }
                else {
                    component = (qq)new qy(translationKey);
                }
            }
            else if (object.has("score")) {
                final JsonObject score = ajd.t(object, "score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                component = (qq)new qs(ajd.h(score, "name"), ajd.h(score, "objective"));
            }
            else if (object.has("selector")) {
                final Optional<qk> separator = this.a(type, context, object);
                component = (qq)new qt(ajd.h(object, "selector"), (Optional)separator);
            }
            else if (object.has("keybind")) {
                component = (qq)new qp(ajd.h(object, "keybind"));
            }
            else {
                if (!object.has("nbt")) {
                    throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                }
                final String nbtPath = ajd.h(object, "nbt");
                final Optional<qk> separator2 = this.a(type, context, object);
                final boolean interpreting = ajd.a(object, "interpret", false);
                if (object.has("block")) {
                    component = (qq)new qr.a(nbtPath, interpreting, ajd.h(object, "block"), (Optional)separator2);
                }
                else if (object.has("entity")) {
                    component = (qq)new qr.b(nbtPath, interpreting, ajd.h(object, "entity"), (Optional)separator2);
                }
                else {
                    if (!object.has("storage")) {
                        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                    }
                    component = (qq)new qr.c(nbtPath, interpreting, new yt(ajd.h(object, "storage")), (Optional)separator2);
                }
            }
            if (object.has("extra")) {
                final JsonArray extra = object.getAsJsonArray("extra");
                if (extra.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (final JsonElement element : extra) {
                    component.a((qk)this.a(element, type, context));
                }
            }
            component.a((qu)context.deserialize(json, (Type)qu.class));
            return component;
        }
        if (json.isJsonArray()) {
            qq component2 = null;
            for (final JsonElement jsonElement : json.getAsJsonArray()) {
                final qq element2 = this.a(jsonElement, jsonElement.getClass(), context);
                if (component2 == null) {
                    component2 = element2;
                }
                else {
                    component2.a((qk)element2);
                }
            }
            return component2;
        }
        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
    }
}
