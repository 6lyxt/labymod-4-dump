// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.renderer.shadow;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;

public class DefaultShadowRenderPassContext implements ShadowRenderPassContext
{
    @Override
    public boolean isShadowRenderPass() {
        return false;
    }
    
    @Nullable
    @Override
    public FloatMatrix4 getShadowModelViewMatrix() {
        return null;
    }
    
    @Nullable
    @Override
    public FloatMatrix4 getShadowModelViewInverseMatrix() {
        return null;
    }
    
    @Nullable
    @Override
    public FloatMatrix4 getShadowProjectionMatrix() {
        return null;
    }
    
    @Nullable
    @Override
    public FloatMatrix4 getShadowProjectionInverseMatrix() {
        return null;
    }
}
