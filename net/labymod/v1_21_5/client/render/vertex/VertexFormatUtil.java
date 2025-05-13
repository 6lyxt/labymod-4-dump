// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.render.vertex;

import java.util.List;
import net.labymod.core.thirdparty.optifine.vertex.OptiFineVertexFormatElement;
import net.labymod.api.thirdparty.optifine.OptiFine;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.labymod.api.client.render.shader.ShaderProgram;
import com.mojang.blaze3d.vertex.VertexFormat;

public final class VertexFormatUtil
{
    public static void bindAttributeLocation(final VertexFormat vertexFormat, final ShaderProgram program) {
        final List<VertexFormatElement> elements = vertexFormat.getElements();
        for (int index = 0; index < elements.size(); ++index) {
            final VertexFormatElement element = elements.get(index);
            final String name = vertexFormat.getElementAttributeNames().get(index);
            int location = index;
            if (OptiFine.isPresent()) {
                final VertexFormatElement vertexFormatElement = element;
                if (vertexFormatElement instanceof final OptiFineVertexFormatElement ofElement) {
                    location = ofElement.bridge$optifine$getAttributeIndex();
                }
            }
            program.bindAttributeLocation(location, name);
        }
    }
}
