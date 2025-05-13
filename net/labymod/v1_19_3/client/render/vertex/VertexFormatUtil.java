// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.render.vertex;

import com.google.common.collect.ImmutableList;
import net.labymod.core.thirdparty.optifine.vertex.OptiFineVertexFormatElement;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.client.render.shader.ShaderProgram;

public final class VertexFormatUtil
{
    public static void bindAttributeLocation(final eei vertexFormat, final ShaderProgram program) {
        final ImmutableList<eej> elements = (ImmutableList<eej>)vertexFormat.c();
        for (int index = 0; index < elements.size(); ++index) {
            final eej element = (eej)elements.get(index);
            final String name = (String)vertexFormat.d().get(index);
            int location = index;
            if (OptiFine.isPresent() && element instanceof OptiFineVertexFormatElement) {
                final OptiFineVertexFormatElement ofElement = (OptiFineVertexFormatElement)element;
                location = ofElement.bridge$optifine$getAttributeIndex();
            }
            program.bindAttributeLocation(location, name);
        }
    }
}
