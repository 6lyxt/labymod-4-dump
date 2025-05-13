// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.compiler;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.matrix.StackProvider;

public class ModernImmediateModelCompiler extends ImmediateModelCompiler
{
    public void compileBox(final StackProvider provider, final BufferConsumer consumer, final ModelBox box, final int packedLight, final float red, final float green, final float blue, final float alpha) {
        final FloatMatrix4 modelViewMatrix = provider.getPosition();
        final FloatMatrix3 normalMatrix = provider.getNormal();
        for (final ModelBoxQuad quad : box.getQuads()) {
            if (quad.isVisible()) {
                ModernImmediateModelCompiler.NORMAL.transform(quad.getNormal(), normalMatrix);
                final float normX = ModernImmediateModelCompiler.NORMAL.getX();
                final float normY = ModernImmediateModelCompiler.NORMAL.getY();
                final float normZ = ModernImmediateModelCompiler.NORMAL.getZ();
                for (final ModelBoxVertex vertex : quad.getVertices()) {
                    final FloatVector3 pos = vertex.getPosition();
                    ModernImmediateModelCompiler.POSITION.transform(pos, modelViewMatrix, 0.0625f);
                    final FloatVector2 uv = vertex.getUV();
                    this.addVertex(consumer, red, green, blue, alpha, uv, packedLight, normX, normY, normZ);
                }
            }
        }
    }
}
