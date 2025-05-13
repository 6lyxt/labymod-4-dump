// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.lightning.PointLightSource;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.render.schematic.block.Block;

public class GlowstoneMaterial extends SolidMaterial
{
    public GlowstoneMaterial() {
        super("glowstone");
    }
    
    @Override
    public int getLight(final Block block) {
        return 15;
    }
    
    @Override
    public int getLightColor(final Block block) {
        return ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public LightSource createLightSource(final int x, final int y, final int z, final Block block) {
        final PointLightSource lightSource = new PointLightSource();
        lightSource.getPosition().set(x + 0.5f, y + 0.5f, z + 0.5f);
        lightSource.getColor().set(1.0f, 1.0f, 1.0f);
        lightSource.setQuadratic(0.0075f);
        lightSource.setLinear(0.045f);
        lightSource.setConstant(1.0f);
        return lightSource;
    }
}
