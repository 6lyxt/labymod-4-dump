// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gfx.pipeline.util.MatrixUtil;
import net.labymod.api.util.math.vector.FloatMatrix4;

public class MatrixStorage
{
    private static final FloatMatrix4 SHARED_MATRIX;
    private final FloatMatrix4 viewMatrix;
    private final FloatMatrix4 projectionMatrix;
    private final FloatMatrix4 modelViewMatrix;
    private final GFXRenderPipeline renderPipeline;
    
    public MatrixStorage(final GFXRenderPipeline renderPipeline) {
        this.viewMatrix = FloatMatrix4.newIdentity();
        this.projectionMatrix = FloatMatrix4.newIdentity();
        this.modelViewMatrix = FloatMatrix4.newIdentity();
        this.renderPipeline = renderPipeline;
    }
    
    public FloatMatrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    public void setProjectionMatrix(final FloatMatrix4 projectionMatrix) {
        this.projectionMatrix.set(projectionMatrix);
    }
    
    public void setGameProjectionMatrix() {
        this.projectionMatrix.set(this.renderPipeline.matrices().projectionMatrix());
    }
    
    public FloatMatrix4 getModelViewMatrix() {
        return this.modelViewMatrix;
    }
    
    public void setModelViewMatrix(final FloatMatrix4 modelViewMatrix) {
        this.setModelViewMatrix(modelViewMatrix, 5);
    }
    
    public void setModelViewMatrix(FloatMatrix4 modelViewMatrix, final int options) {
        if (MatrixOptions.hasOption(options, 4)) {
            modelViewMatrix = MatrixUtil.calculateModelViewMatrix(modelViewMatrix);
        }
        final boolean multiplyWithGameModelViewMatrix = MatrixOptions.hasOption(options, 1);
        if (multiplyWithGameModelViewMatrix && this.renderPipeline.renderEnvironmentContext().isScreenContext() && !PlatformEnvironment.isAncientOpenGL()) {
            FloatMatrix4 modelMatrix = this.renderPipeline.matrices().modelViewMatrix();
            MatrixStorage.SHARED_MATRIX.set(modelMatrix);
            modelMatrix = MatrixStorage.SHARED_MATRIX;
            modelMatrix.multiply(modelViewMatrix);
            modelViewMatrix = modelMatrix;
        }
        this.modelViewMatrix.set(modelViewMatrix);
    }
    
    public FloatMatrix4 getViewMatrix() {
        return this.viewMatrix;
    }
    
    public void setViewMatrix(final FloatMatrix4 viewMatrix) {
        this.viewMatrix.set(viewMatrix);
    }
    
    static {
        SHARED_MATRIX = FloatMatrix4.newIdentity();
    }
}
