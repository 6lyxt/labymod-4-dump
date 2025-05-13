// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformer;

@Singleton
@Implements(value = ResourceTransformer.class, key = "damage_overlay_rendertype_armor_cutout_no_cull_json")
public class DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer implements ResourceTransformer
{
    @Inject
    public DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer() {
    }
    
    @Override
    public byte[] transform(final byte[] resourceData) {
        final String content = new String(resourceData);
        final JsonElement shaderElement = new JsonParser().parse(content);
        if (!shaderElement.isJsonObject()) {
            return this.convertToByteArray(shaderElement);
        }
        final JsonObject object = shaderElement.getAsJsonObject();
        if (!object.has("samplers") || !object.get("samplers").isJsonArray()) {
            return this.convertToByteArray(shaderElement);
        }
        final JsonArray samplers = object.get("samplers").getAsJsonArray();
        final JsonObject samplerObject = new JsonObject();
        samplerObject.addProperty("name", "Sampler1");
        samplers.add((JsonElement)samplerObject);
        object.add("samplers", (JsonElement)samplers);
        return this.convertToByteArray(shaderElement);
    }
    
    private byte[] convertToByteArray(@NotNull final JsonElement element) {
        return element.toString().getBytes(StandardCharsets.UTF_8);
    }
}
