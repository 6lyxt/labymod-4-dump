// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.shader;

import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ShaderPipeline
{
    boolean hasActiveShaderPack();
    
    ShadowRenderPassContext shadowRenderPassContext();
}
