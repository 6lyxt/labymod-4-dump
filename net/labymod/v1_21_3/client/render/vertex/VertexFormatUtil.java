// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.render.vertex;

import java.util.List;
import net.labymod.core.thirdparty.optifine.vertex.OptiFineVertexFormatElement;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.client.render.shader.ShaderProgram;

public final class VertexFormatUtil
{
    public static void bindAttributeLocation(final fgx vertexFormat, final ShaderProgram program) {
        final List<fgy> elements = vertexFormat.c();
        for (int index = 0; index < elements.size(); ++index) {
            final fgy element = elements.get(index);
            final String name = vertexFormat.d().get(index);
            int location = index;
            if (OptiFine.isPresent()) {
                final fgy fgy = element;
                if (fgy instanceof final OptiFineVertexFormatElement ofElement) {
                    location = ofElement.bridge$optifine$getAttributeIndex();
                }
            }
            program.bindAttributeLocation(location, name);
        }
    }
}
