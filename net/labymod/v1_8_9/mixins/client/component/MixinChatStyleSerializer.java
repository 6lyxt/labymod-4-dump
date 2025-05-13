// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.component;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.component.VersionedTextColor;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import net.labymod.v1_8_9.client.component.VersionedNamedTextColors;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.serializer.Serializer;

@Mixin({ ez.a.class })
public class MixinChatStyleSerializer implements Serializer
{
    @Inject(method = { "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/ChatStyle;" }, at = { @At("RETURN") })
    public void test(final JsonElement element, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_, final CallbackInfoReturnable<ez> cir) {
        final ez style = (ez)cir.getReturnValue();
        if (style == null || !element.isJsonObject()) {
            return;
        }
        final JsonObject jsonObject = element.getAsJsonObject();
        if (!jsonObject.has("color")) {
            return;
        }
        final JsonElement jsonElement = jsonObject.get("color");
        if (!jsonElement.isJsonPrimitive()) {
            return;
        }
        final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
        if (!jsonPrimitive.isString()) {
            return;
        }
        final String name = jsonPrimitive.getAsString();
        final VersionedTextColor textColor = VersionedNamedTextColors.byName(name);
        style.a((textColor == null) ? null : textColor.getFormatting());
    }
}
