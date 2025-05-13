// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.immediate;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.util.math.vector.FloatMatrix4;

public final class IdentiyModelMatrixImmediateDrawPhase implements ImmediateDrawPhase
{
    private static final FloatMatrix4 DEFAULT_MODEL_MATRIX;
    private final GFXRenderPipeline renderPipeline;
    private FloatMatrix4 previousModelViewMatrix;
    
    public IdentiyModelMatrixImmediateDrawPhase() {
        this.renderPipeline = Laby.references().gfxRenderPipeline();
    }
    
    @Override
    public void setup() {
        this.previousModelViewMatrix = this.renderPipeline.getModelViewMatrix();
        this.renderPipeline.setModelViewMatrix(IdentiyModelMatrixImmediateDrawPhase.DEFAULT_MODEL_MATRIX);
    }
    
    @Override
    public void end() {
        this.renderPipeline.setModelViewMatrix(this.previousModelViewMatrix);
    }
    
    static {
        DEFAULT_MODEL_MATRIX = FloatMatrix4.newIdentity();
    }
}
