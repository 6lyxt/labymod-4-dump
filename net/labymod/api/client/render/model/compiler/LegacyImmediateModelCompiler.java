// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.compiler;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.matrix.StackProvider;

public class LegacyImmediateModelCompiler extends ImmediateModelCompiler
{
    private static final int[] INDEX_BUFFER;
    
    @Override
    protected void compileBoxes(final StackProvider provider, final BufferConsumer consumer, final ModelPart part, final int packedLight, final float red, final float green, final float blue, final float alpha) {
        final GFXBridge gfx = Laby.gfx();
        gfx.color4f(red, green, blue, alpha);
        super.compileBoxes(provider, consumer, part, packedLight, red, green, blue, alpha);
        gfx.blaze3DBufferSource().endLegacyBuffer();
    }
    
    @Override
    protected void compileBox(final StackProvider provider, final BufferConsumer consumer, final ModelBox box, final int packedLight, final float red, final float green, final float blue, final float alpha) {
        final FloatMatrix4 modelViewMatrix = provider.getPosition();
        final FloatMatrix3 normalMatrix = provider.getNormal();
        for (final ModelBoxQuad quad : box.getQuads()) {
            if (quad.isVisible()) {
                LegacyImmediateModelCompiler.NORMAL.transform(quad.getNormal(), normalMatrix);
                final float normX = LegacyImmediateModelCompiler.NORMAL.getX();
                final float normY = LegacyImmediateModelCompiler.NORMAL.getY();
                final float normZ = LegacyImmediateModelCompiler.NORMAL.getZ();
                final ModelBoxVertex[] vertices = quad.getVertices();
                for (final int index : LegacyImmediateModelCompiler.INDEX_BUFFER) {
                    final ModelBoxVertex vertex = vertices[index];
                    LegacyImmediateModelCompiler.POSITION.transform(vertex.getPosition(), modelViewMatrix, 0.0625f);
                    final FloatVector2 uv = vertex.getUV();
                    this.addVertex(consumer, red, green, blue, alpha, uv, packedLight, normX, normY, normZ);
                }
            }
        }
    }
    
    static {
        INDEX_BUFFER = new int[] { 0, 1, 2, 2, 3, 0 };
    }
}
