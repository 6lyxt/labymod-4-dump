// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline;

import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.Matrices;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DShaderUniformPipeline;

@Singleton
@Implements(Blaze3DShaderUniformPipeline.class)
public class VersionedBlaze3DShaderUniformPipeline extends AbstractBlaze3DShaderUniformPipeline implements Blaze3DShaderUniformPipeline
{
    @Inject
    public VersionedBlaze3DShaderUniformPipeline() {
        this.matrices = new VersionedMatrices();
        this.fog.setStart(() -> bus.i.d);
        this.fog.setEnd(() -> bus.i.e);
    }
    
    @Override
    public float getLineWidth() {
        return 1.0f;
    }
    
    @Override
    public float getGameTime() {
        return 0.0f;
    }
    
    private static class VersionedMatrices extends Matrices
    {
        @Override
        public FloatMatrix4 modelViewMatrix() {
            final FloatMatrix4 modelViewMatrix = super.modelViewMatrix();
            modelViewMatrix.set(MatrixTracker.MODEL_VIEW_MATRIX.getProvider().getPosition());
            return modelViewMatrix;
        }
        
        @Override
        public FloatMatrix4 projectionMatrix() {
            final FloatMatrix4 projectionMatrix = super.projectionMatrix();
            projectionMatrix.set(MatrixTracker.PROJECTION_MATRIX.getProvider().getPosition());
            return projectionMatrix;
        }
        
        @Override
        public FloatMatrix4 textureMatrix() {
            final FloatMatrix4 textureMatrix = super.textureMatrix();
            textureMatrix.set(MatrixTracker.TEXTURE_MATRIX.getProvider().getPosition());
            return textureMatrix;
        }
    }
}
