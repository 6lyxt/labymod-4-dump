// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import java.nio.charset.StandardCharsets;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformer;

@Singleton
@Implements(value = ResourceTransformer.class, key = "damage_overlay_rendertype_armor_cutout_no_cull_fragment_shader")
public class DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer implements ResourceTransformer
{
    @Override
    public byte[] transform(final byte[] resourceData) {
        final String content = new String(resourceData);
        final String[] lines = content.split("\n");
        final StringBuilder builder = new StringBuilder();
        for (final String line : lines) {
            final String trimmedLine = line.trim();
            if (trimmedLine.startsWith("in vec4 vertexColor;")) {
                builder.append("in vec4 overlayColor;").append("\n");
            }
            if (trimmedLine.startsWith("fragColor = linear_fog")) {
                builder.append("color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);").append("\n");
            }
            builder.append(line).append("\n");
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
