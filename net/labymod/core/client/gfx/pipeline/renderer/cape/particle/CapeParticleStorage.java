// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.renderer.cape.particle;

import net.labymod.api.util.math.vector.FloatMatrix4;

public class CapeParticleStorage
{
    private final FloatMatrix4 modelViewMatrix;
    private final float alpha;
    
    public CapeParticleStorage(final FloatMatrix4 modelViewMatrix, float alpha) {
        this.modelViewMatrix = modelViewMatrix;
        alpha = 1.0f - (alpha - 1.0f);
        this.alpha = alpha;
    }
    
    public FloatMatrix4 modelViewMatrix() {
        return this.modelViewMatrix;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
}
