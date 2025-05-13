// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.lighting;

import net.labymod.api.Laby;

public enum LightType
{
    SKY, 
    BLOCK;
    
    private final LightingLayerMapper mapper;
    
    private LightType() {
        this.mapper = Laby.references().lightingLayerMapper();
    }
    
    public Object toMinecraft() {
        return this.mapper.toMinecraft(this);
    }
}
