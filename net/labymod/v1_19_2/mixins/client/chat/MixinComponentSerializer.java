// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.chat;

import com.google.gson.JsonParseException;
import net.labymod.api.Laby;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.Iterator;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import net.labymod.v1_19_2.client.network.chat.VersionedBaseComponent;
import net.labymod.core.event.client.component.ComponentSerializeEventCaller;
import net.labymod.api.client.component.Component;
import net.labymod.v1_19_2.client.network.chat.MutableComponentAccessor;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import java.util.Optional;
import org.spongepowered.asm.mixin.Shadow;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ rq.a.class })
public abstract class MixinComponentSerializer
{
    @Shadow
    protected abstract void a(final sj p0, final JsonObject p1, final JsonSerializationContext p2);
    
    @Shadow
    protected abstract void a(final JsonSerializationContext p0, final JsonObject p1, final Optional<rq> p2);
    
    @Shadow
    private static Object a(final Object arg) {
        return null;
    }
    
    @Shadow
    protected abstract Optional<rq> a(final Type p0, final JsonDeserializationContext p1, final JsonObject p2);
    
    @Overwrite
    public JsonElement a(rq value, final Type type, final JsonSerializationContext context) {
        Component labyComponent;
        if (value instanceof final MutableComponentAccessor accessor) {
            labyComponent = accessor.getLabyComponent();
        }
        else {
            labyComponent = (Component)value;
        }
        final ComponentSerializeEvent event = ComponentSerializeEventCaller.call(labyComponent);
        final Component unwrappedComponent = event.getUnwrappedComponent();
        if (unwrappedComponent != null) {
            if (unwrappedComponent instanceof final VersionedBaseComponent versionedBaseComponent) {
                value = versionedBaseComponent.getContents();
            }
            else {
                value = (rq)unwrappedComponent;
            }
        }
        final JsonObject json = (event.getJson() != null) ? event.getJson() : new JsonObject();
        if (!value.a().g()) {
            this.a(value.a(), json, context);
        }
        if (!value.c().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (final rq sibling : value.c()) {
                extra.add(this.a(sibling, rq.class, context));
            }
            json.add("extra", (JsonElement)extra);
        }
        if (!event.wasSerialized()) {
            final rr contents = value.b();
            if (contents == rr.a) {
                json.addProperty("text", "");
            }
            else if (contents instanceof final ss literal) {
                json.addProperty("text", literal.a());
            }
            else if (contents instanceof final sx translatable) {
                json.addProperty("translate", translatable.a());
                if (translatable.b().length > 0) {
                    final JsonArray args = new JsonArray();
                    for (final Object arg : translatable.b()) {
                        if (arg instanceof final rq rq) {
                            args.add(this.a(rq, arg.getClass(), context));
                        }
                        else {
                            args.add((JsonElement)new JsonPrimitive(String.valueOf(arg)));
                        }
                    }
                    json.add("with", (JsonElement)args);
                }
            }
            else if (contents instanceof final su score) {
                final JsonObject scoreJson = new JsonObject();
                scoreJson.addProperty("name", score.a());
                scoreJson.addProperty("objective", score.c());
                json.add("score", (JsonElement)scoreJson);
            }
            else if (contents instanceof final sv selector) {
                json.addProperty("selector", selector.a());
                this.a(context, json, selector.c());
            }
            else if (contents instanceof final sq keybind) {
                json.addProperty("keybind", keybind.a());
            }
            else {
                if (!(contents instanceof st)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(contents) + " as a Component");
                }
                final st nbt = (st)contents;
                json.addProperty("nbt", nbt.a());
                json.addProperty("interpret", Boolean.valueOf(nbt.b()));
                this.a(context, json, nbt.c());
                final so source = nbt.d();
                if (source instanceof final sn block) {
                    json.addProperty("block", block.a());
                }
                else if (source instanceof final sp entity) {
                    json.addProperty("entity", entity.a());
                }
                else {
                    if (!(source instanceof sw)) {
                        throw new IllegalArgumentException("Don't know how to serialize " + String.valueOf(contents) + " as a Component");
                    }
                    final sw storage = (sw)source;
                    json.addProperty("storage", storage.a().toString());
                }
            }
        }
        return (JsonElement)json;
    }
    
    @Overwrite
    public sb a(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return rq.b(json.getAsString());
        }
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            final ComponentDeserializeEvent event = Laby.fireEvent(new ComponentDeserializeEvent(object));
            sb component;
            if (event.getComponent() != null) {
                component = ((VersionedBaseComponent)event.getComponent()).getHolder();
            }
            else if (object.has("text")) {
                final String text = alz.h(object, "text");
                component = (text.isEmpty() ? rq.h() : rq.b(text));
            }
            else if (object.has("translate")) {
                final String translationKey = alz.h(object, "translate");
                if (object.has("with")) {
                    final JsonArray argsJson = alz.u(object, "with");
                    final Object[] args = new Object[argsJson.size()];
                    for (int i = 0; i < args.length; ++i) {
                        args[i] = a(this.a(argsJson.get(i), type, context));
                    }
                    component = rq.a(translationKey, args);
                }
                else {
                    component = rq.c(translationKey);
                }
            }
            else if (object.has("score")) {
                final JsonObject score = alz.t(object, "score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                component = rq.a(alz.h(score, "name"), alz.h(score, "objective"));
            }
            else if (object.has("selector")) {
                final Optional<rq> separator = this.a(type, context, object);
                component = rq.a(alz.h(object, "selector"), (Optional)separator);
            }
            else if (object.has("keybind")) {
                component = rq.d(alz.h(object, "keybind"));
            }
            else {
                if (!object.has("nbt")) {
                    throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                }
                final String nbtPath = alz.h(object, "nbt");
                final Optional<rq> separator2 = this.a(type, context, object);
                final boolean interpreting = alz.a(object, "interpret", false);
                so source;
                if (object.has("block")) {
                    source = (so)new sn(alz.h(object, "block"));
                }
                else if (object.has("entity")) {
                    source = (so)new sp(alz.h(object, "entity"));
                }
                else {
                    if (!object.has("storage")) {
                        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
                    }
                    source = (so)new sw(new abb(alz.h(object, "storage")));
                }
                component = rq.a(nbtPath, interpreting, (Optional)separator2, source);
            }
            if (object.has("extra")) {
                final JsonArray extra = alz.u(object, "extra");
                if (extra.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (final JsonElement element : extra) {
                    component.b((rq)this.a(element, type, context));
                }
            }
            component.b((sj)context.deserialize(json, (Type)sj.class));
            return component;
        }
        if (json.isJsonArray()) {
            final JsonArray array = json.getAsJsonArray();
            sb component = null;
            for (final JsonElement element2 : array) {
                final sb entry = this.a(element2, element2.getClass(), context);
                if (component == null) {
                    component = entry;
                }
                else {
                    component.b((rq)entry);
                }
            }
            return component;
        }
        throw new JsonParseException("Don't know how to turn " + String.valueOf(json) + " into a Component");
    }
}
