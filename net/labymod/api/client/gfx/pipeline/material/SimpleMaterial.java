// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.material;

import net.labymod.api.client.resources.ResourceLocation;

public class SimpleMaterial implements MutableMaterial
{
    private ResourceLocation textureLocation;
    private MaterialColor color;
    private boolean glowing;
    
    public SimpleMaterial(final Material.Builder builder) {
        this.textureLocation = builder.getTextureLocation();
        this.color = builder.getColor();
        this.glowing = builder.isGlowing();
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
    
    @Override
    public MaterialColor getColor() {
        return this.color;
    }
    
    @Override
    public boolean isGlowing() {
        return this.glowing;
    }
    
    @Override
    public void setTextureLocation(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    @Override
    public void setColor(final MaterialColor color) {
        this.color = color;
    }
    
    @Override
    public void setGlowing(final boolean glowing) {
        this.glowing = glowing;
    }
}
