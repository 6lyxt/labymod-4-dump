// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;

public class SolidMaterial extends Material
{
    public SolidMaterial(final String id) {
        super(id);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
