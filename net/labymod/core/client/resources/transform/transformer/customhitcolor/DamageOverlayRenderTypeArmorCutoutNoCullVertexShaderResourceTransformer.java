// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import java.nio.charset.StandardCharsets;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformer;

@Singleton
@Implements(value = ResourceTransformer.class, key = "damage_overlay_rendertype_armor_cutout_no_cull_vertex_shader")
public class DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer implements ResourceTransformer
{
    @Override
    public byte[] transform(final byte[] resourceData) {
        final String content = new String(resourceData);
        final String[] lines = content.split("\n");
        final StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            final String trimmedLine = line.trim();
            final boolean isSampler2 = trimmedLine.startsWith("uniform sampler2D Sampler2;");
            if (isSampler2) {
                builder.append("uniform sampler2D Sampler1;").append("\n");
            }
            if (trimmedLine.startsWith("in vec2 UV1;")) {
                line = "in ivec2 UV1;";
            }
            builder.append(line).append("\n");
            if (isSampler2) {
                builder.append("uniform vec3 customHitColor;").append("\n");
            }
            if (trimmedLine.startsWith("vertexColor = minecraft_mix_light")) {
                builder.append("overlayColor = texelFetch(Sampler1, UV1, 0);").append("\n");
            }
            if (MinecraftVersions.V23w05a.orNewer()) {
                if (trimmedLine.startsWith("out vec2 texCoord1")) {
                    builder.append("out vec4 overlayColor;").append("\n");
                }
            }
            else if (trimmedLine.startsWith("out vec4 normal;")) {
                builder.append("out vec4 overlayColor;").append("\n");
            }
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
