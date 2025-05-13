// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;

public class EmptyMaterial extends Material
{
    public EmptyMaterial(final String id) {
        super(id);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.NONE;
    }
    
    @Override
    public float getTransparency() {
        return 1.0f;
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
}
