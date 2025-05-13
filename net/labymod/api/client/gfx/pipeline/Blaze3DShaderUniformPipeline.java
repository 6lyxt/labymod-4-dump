// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;

@Referenceable
public interface Blaze3DShaderUniformPipeline extends FrameContext
{
    FloatVector2 screenSize();
    
    FloatVector4 colorModulator();
    
    float getLineWidth();
    
    float getGameTime();
    
    Matrices matrices();
    
    MojangLight light();
    
    Fog fog();
}
