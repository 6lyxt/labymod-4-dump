// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.util.math.vector.FloatMatrix4;

public class Matrices
{
    private final FloatMatrix4 viewMatrix;
    private final FloatMatrix4 modelViewMatrix;
    private final FloatMatrix4 projectionMatrix;
    private final FloatMatrix4 textureMatrix;
    
    public Matrices() {
        this(FloatMatrix4.newIdentity(), FloatMatrix4.newIdentity(), FloatMatrix4.newIdentity(), FloatMatrix4.newIdentity());
    }
    
    public Matrices(final FloatMatrix4 viewMatrix, final FloatMatrix4 modelViewMatrix, final FloatMatrix4 projectionMatrix, final FloatMatrix4 textureMatrix) {
        this.viewMatrix = viewMatrix;
        this.modelViewMatrix = modelViewMatrix;
        this.projectionMatrix = projectionMatrix;
        this.textureMatrix = textureMatrix;
    }
    
    public FloatMatrix4 viewMatrix() {
        return this.viewMatrix;
    }
    
    public FloatMatrix4 modelViewMatrix() {
        return this.modelViewMatrix;
    }
    
    public FloatMatrix4 projectionMatrix() {
        return this.projectionMatrix;
    }
    
    public FloatMatrix4 textureMatrix() {
        return this.textureMatrix;
    }
    
    public void updateModelViewMatrix(final FloatMatrix4 matrix) {
        this.modelViewMatrix.set(matrix);
    }
    
    public void updateProjectionMatrix(final FloatMatrix4 matrix) {
        this.projectionMatrix.set(matrix);
    }
    
    public void updateTextureMatrix(final FloatMatrix4 matrix) {
        this.textureMatrix.set(matrix);
    }
}
