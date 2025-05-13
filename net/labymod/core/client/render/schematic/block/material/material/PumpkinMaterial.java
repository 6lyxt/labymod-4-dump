// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

public class PumpkinMaterial extends FacingMaterial
{
    public PumpkinMaterial(final String id) {
        super(id);
        this.resourceTop = this.createResource("pumpkin_top");
        this.resourceBottom = this.createResource("pumpkin_top");
        this.resourceSide = this.createResource("pumpkin");
        this.resourceFront = this.resourceLocation;
    }
}
