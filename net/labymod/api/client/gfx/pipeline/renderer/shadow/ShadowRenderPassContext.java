// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.shadow;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;

public interface ShadowRenderPassContext
{
    boolean isShadowRenderPass();
    
    @Nullable
    FloatMatrix4 getShadowModelViewMatrix();
    
    @Nullable
    FloatMatrix4 getShadowModelViewInverseMatrix();
    
    @Nullable
    FloatMatrix4 getShadowProjectionMatrix();
    
    @Nullable
    FloatMatrix4 getShadowProjectionInverseMatrix();
}
