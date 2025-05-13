// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.util;

import net.labymod.api.Laby;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;

public final class MatrixUtil
{
    private static final GFXRenderPipeline RENDER_PIPELINE;
    
    private MatrixUtil() {
    }
    
    public static FloatMatrix4 calculateModelViewMatrix(final FloatMatrix4 modelMatrix) {
        return calculateModelViewMatrix(modelMatrix, MatrixUtil.RENDER_PIPELINE.getViewMatrix());
    }
    
    public static FloatMatrix4 calculateModelViewMatrix(final FloatMatrix4 modelMatrix, final FloatMatrix4 viewMatrix) {
        if (MinecraftVersions.V24w05a.orNewer()) {
            final FloatMatrix4 finalMatrix = viewMatrix.copy();
            finalMatrix.multiply(modelMatrix);
            return finalMatrix;
        }
        return modelMatrix;
    }
    
    static {
        RENDER_PIPELINE = Laby.references().gfxRenderPipeline();
    }
}
