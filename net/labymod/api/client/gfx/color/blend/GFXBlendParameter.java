// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.color.blend;

import net.labymod.api.client.gfx.GFXObject;

public enum GFXBlendParameter implements GFXObject
{
    ZERO(BlendParameter.ZERO), 
    ONE(BlendParameter.ONE), 
    SOURCE_COLOR(BlendParameter.SOURCE_COLOR), 
    ONE_MINUS_SOURCE_COLOR(BlendParameter.ONE_MINUS_SOURCE_COLOR), 
    SOURCE_ALPHA(BlendParameter.SOURCE_ALPHA), 
    ONE_MINUS_SOURCE_ALPHA(BlendParameter.ONE_MINUS_SOURCE_ALPHA), 
    DESTINATION_ALPHA(BlendParameter.DESTINATION_ALPHA), 
    ONE_MINUS_DESTINATION_ALPHA(BlendParameter.ONE_MINUS_DESTINATION_ALPHA), 
    DESTINATION_COLOR(BlendParameter.DESTINATION_COLOR), 
    ONE_MINUS_DESTINATION_COLOR(BlendParameter.ONE_MINUS_DESTINATION_COLOR), 
    SOURCE_ALPHA_SATURATE(BlendParameter.SOURCE_ALPHA_SATURATE);
    
    private final BlendParameter parameter;
    
    private GFXBlendParameter(final BlendParameter parameter) {
        this.parameter = parameter;
    }
    
    @Override
    public int getHandle() {
        return this.parameter.getId();
    }
}
